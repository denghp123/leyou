package cn.itcast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author dhp
 * @Date 2020/6/5 11:25
 * @Version 1.0
 */
@Configuration
public class  GloblCrosConfig {

//    @Bean
//    public CorsFilter corsFilter(){
//        //添加cors的配置信息
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        //允许的域，不能写* 否则就无法操作cookie了
//        corsConfiguration.addAllowedOrigin("http://manage.leyou.com");
//        //是否发送cookie信息
//        corsConfiguration.setAllowCredentials(true);
//        //3) 允许的请求方式
//        corsConfiguration.addAllowedMethod("OPTIONS");
//        corsConfiguration.addAllowedMethod("HEAD");
//        corsConfiguration.addAllowedMethod("GET");
//        corsConfiguration.addAllowedMethod("PUT");
//        corsConfiguration.addAllowedMethod("POST");
//        corsConfiguration.addAllowedMethod("DELETE");
//        corsConfiguration.addAllowedMethod("PATCH");
//        // 4）允许的头信息
//        corsConfiguration.addAllowedHeader("*");
//
//        //2.添加映射路径，我们拦截一切请求
//        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
//        configSource.registerCorsConfiguration("/**", corsConfiguration);
//
//        //3.返回新的CorsFilter.
//        return new CorsFilter(configSource);
//    }

    @Bean
    public CorsFilter corsFilter(CorsProperties prop) {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,一定不要写*，否则cookie就无法使用了
        prop.getAllowedOrigins().forEach(config::addAllowedOrigin);
        //2) 是否发送Cookie信息
        config.setAllowCredentials(prop.getAllowCredentials());
        //3) 允许的请求方式
        prop.getAllowedMethods().forEach(config::addAllowedMethod);
        //4）允许的头信息
        prop.getAllowedHeaders().forEach(config::addAllowedHeader);
        //5） 允许的时间
        config.setMaxAge(prop.getMaxAge());

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration(prop.getPath(), config);

        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }
}
