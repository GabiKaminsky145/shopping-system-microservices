package com.apigateway.Config;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class SwaggerController {

    private final DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public SwaggerController(final DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/v3/api-docs/swagger-config")
    public Map<String, Object> swaggerConfig() {

        Map<String, Object> swaggerConfig = new LinkedHashMap<>();
        List<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrls = new LinkedList<>();
        System.out.println("Services = " + discoveryClient.getServices());
        discoveryClient.getServices().forEach(serviceName -> {
            String url =  "http://localhost:" + loadBalancerClient.choose(serviceName).getPort() + "/v3/api-docs";
            swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl(serviceName, url, serviceName));
        });
        swaggerConfig.put("urls", swaggerUrls);
        return swaggerConfig;
    }
}
