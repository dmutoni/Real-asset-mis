package com.academic.realassetmis.services;

import com.academic.realassetmis.models.Apartment;
import com.academic.realassetmis.models.dto.ApartmentDto;
import com.academic.realassetmis.repositories.IApartmentRepository;
import com.academic.realassetmis.utils.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApartmentService {

    @Autowired
    private IApartmentRepository apartmentRepository;

    public List<Apartment> getAll() {

        return apartmentRepository.findAll();
    }

    public Apartment getById(UUID id) {
        Optional<Apartment> findById = apartmentRepository.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        }
        throw new ResourceNotFoundException("apartment", "id", id);
    }

    public Apartment create(ApartmentDto department) {
        System.out.println(department);
        Apartment apartment = new Apartment();
        apartment.setName(department.getName());
        apartment.setLocation(department.getLocation());
        apartment.setPrice(department.getPrice());
        apartment.setOwner(department.getOwner());

        return apartmentRepository.save(apartment);
    }

    public Apartment update(UUID id, ApartmentDto schoolDto) {
        Optional<Apartment> apartmentExists = this.apartmentRepository.findById(id);

        if (apartmentExists.isPresent()) {
            Apartment apartment = apartmentExists.get();
            apartment.setName(schoolDto.getName());
            apartment.setLocation(schoolDto.getLocation());
            apartment.setPrice(schoolDto.getPrice());
            apartment.setOwner(schoolDto.getOwner());
            return apartmentRepository.save(apartment);
        }
        throw new ResourceNotFoundException("apartment", "id", id);
    }

    public Apartment deleteApartment(UUID id) {
        apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment not found with id" + id));
        apartmentRepository.deleteById(id);
        return null;
    }

    public boolean existsByName(String name) {
        return apartmentRepository.existsByName(name);
    }
}

