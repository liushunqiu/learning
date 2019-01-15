package com.liushunqiu.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpMessageConverterConfig {
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters(){
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

		FastJsonConfig fastJsonConfig = new FastJsonConfig();

		SerializerFeature[] serializerFeatures = new SerializerFeature[]{
				//    输出key是包含双引号
//                SerializerFeature.QuoteFieldNames,
				//    是否输出为null的字段,若为null 则显示该字段
//                SerializerFeature.WriteMapNullValue,
				//    数值字段如果为null，则输出为0
				SerializerFeature.WriteNullNumberAsZero,
				//     List字段如果为null,输出为[],而非null
				SerializerFeature.WriteNullListAsEmpty,
				//    字符类型字段如果为null,输出为"",而非null
				SerializerFeature.WriteNullStringAsEmpty,
				//    Boolean字段如果为null,输出为false,而非null
				SerializerFeature.WriteNullBooleanAsFalse,
				//    Date的日期转换器
				SerializerFeature.WriteDateUseDateFormat,
				//    循环引用
				SerializerFeature.DisableCircularReferenceDetect
		};
		fastJsonConfig.setSerializerFeatures(serializerFeatures);
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		return new HttpMessageConverters(fastJsonHttpMessageConverter);
	}
}
