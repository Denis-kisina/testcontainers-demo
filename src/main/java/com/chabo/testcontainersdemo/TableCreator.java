package com.chabo.testcontainersdemo;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TableCreator implements CommandLineRunner {

  private JdbcTemplate jdbcTemplate;

  @Override
  public void run(String... args) throws Exception {
    ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
        new ClassPathResource("sql/init.sql"));
  }
}
