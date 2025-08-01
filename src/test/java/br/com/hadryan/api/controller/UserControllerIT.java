package br.com.hadryan.api.controller;


import br.com.hadryan.api.config.TestContainersConfiguration;
import br.com.hadryan.api.mapper.request.UserPostRequest;
import br.com.hadryan.api.mapper.response.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/sql/init-user-table.sql")
@Import(TestContainersConfiguration.class)
public class UserControllerIT {

    private static final String BASE_URL = "/api/v1/users";

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    @DisplayName("Create User Successfully")
    public void createUserShouldReturnUserCreated() {
        String url = "http://localhost:" + port + BASE_URL + "/create";

        UserPostRequest userPostRequest = new UserPostRequest();
        userPostRequest.setFirstName("John");
        userPostRequest.setLastName("Doe");
        userPostRequest.setEmail("mail@mail.com");
        userPostRequest.setPassword("password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserPostRequest> request = new HttpEntity<>(userPostRequest, headers);

        ResponseEntity<UserResponse> response = restTemplate.postForEntity(url, request, UserResponse.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        UserResponse userResponse = response.getBody();
        Assertions.assertNotNull(userResponse);
        Assertions.assertNotNull(userResponse.getId());
        Assertions.assertEquals(userResponse.getFirstName(), userPostRequest.getFirstName());
        Assertions.assertEquals(userResponse.getLastName(), userPostRequest.getLastName());
        Assertions.assertEquals(userResponse.getEmail(), userPostRequest.getEmail());
    }

}
