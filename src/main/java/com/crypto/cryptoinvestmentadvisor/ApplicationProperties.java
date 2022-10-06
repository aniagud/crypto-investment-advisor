package com.crypto.cryptoinvestmentadvisor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties
public class ApplicationProperties {

    private String dataBaseDirectory;
}
