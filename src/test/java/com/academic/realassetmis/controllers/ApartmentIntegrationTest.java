package com.academic.realassetmis.controllers;

import com.academic.realassetmis.models.Apartment;
import com.academic.realassetmis.models.dto.ApartmentDto;
import com.academic.realassetmis.utils.APIResponse;
import org.apache.coyote.Response;
import org.json.JSONException;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApartmentIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAll_success() throws JSONException {
        String response = this.restTemplate.getForObject("/all-apartments", String.class);
        JSONAssert.assertEquals("[{name: \"Aheza\"},{name: \"Akarabo\"},{name: \"Isange\"}, {name: \"Ngwino aheza\"}]", response, false);
    }
    @Test
    public void getById_successObject() {
        Apartment apartment = this.restTemplate.getForObject("/all-apartments/e4432ba9-889b-4887-ab8b-14cb85daefb3", Apartment.class);

        assertEquals("Aheza", apartment.getName());
    }

    @Test
    public void getById_404() {
        ResponseEntity<Apartment> response = this.restTemplate.getForEntity("/all-apartments/e4432ba9-889b-4887-ab8b-14cb85daefb4",Apartment.class);
        System.out.println("this was a reposne"+response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void create_success(){
        Apartment apartment =  new Apartment("Testing", "nyabihu", "Stanley", 150);
        ResponseEntity<Apartment> response = this.restTemplate.postForEntity("/save-apartment", apartment, Apartment.class);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Testing", response.getBody().getName());
    }

    @Test
    public void create_400(){
        Apartment apartment =  new Apartment("Akarabo", "nyabihu", "Stanley", 150);
        ResponseEntity<String> response = this.restTemplate.postForEntity("/save-apartment", apartment, String.class);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Names exists already", response.getBody());
    }

    @Test
    public void update_Success() {
        ApartmentDto apartment = new ApartmentDto("Updated Akarabo", "nyabihu", "Stanley", 150);
        ResponseEntity<Apartment> response = restTemplate.exchange("/update-apartment?id=e4432ba9-889b-4887-ab8b-14cb85daefb3", HttpMethod.PUT, new HttpEntity<>(apartment), Apartment.class);

        assertEquals(response.getBody().getName(), "Updated Akarabo");
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void delete_Success() {
        ResponseEntity<Apartment> response = restTemplate.exchange("/delete-apartment?id=e4432ba9-889b-4887-ab8b-14cb85daefb3", HttpMethod.DELETE, null, Apartment.class);

//        assertEquals(response.getBody().getName(), "Updated Akarabo");
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}
