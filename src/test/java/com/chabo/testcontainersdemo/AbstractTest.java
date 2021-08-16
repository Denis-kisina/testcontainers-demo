package com.chabo.testcontainersdemo;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

class AbstractTest {

  //    @Container
  private static final MySQLContainer container = new MySQLContainer("mysql:8.0.26");
//        .withDatabaseName("somedatabase")
//        .withUsername("root")
//        .withPassword("letsgokisina");

  @BeforeAll
  public static void setup(){
    container.start();
  }


  @DynamicPropertySource
  public static void overrideProps(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.username", container::getUsername);
    registry.add("spring.datasource.password", container::getPassword);
  }
}
