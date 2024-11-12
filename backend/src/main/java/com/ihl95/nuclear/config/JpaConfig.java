package com.ihl95.nuclear.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ihl95.nuclear.components.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaConfig {

  @Bean
  public AuditorAware<String> auditorProvider() {
    return new AuditorAwareImpl();
  }

}
