package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void checkUserExist() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("username", "yue");
        ResponseEntity<String> response = testRestTemplate.postForEntity("/user/check", multiValueMap, String.class);
        Assert.assertEquals("invalid", response.getBody());
    }

    @Test
    public void checkUserNotExist() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("username", "yue111");
        ResponseEntity<String> response = testRestTemplate.postForEntity("/user/check", multiValueMap, String.class);
        Assert.assertEquals("valid", response.getBody());
    }

    @Test
    public void loginRightUsernameAndRightPassword() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("username", "yue");
        multiValueMap.add("password", "123");
        ResponseEntity<String> response = testRestTemplate.postForEntity("/user/login", multiValueMap, String.class);
        String path = response.getHeaders().getLocation().getPath();
        boolean actual = path.startsWith("/index");
        Assert.assertEquals(true, actual);
    }

    @Test
    public void loginRightUsernameAndWrongPassword() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("username", "yue");
        multiValueMap.add("password", "123456");
        String response = testRestTemplate.postForObject("/user/login", multiValueMap, String.class);
        System.out.print(response);
        boolean actual = response.indexOf("密码错误") > 0;
        Assert.assertEquals(true, actual);
    }
}
