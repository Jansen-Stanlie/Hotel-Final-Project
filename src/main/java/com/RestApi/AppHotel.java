package com.RestApi;

import com.RestApi.rabbitmq.RestApiReceive;
import com.Stans.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages={"com."})
public class AppHotel
{
    RestApiReceive receive = new RestApiReceive();
    public static void main( String[] args )
    {
        SpringApplication app = new  SpringApplication(AppHotel.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        app.run(args);
    }
    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/User/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/User/register").permitAll()
                    .antMatchers(HttpMethod.POST, "/User/forgetPass").permitAll()
                    .anyRequest().authenticated();

        }
    }
}
