package com.liushunqiu.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.util.JedisClusterCRC16;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RedisUtils {

	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "PX";
	private static final Long RELEASE_SUCCESS = 1L;

	private final ThreadLocal<String> requestId = new ThreadLocal<>();

	private final static ExecutorService executorService = new ThreadPoolExecutor(
			//核心线程数量
			1,
			//最大线程数量
			8,
			//当线程空闲时，保持活跃的时间
			1000,
			//时间单元 ，毫秒级
			TimeUnit.MILLISECONDS,
			//线程任务队列
			new LinkedBlockingQueue<>(1024),
			//创建线程的工厂
			new RedisTreadFactory("redis-batch"));

	@Autowired
	private JedisCluster jedisCluster;

	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	public String get(String key) {
		return jedisCluster.get(key);
	}

	public Map<String, String> getBatchKey(List<String> keys) {
		Map<Jedis, List<String>> nodeKeyListMap = new HashMap<>();
		for (String key : keys) {
			//计算slot
			int slot = JedisClusterCRC16.getSlot(key);
			Jedis jedis = jedisCluster.getConnectionFromSlot(slot);
			if (nodeKeyListMap.containsKey(jedis)) {
				nodeKeyListMap.get(jedis).add(key);
			} else {
				nodeKeyListMap.put(jedis, Arrays.asList(key));
			}
		}
		//结果集
		Map<String, String> resultMap = new HashMap<>();
		CompletionService<Map<String,String>> batchService = new ExecutorCompletionService(executorService);
		nodeKeyListMap.forEach((k,v)->{
			batchService.submit(new BatchTask(k,v));
		});
		nodeKeyListMap.forEach((k,v)->{
			try {
				resultMap.putAll(batchService.take().get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});
		return resultMap;
	}

	public boolean lock(String lockKey, long expireTime){
		String uuid = UUID.randomUUID().toString();
		requestId.set(uuid);
		String result = jedisCluster.set(lockKey, uuid, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
		return LOCK_SUCCESS.equals(result);
	}

	public boolean unLock(String lockKey){
		String uuid = requestId.get();
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = jedisCluster.eval(script, Collections.singletonList(lockKey), Collections.singletonList(uuid));
		requestId.remove();
		return RELEASE_SUCCESS.equals(result);
	}

	class BatchTask implements Callable<Map<String,String>>{

		private Jedis jedis;

		private List<String> keys;

		private BatchTask(Jedis jedis, List<String> keys) {
			this.jedis = jedis;
			this.keys = keys;
		}

		@Override
		public Map<String, String> call() throws Exception {
			Map<String, String> resultMap = new HashMap<>();
			String[] keyArray = keys.toArray(new String[]{});
			List<String> nodeValueList = jedis.mget(keyArray);
			for (int i = 0; i < keys.size(); i++) {
				resultMap.put(keys.get(i),nodeValueList.get(i));
			}
			return resultMap;
		}
	}

	 static class RedisTreadFactory implements ThreadFactory{

		private final AtomicInteger threadNumber = new AtomicInteger(0);

		private final String namePredix;

		public RedisTreadFactory(String namePredix) {
			this.namePredix = namePredix +"-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread( r,namePredix + threadNumber.getAndIncrement());
			if (t.isDaemon())
				t.setDaemon(true);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}
	}
}
