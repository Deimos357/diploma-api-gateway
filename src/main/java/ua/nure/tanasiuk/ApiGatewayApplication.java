package ua.nure.tanasiuk;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.nure.tanasiuk.filter.InternalServerErrorPostFilter;
import ua.nure.tanasiuk.filter.SecurityFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableZuulProxy
@EnableResourceServer
@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public SecurityFilter coreFilter() {
        return new SecurityFilter();
    }

    @Bean
    public InternalServerErrorPostFilter postFilter() {
        return new InternalServerErrorPostFilter(new ObjectMapper());
    }
}
