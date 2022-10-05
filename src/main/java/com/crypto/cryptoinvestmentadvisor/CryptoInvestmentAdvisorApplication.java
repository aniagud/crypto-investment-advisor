package com.crypto.cryptoinvestmentadvisor;

import com.crypto.cryptoinvestmentadvisor.cryptostats.StatsProcessor;
import com.crypto.cryptoinvestmentadvisor.util.FileReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
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
}
