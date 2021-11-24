package com.academic.realassetmis.controllers;

import com.academic.realassetmis.models.Apartment;
import com.academic.realassetmis.models.dto.ApartmentDto;
import com.academic.realassetmis.services.ApartmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ApartmentController.class)
public class ApartmentControllerTest {

    @MockBean
    private ApartmentService apartmentServiceMock;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAll_success() throws Exception {
        List<Apartment> asList = Arrays.asList(new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Isange", "Nyagatare", "Stanley", 130),
                new Apartment(UUID.fromString("edcd99ae-157a-4c88-8a44-405d93b4f18a"), "Urugwiro", "Kigali", "Donatien", 150));

        when(apartmentServiceMock.getAll()).thenReturn(asList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/all-apartments")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"16e1f6fb-fae5-4dd2-9b15-622914827bdc\",\"name\":\"Isange\",\"location\":\"Nyagatare\",\"price\":130}," +
                        "{\"id\":\"edcd99ae-157a-4c88-8a44-405d93b4f18a\",\"name\":\"Urugwiro\",\"location\":\"Kigali\",\"price\":150}]"))
                .andReturn();

    }

    @Test
    public void getById_Success() throws Exception {
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Isange", "Nyagatare", "Stanley", 130);

        when(apartmentServiceMock.getById(apartment.getId())).thenReturn(apartment);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/all-apartments/16e1f6fb-fae5-4dd2-9b15-622914827bdc")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"16e1f6fb-fae5-4dd2-9b15-622914827bdc\",\"name\":\"Isange\",\"location\":\"Nyagatare\",\"price\":130}"))
                .andReturn();
    }

    @Test
    public void getById_WhenGivenIdNotFound() throws Exception {
        Apartment apartment = new Apartment(UUID.fromString("8f352825-e13f-4f3f-b0ad-e3d2fceccfbc"), "Isange", "Nyagatare", "Stanley", 130);

        when(apartmentServiceMock.getById(apartment.getId())).thenReturn(apartment);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/all-apartments/8f352825-e13f-4f3f-b0ad-e3d2fcebcfbc")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"Apartment not found\"}"))
                .andReturn();
    }

    @Test
    public void registerApartment_Success() throws Exception {
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Isange", "Nyagatare", "Stanley", 130);
        ApartmentDto apartmentDto = new ApartmentDto("Isange", "Nyagatare", "Stanley", 130);

        when(apartmentServiceMock.create(apartmentDto)).thenReturn(apartment);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/save-apartment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"16e1f6fb-fae5-4dd2-9b15-622914827bdc\",\"name\":\"Isange\",\"location\":\"Nyagatare\",\"owner\":\"Stanley\",\"price\":130}");

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":\"16e1f6fb-fae5-4dd2-9b15-622914827bdc\",\"name\":\"Isange\",\"location\":\"Nyagatare\",\"owner\":\"Stanley\",\"price\":130}"))
                .andReturn();
    }

    @Test
    public void updateApartment_Success() throws Exception {
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Isange", "Nyagatare", "Stanley", 130);
        Apartment updateApartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Isange", "Nyagatare", "Stanley", 150);
        ApartmentDto apartmentDto = new ApartmentDto("Isange", "Nyagatare", "Stanley", 150);


        when(apartmentServiceMock.update(apartment.getId(), apartmentDto)).thenReturn(updateApartment);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/update-apartment?id=16e1f6fb-fae5-4dd2-9b15-622914827bdc")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Isange\",\"location\":\"Nyagatare\",\"owner\":\"Stanley\",\"price\":150}");

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"name\":\"Isange\",\"location\":\"Nyagatare\",\"owner\":\"Stanley\",\"price\":150}"))
                .andReturn();
    }

    @Test
    public void updateApartment_Failed() throws Exception {
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-623914827bdc"), "Isange", "Nyagatare", "Stanley", 130);
        Apartment updateApartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-623914827bdc"), "Isange", "Nyagatare", "Stanley", 150);
        ApartmentDto apartmentDto = new ApartmentDto("Isange", "Nyagatare", "Stanley", 150);


        when(apartmentServiceMock.update(apartment.getId(), apartmentDto)).thenReturn(null);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/update-apartment?id=16e1f6fb-fae5-4dd2-9b15-622914827idc")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Isange\",\"location\":\"Nyagatare\",\"owner\":\"Stanley\",\"price\":150}");

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":false,\"message\":\"Apartment not found\"}"))
                .andReturn();
    }

    @Test
    public void deleteApartment_Success() throws Exception {
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Isange", "Nyagatare", "Stanley", 130);

        when(apartmentServiceMock.deleteApartment(apartment.getId())).thenReturn(apartment);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/delete-apartment?id=16e1f6fb-fae5-4dd2-9b15-622914827bdc")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Isange\",\"location\":\"Nyagatare\",\"owner\":\"Stanley\",\"price\":130}"))
                .andReturn();
    }
    @Test
    public void deleteApartment_Failed() throws Exception {
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Isange", "Nyagatare", "Stanley", 130);

        when(apartmentServiceMock.deleteApartment(apartment.getId())).thenReturn(apartment);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/delete-apartment?id=16e1f6jb-fab5-4dd2-9b15-622914827bdc")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":false,\"message\":\"Apartment not found\"}"))
                .andReturn();
    }
}
