package com.amur.secondservice.utils;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Component
public class RestTemplateConfig {
    private final String dirToSertificates;
    private final String customTrustStore;
    private final String customTrustStorePassword;

    @Autowired
    public RestTemplateConfig(@Value("${soaspring.dir-to-sertificates}") String dirToSertificates,
                              @Value("${soaspring.custom-trust-store}") String customTrustStore,
                              @Value("${soaspring.custom-trust-store-password}") String customTrustStorePassword) {
        this.dirToSertificates = dirToSertificates;
        this.customTrustStore = customTrustStore;
        this.customTrustStorePassword = customTrustStorePassword;
    }


    public RestTemplate restTemplate() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        System.out.println(dirToSertificates + " " + customTrustStore + " " + customTrustStorePassword);
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(new File(dirToSertificates + customTrustStore),
                        customTrustStorePassword.toCharArray())
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        HttpComponentsClientHttpRequestFactory customRequestFactory = new HttpComponentsClientHttpRequestFactory();
        customRequestFactory.setHttpClient(httpClient);
        return new RestTemplate(customRequestFactory);
    }
}
