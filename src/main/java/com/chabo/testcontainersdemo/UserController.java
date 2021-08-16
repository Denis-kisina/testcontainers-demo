package com.chabo.testcontainersdemo;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {

  private JdbcTemplate jdbcTemplate;

  @PostMapping
  public UserCreateResponse create(@RequestBody User request) {
    String id = UUID.randomUUID().toString();
    jdbcTemplate.update("INSERT INTO users (id, name, email) VALUES (?, ?, ?)",
        id, request.getName(), request.getEmail());
    return new UserCreateResponse(id);
  }

  @GetMapping("/{id}")
  public User getUser(@PathVariable String id) {
    return jdbcTemplate.queryForObject("SELECT id, name, email"
            + " FROM users WHERE id = ?", new Object[]{id},
        (rs, rowNum) -> new User(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("email")
        )
    );
  }
}
