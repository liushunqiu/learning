package com.liushunqiu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

@Configuration
@ConditionalOnClass({JedisCluster.class})
public class RedisConfig {
	@Autowired
	private RedisProperties redisProperties;

	@Bean(destroyMethod = "close")
	public JedisCluster getJedisCluster() {
		Set<HostAndPort> nodes = new HashSet<>();
		redisProperties.getCluster().getNodes().forEach(node->{
			String[] hp = node.split(":");
			nodes.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
		});
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
		jedisPoolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().toMillis());
		jedisPoolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
		jedisPoolConfig.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
		int timeout = (int) redisProperties.getTimeout().toMillis();
		return new JedisCluster(nodes, timeout, timeout, nodes.size(), redisProperties.getPassword(), jedisPoolConfig);
	}
}
