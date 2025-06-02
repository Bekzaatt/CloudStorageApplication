package com.bekzataitymov.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Bekzat",
                        email = "zhumalyulybekzat@gmail.com"
                ),
                description = "Cloud storage project for pet project",
                title = "Cloud storage application"
        )
)
@SecurityScheme(
        name = "Authorize",
        description = "Authorize with sessions",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.COOKIE,
        paramName = "JSESSIONID"
)
public class OpenApiConfig {
}
