package com.sdpaymentgateway;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	public static final Contact DEFAULT_CONTACT = new Contact(
			"Sandhya P. Deshpande", "http://www.sdatlantis.com", "sandhya.deshpande@mindtree.com");
	
	/*public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
			"SD Payment Gateway API", "Payment Gateway for the Atlantis Project", "1.0", 
			"urn:tos", DEFAULT_CONTACT.toString(), "Apache2.0", "http://www.apache.org/licences/LICENCE-2.0");
	
	*/
	public static final ApiInfoBuilder DEFAULT_API_INFO_BUILDER = new ApiInfoBuilder();	
	
	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>();
	
	@Bean
	public Docket api(){	
		//sets the contact and license information
		setDefaultApiInfo();
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO_BUILDER.build());
				//.produces(DEFAULT_PRODUCES_AND_CONSUMES )
				//.consumes(DEFAULT_PRODUCES_AND_CONSUMES );
	}
	
	public void setDefaultApiInfo(){ 
		
		DEFAULT_PRODUCES_AND_CONSUMES.add("application/json");
		//DEFAULT_PRODUCES_AND_CONSUMES.add("application/xml");
		
		DEFAULT_API_INFO_BUILDER.contact(DEFAULT_CONTACT);
		DEFAULT_API_INFO_BUILDER.description("Payment Gateway for the Atlantis Project");
		DEFAULT_API_INFO_BUILDER.title("SD Payment Gateway API");
		DEFAULT_API_INFO_BUILDER.license("Apache2.0");
		DEFAULT_API_INFO_BUILDER.licenseUrl("http://www.apache.org/licences/LICENCE-2.0");		
	}
}

