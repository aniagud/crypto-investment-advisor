package com.crypto.cryptoinvestmentadvisor;

import com.crypto.cryptoinvestmentadvisor.cryptostats.StatsProcessor;
import com.crypto.cryptoinvestmentadvisor.util.FileReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ConfigurationPropertiesScan(basePackageClasses = ApplicationProperties.class)
public class CryptoInvestmentAdvisorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoInvestmentAdvisorApplication.class, args);
    }

    @Bean
    public FileReader fileReader(ApplicationProperties applicationProperties) {
        return new FileReader(applicationProperties.getDataBaseDirectory());
    }

    @Bean
    public StatsProcessor statsProcessor(FileReader fileReader) {
        return new StatsProcessor(fileReader);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
