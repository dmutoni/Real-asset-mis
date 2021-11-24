package com.academic.realassetmis.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.academic.realassetmis.models.Apartment;
import com.academic.realassetmis.models.dto.ApartmentDto;
import com.academic.realassetmis.repositories.IApartmentRepository;
import com.academic.realassetmis.services.ApartmentService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApartmentRepositoryTest {
    @Mock
    private IApartmentRepository apartmentRepositoryMock;

    @InjectMocks
    private ApartmentService apartmentService;

    @Autowired
    private IApartmentRepository apartmentRepository;

    @Test
    public void findAll_success () {
        List<Apartment> apartments = apartmentRepository.findAll();
        assertEquals(4, apartments.size());
    }
    @Test
    public void findOne_success() {
        Optional<Apartment> apartmentOption = apartmentRepository.findById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"));
        assertTrue(apartmentOption.isPresent());
    }
    @Test
    public void findOne_fail() {
        Optional<Apartment> apartmentOption = apartmentRepository.findById(UUID.fromString("8f354825-e13f-4f3f-b0ad-e3d2fceccfbc"));
        assertFalse(apartmentOption.isPresent());
    }
    @Test
    public void saveOne() {
        Apartment dto = new Apartment("Akarabo", "nyabihu", "Stanley", 150);
        Apartment apartmentOption = apartmentRepository.save(dto);
        assertTrue(apartmentOption.getId() != null);
    }
    @Test
    public void updateOne() {
        Apartment dto = new Apartment("Akarabo", "nyabihu", "Stanley", 150);
        Apartment apartmentOption = apartmentRepository.save(dto);
        assertTrue(apartmentOption.getId() != null);
    }
}
