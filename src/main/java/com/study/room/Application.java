package com.study.room;

import com.study.room.configurer.PassToken;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RestController
@EnableSwagger2
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PassToken
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiIgnore()
    @ApiOperation(value = "重定向到api首页")
    public ModelAndView index() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }

    @Bean
    public Docket createRestApi() {
        // 为 swagger添加 header参数输入
        ParameterBuilder userTokenHeader = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        userTokenHeader.name("token").description("token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(userTokenHeader.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.study.room"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("study.room")
                .description("study.room")
                .termsOfServiceUrl("share.dragonsking.cn")
                .contact("hezijian6338")
                .version("1.0")
                .build();
    }
}

