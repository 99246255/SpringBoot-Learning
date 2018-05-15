package com.example.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * fastJson相关设置
	 */
	private FastJsonConfig getFastJsonConfig() {

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		// 在serializerFeatureList中添加转换规则
		List<SerializerFeature> serializerFeatureList = new ArrayList<SerializerFeature>();
		serializerFeatureList.add(SerializerFeature.PrettyFormat);
		serializerFeatureList.add(SerializerFeature.WriteMapNullValue);
		serializerFeatureList.add(SerializerFeature.WriteNullStringAsEmpty);
		serializerFeatureList.add(SerializerFeature.WriteNullListAsEmpty);
		serializerFeatureList.add(SerializerFeature.DisableCircularReferenceDetect);
		serializerFeatureList.add(SerializerFeature.WriteEnumUsingToString);
		serializerFeatureList.add(SerializerFeature.WriteDateUseDateFormat);
		SerializerFeature[] serializerFeatures = serializerFeatureList
				.toArray(new SerializerFeature[serializerFeatureList.size()]);

		// 自定义序列化规则
		SerializeConfig serializeConfig = new SerializeConfig();
		serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		serializeConfig.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		// 自定义反序列化规则
		ParserConfig parserConfig = new ParserConfig();
		fastJsonConfig.setSerializeConfig(serializeConfig);
		fastJsonConfig.setParserConfig(parserConfig);
		fastJsonConfig.setSerializerFeatures(serializerFeatures);
		return fastJsonConfig;
	}

	/**
	 * fastJson相关设置
	 */
	private FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter() {

		FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter4();

		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.parseMediaType("text/html;charset=UTF-8"));
		supportedMediaTypes.add(MediaType.parseMediaType("application/json"));

		fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		fastJsonHttpMessageConverter.setFastJsonConfig(getFastJsonConfig());

		return fastJsonHttpMessageConverter;
	}
	
	/**
	 * 添加fastJsonHttpMessageConverter到converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(fastJsonHttpMessageConverter());
	}

//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("*")
//				.allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
//				.allowCredentials(false).maxAge(3600);
//	}
	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(commonIntercepter).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
	
}
