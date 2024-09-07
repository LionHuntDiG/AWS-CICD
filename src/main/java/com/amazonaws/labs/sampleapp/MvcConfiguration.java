package com.amazonaws.labs.sampleapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClientBuilder;
import com.amazonaws.services.codedeploy.AmazonCodeDeploy;
import com.amazonaws.services.codedeploy.AmazonCodeDeployClientBuilder;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;

import java.io.IOException;

@EnableWebMvc
@ComponentScan(basePackages = {"com.amazonaws.labs.sampleapp"})
@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    // Set the region explicitly
    private static final Region region = Region.getRegion(Regions.AP_SOUTH_1); // Explicitly set to ap-south-1

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/images/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public AmazonCodeDeploy codeDeploy() {
        // Use the AmazonCodeDeployClientBuilder to create a client with the specified region
        return AmazonCodeDeployClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1)
                .build();
    }

    @Bean
    public AmazonEC2 ec2() {
        // Use the AmazonEC2ClientBuilder to create a client with the specified region
        return AmazonEC2ClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1)
                .build();
    }
    
    @Bean
    public AmazonAutoScaling autoScaling() {
        // Use the AmazonAutoScalingClientBuilder to create a client with the specified region
        return AmazonAutoScalingClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1)
                .build();
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer properties() {
        final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        try {
            configurer.setLocations(new PathMatchingResourcePatternResolver().getResources("file:/var/codedeploy/tomcat-sample/env.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resources.", e);
        }
        return configurer;
    }
}
