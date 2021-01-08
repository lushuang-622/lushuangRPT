package com.ssd.config.swagger2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Api：用在请求的类上，表示对类的说明
 *     tags="说明该类的作用，可以在UI界面上看到的注解"
 *     value="该参数没什么意义，在UI界面上也看到，所以不需要配置"
 *
 * @ApiOperation：用在请求的方法上，说明方法的用途、作用
 *     value="说明方法的用途、作用"
 *     notes="方法的备注说明"
 *
 * @ApiImplicitParams：用在请求的方法上，表示一组参数说明
 *     @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
 *         name：参数名
 *         value：参数的汉字说明、解释
 *         required：参数是否必须传
 *         paramType：参数放在哪个地方
 *             · header --> 请求参数的获取：@RequestHeader
 *             · query --> 请求参数的获取：@RequestParam
 *             · path（用于restful接口）--> 请求参数的获取：@PathVariable
 *             · body（不常用）
 *             · form（不常用）
 *         dataType：参数类型，默认String，其它值dataType="Integer"
 *         defaultValue：参数的默认值
 *
 * @ApiResponses：用在请求的方法上，表示一组响应
 *     @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
 *         code：数字，例如400
 *         message：信息，例如"请求参数没填好"
 *         response：抛出异常的类
 *
 * @ApiModel：用于响应类上，表示一个返回响应数据的信息
 *             （这种一般用在post创建的时候，使用@RequestBody这样的场景，
 *             请求参数无法使用@ApiImplicitParam注解进行描述的时候）
 *     @ApiModelProperty：用在属性上，描述响应类的属性
 *
 * @author lus
 * @Date 2021/1/7 0007 09
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value(value = "${swagger.enabled}")
    boolean swaggerEnabled;
    /**
     * select()数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现，本例
     * 采用指定扫描的包路径来定义，Swagger会扫描该包下所有Controller定义的API，并产生文档
     * 内容（除了被@ApiIgnore指定的请求）。
     *
     * 指定 @Bean类型可以确定前端模块模式类型
     * @return
     */
    @Bean("monitor")
    public Docket createRestApi() {
        // 添加请求参数，我们这里把自定义固定令牌作为请求头部参数传入后端
        List<Parameter> parameters = new ArrayList<Parameter>();
        ParameterBuilder parameterBuilder1 = new ParameterBuilder();
        parameterBuilder1.name("access").description("令牌").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameters.add(parameterBuilder1.build());

        ParameterBuilder parameterBuilder2 = new ParameterBuilder();
        parameterBuilder2.name("accessToken").description("动态令牌").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameters.add(parameterBuilder2.build());

        ParameterBuilder parameterBuilder3 = new ParameterBuilder();
        parameterBuilder3.name("userCode").description("用户标识").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameters.add(parameterBuilder3.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("monitor模块接口")
                .apiInfo(apiInfo())
                .enable(swaggerEnabled)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssd.monitor"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    /**
     * apiInfo()用来创建该Api的基本信息（这些基本信息会展现在文档页面中）。
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("这里创建属于本服务的标题")
                .description("这里详细介绍服务")
                .termsOfServiceUrl("http://www.lus.com/")
                .contact(new Contact("lus","http://www.lus.com","lus622314@163.com"))
                .version("1.0")
                .build();
    }
}
