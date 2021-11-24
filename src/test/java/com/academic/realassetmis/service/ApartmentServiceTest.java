package com.academic.realassetmis.service;

import com.academic.realassetmis.models.Apartment;
import com.academic.realassetmis.models.dto.ApartmentDto;
import com.academic.realassetmis.repositories.IApartmentRepository;
import com.academic.realassetmis.services.ApartmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApartmentServiceTest {
    @Mock
    private IApartmentRepository apartmentRepositoryMock;

    @InjectMocks
    private ApartmentService apartmentService;

    @Test
    public void getAllApartments() {
        when(apartmentRepositoryMock.findAll()).thenReturn(Arrays.asList(new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Akarabo", "nyabihu", "Stanley", 130),
                new Apartment(UUID.fromString("edcd99ae-157a-4c88-8a44-405d93b4f18a"), "Isange", "Kigali", "Mwenyemari", 140)));
        assertEquals(130, apartmentService.getAll().get(0).getPrice());
    }

    @Test
    public void getOneApartmentWhenIdIsFound() {
        when(apartmentRepositoryMock.findById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"))).thenReturn(Optional.of(new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Akarabo", "nyabihu", "Stanley", 130)));
        assertEquals(130, apartmentService.getById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc")).getPrice());
    }
    @Test
    public void getOneApartmentWhenIdIsNotFound() {
        when(apartmentRepositoryMock.findById(UUID.fromString("16e1f6fb-fbe5-4dd2-9b15-622914827bdc"))).thenReturn(Optional.of(new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Akarabo", "nyabihu", "Stanley", 130)));
        assertEquals(130, apartmentService.getById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc")).getPrice());
    }

    @Test
    public void saveOneApartment() {
        ApartmentDto dto = new ApartmentDto("Akarabo", "nyabihu", "Stanley", 130);
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Akarabo", "nyabihu", "Stanley", 130);
        when(apartmentRepositoryMock.save(any(Apartment.class))).thenReturn(apartment);
        assertEquals(130, apartmentService.create(dto).getPrice());
    }

    @Test
    public void whenGivenId_shouldUpdateUser_ifFound() {
       ApartmentDto dto = new ApartmentDto("Akarabo", "nyabihu", "Stanley", 150);
       Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Akarabo", "nyabihu", "Stanley", 130);
       Apartment updatedApartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Akarabo", "nyabihu", "Stanley", 150);

        when(apartmentRepositoryMock.getById(apartment.getId())).thenReturn(apartment);
        when(apartmentRepositoryMock.save(apartment)).thenReturn(updatedApartment);

        assertEquals(150, apartmentService.update(apartment.getId(), dto).getPrice());
//        given(apartmentService.())
    }


    @Test
    public void whenGivenId_shouldUpdateUser_ifNotFound() {
        ApartmentDto dto = new ApartmentDto("Akarabo", "nyabihu", "Stanley", 150);
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Akarabo", "nyabihu", "Stanley", 130);
        Apartment updatedApartment = new Apartment(UUID.fromString("36e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Akarabo", "nyabihu", "Stanley", 150);

        when(apartmentRepositoryMock.findById(apartment.getId())).thenReturn(Optional.of(apartment));
        when(apartmentRepositoryMock.save(updatedApartment)).thenReturn(updatedApartment);

        assertEquals(150, apartmentService.update(apartment.getId(), dto).getPrice());
//        given(apartmentService.())
    }
    @Test
    public void deleteWhenGivenIdIsFound() {
        ApartmentDto dto = new ApartmentDto("Akarabo", "nyabihu", "Stanley", 150);
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Akarabo", "nyabihu", "Stanley", 130);

        when(apartmentRepositoryMock.findById(apartment.getId())).thenReturn(Optional.of(apartment));
//        when(apartmentRepositoryMock.deleteById(apartment.getId())).thenReturn(null);

        assertEquals(150, apartmentService.update(apartment.getId(), dto).getPrice());
//        given(apartmentService.())
    }
}

