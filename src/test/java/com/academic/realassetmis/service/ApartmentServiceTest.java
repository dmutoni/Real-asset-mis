package com.academic.realassetmis.service;

import com.academic.realassetmis.models.Apartment;
import com.academic.realassetmis.models.dto.ApartmentDto;
import com.academic.realassetmis.repositories.IApartmentRepository;
import com.academic.realassetmis.services.ApartmentService;
import com.academic.realassetmis.utils.Exceptions.BadRequestException;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
    public void update_Success() {
        ApartmentDto dto = new ApartmentDto("Akarabo", "nyabihu", "Stanley", 150);
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Akarabo", "nyabihu", "Stanley", 130);
        when(apartmentRepositoryMock.findById(apartment.getId())).thenReturn(Optional.of(apartment));
        when(apartmentRepositoryMock.save(apartment)).thenReturn(apartment);
        Apartment updateApartment = apartmentService.update(apartment.getId(), dto);
        System.out.println(updateApartment.getPrice());
        assertEquals(150, updateApartment.getPrice());
    }

    @Test
    public void whenGivenId_shouldUpdateUser_ifNotFound() {
        ApartmentDto dto = new ApartmentDto("Akarabo", "nyabihu", "Stanley", 150);
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Akarabo", "nyabihu", "Stanley", 130);
        when(apartmentRepositoryMock.findById(apartment.getId())).thenThrow(BadRequestException.class);
        when(apartmentRepositoryMock.save(apartment)).thenReturn(apartment);
        Apartment updateApartment = apartmentService.update(apartment.getId(), dto);
        System.out.println(updateApartment.getPrice());
        assertEquals(150, updateApartment.getPrice());
    }

    @Test
    public void deleteApartment_Success(){
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Akarabo", "nyabihu", "Stanley", 130);
        when(apartmentRepositoryMock.findById(apartment.getId())).thenReturn(Optional.of(apartment));
        apartmentService.deleteApartment(apartment.getId());
        verify(apartmentRepositoryMock).deleteById(apartment.getId());
    }
    @Test
    public void deleteApartment_404(){
        Apartment apartment = new Apartment(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-632914827bdc"), "Akarabo", "nyabihu", "Stanley", 130);
        when(apartmentRepositoryMock.findById(apartment.getId())).thenReturn(Optional.of(apartment));
        apartmentService.deleteApartment(apartment.getId());
        verify(apartmentRepositoryMock).deleteById(apartment.getId());
    }
}

