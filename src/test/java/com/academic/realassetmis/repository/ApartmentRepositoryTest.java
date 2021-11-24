package com.academic.realassetmis.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.academic.realassetmis.models.Apartment;
import com.academic.realassetmis.repositories.IApartmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApartmentRepositoryTest {
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
        Apartment apartment = new Apartment("Akarabo", "nyabihu", "Stanley", 150);
        Apartment apartmentOption = apartmentRepository.save(apartment);
        assertNotNull(apartmentOption.getId());
    }
    @Test
    public void deleteWithSuccess() {
        apartmentRepository.deleteById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"));
        List<Apartment> apartments = apartmentRepository.findAll();
        assertEquals(3, apartments.size());
    }
    @Test
    public void delete_Fail(){
        apartmentRepository.deleteById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"));
        Optional<Apartment> book = apartmentRepository.findById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914837bdc"));
        assertFalse(book.isPresent());
    }
}
