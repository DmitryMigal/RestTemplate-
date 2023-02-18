package org.example;

import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class Communication {


    public static void main(String[] args) {
        Communication communication = new Communication(new RestTemplate());
        communication.allUser();
        User james = new User();
        james.setId(3L);
        james.setName("James");
        james.setLastName("Brown");
        ;
        james.setAge((byte) 34);
        communication.createUser(james);
        User thomas = new User();
        thomas.setId(3L);
        thomas.setName("Thomas");
        thomas.setLastName("Shelby");
        thomas.setAge((byte) 23);
        communication.putUser(thomas);
        User delete = new User();
        delete.setId(3L);
        delete.setName("Thomas");
        delete.setLastName("Shelby");
        delete.setAge((byte) 23);
        communication.deleteUser(delete);
    }


    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    final String url = "http://94.198.50.185:7081/api/users";
    String cookie;
    RestTemplate restTemplate;


    public void allUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> response = restTemplate.getForEntity(url, String.class);
        headers = response.getHeaders();
        cookie = headers.getFirst(headers.SET_COOKIE);
        System.out.println(cookie);
    }

    public void createUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        headers.getFirst(cookie);
        headers.add("Cookie", cookie);
        HttpEntity httpEntity = new HttpEntity<>(user, headers);
        HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        System.out.println(response.getBody());

    }

    public void putUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        headers.getFirst(cookie);
        headers.add("Cookie", cookie);
        HttpEntity httpEntity = new HttpEntity<>(user, headers);
        HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
        System.out.println(response.getBody());
    }

    public void deleteUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        headers.getFirst(cookie);
        headers.add("Cookie", cookie);
        HttpEntity httpEntity = new HttpEntity<>(user, headers);
        HttpEntity<String> response = restTemplate.exchange(url + "/" + user.getId(),
                HttpMethod.DELETE, httpEntity, String.class);
        System.out.println(response.getBody());

    }

}
