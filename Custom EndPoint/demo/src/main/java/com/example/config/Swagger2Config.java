package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {
	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())	//创建API基本信息
				.groupName("")	//指定分组，对应(/v2/api-docs?group=)
				.pathMapping("")	//base地址，最终会拼接Controller中的地址
				.select()		//控制要暴露的接口
				.apis(RequestHandlerSelectors.basePackage("com.example.controller"))	//通过指定扫描包暴露接口
				.paths(PathSelectors.any())	//设置过滤规则暴露接口
				.build().globalOperationParameters(setHeaderToken());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("用户中心数据接口")
				.description("获取用户信息")
				.version("0.1.0")
				.build();
	}

	/**
	 * 设置请求头
	 * @return
	 */
	private List<Parameter> setHeaderToken() {
		List<Parameter> pars = new ArrayList<>();
//		ParameterBuilder tokenPar = new ParameterBuilder();
//		tokenPar.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header")
//				.required(false).defaultValue("").build();
//		pars.add(tokenPar.build());
		return pars;
	}
}
