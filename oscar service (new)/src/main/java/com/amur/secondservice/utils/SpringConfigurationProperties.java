package com.amur.secondservice.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "soaspring")
@Component
@Getter
@Setter
public class SpringConfigurationProperties {
    private String dirToSertificates;
    private String customTrustStore;
    private String customTrustStorePassword;
}
