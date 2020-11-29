package br.com.portabilit.wsmarcaponto.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.classmate.TypeResolver;

import br.com.portabilit.wsmarcaponto.controller.exception.StandardError;
import br.com.portabilit.wsmarcaponto.controller.exception.ValidationError;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private final ResponseMessage code200 = simpleMessage(200, "Ok - Requisição realizada com sucesso.", "");
	private final ResponseMessage code201 = simpleMessage(201, "Created - Recurso criado.", "");
	private final ResponseMessage code400 = simpleMessage(400, "Bad Request - Requisição mal formada.",
			"StandardError");
	private final ResponseMessage code403 = simpleMessage(403, "Forbidden - Acesso Negado.", "StandardError");
	private final ResponseMessage code404 = simpleMessage(404, "Not Found - Não encontrado.", "StandardError");
	private final ResponseMessage code405 = simpleMessage(405,
			"Request method not supported - Método não suportado nesta requisição", "StandardError");
	private final ResponseMessage code422 = simpleMessage(422, "Unprocessable Entity - Erro de validação.",
			"ValidationError");
	private final ResponseMessage code500 = simpleMessage(500, "Internal Server Error - Erro inesperado do servidor.",
			"StandardError");

	private final TypeResolver typeResolver = new TypeResolver();

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(code200, code400, code403, code405, code500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(code201, code403, code405, code422, code500))
				.globalResponseMessage(RequestMethod.PUT,
						Arrays.asList(code200, code400, code403, code404, code405, code422, code500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(code200, code400, code403, code405, code500))
				.additionalModels(typeResolver.resolve(StandardError.class),
						typeResolver.resolve(ValidationError.class))
				.select().apis(RequestHandlerSelectors.basePackage("br.com.portabilit.wsmarcaponto.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ResponseMessage simpleMessage(int code, String message, String body) {
		if (code == 201) {
			Map<String, Header> map = new HashMap<>();
			map.put("location", new Header("location", "URI do novo recurso", new ModelRef("string")));
			return new ResponseMessageBuilder().code(201).message(message).headersWithDescription(map).build();
		} else {
			return new ResponseMessageBuilder().code(code).message(message).responseModel(new ModelRef(body)).build();
		}
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Web Service para o Sistema Marca Ponto",
				"Este Web Service é utilizado pela empresa PortabilIT", "Versão 1.0", "",
				new Contact("Heitor", "https://github.com/HeitorAmaral", "heitor.amaral90@outlook.com"),
				"Permitido uso corporativo", "", Collections.emptyList());
	}
}