package cn.itcast.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author dhp
 * @Date 2020/6/5 14:05
 * @Version 1.0
 */

@Data
@Component
@ConfigurationProperties(prefix = "ly.cors")
public class CorsProperties {

    private List<String> allowedOrigins;
    private Boolean allowCredentials;
    private List<String> allowedMethods;
    private Long maxAge;
    private List<String> allowedHeaders;
    private String path;
}
