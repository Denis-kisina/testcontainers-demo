package com.chabo.testcontainersdemo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.emptyString;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ContextConfiguration(initializers = UserControllerTest.PropertiesInitializer.class)
@Testcontainers
class UserControllerTest {

  @Autowired
  private UserController controller;

  @Container
  private static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:9.6.15")
      .withDatabaseName("users")
      .withUsername("postgres")
      .withPassword("password");

  static class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
          "spring.datasource.url=" + postgres.getJdbcUrl(),
          "spring.datasource.username=" + postgres.getUsername(),
          "spring.datasource.password=" + postgres.getPassword()
      ).applyTo(configurableApplicationContext.getEnvironment());
    }
  }

  @Test
  void test_create_get_user() {
    UserCreateResponse response = controller.create(
        new User("", "Denis Kisina", "denis.kisina@gmail.com"));
    assertThat(response.getId(), not(emptyString()));

    User user = controller.getUser(response.getId());
    assertThat(user.getName(), equalTo("Denis Kisina"));
  }

  @Test
  void test_create_duplicate_email_throws() {
    UserCreateResponse response = controller.create(
        new User("", "Andrew", "denis.kisina@gmail.com"));
    assertThrows(DuplicateKeyException.class, () ->
        controller.create(new User("", "Denis Kisina", "denis.kisina@gmail.com")));
  }
}