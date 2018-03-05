package ru.hh.school.employerreview;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.core.CoreProdConfig;

@Configuration
@Import({CoreProdConfig.class})
public class ProdConfig {

  @Bean
  ExampleResource exampleResource() {
    return new ExampleResource();
  }
}
