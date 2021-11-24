package com.academic.realassetmis.controllers;

import com.academic.realassetmis.models.Apartment;
import com.academic.realassetmis.models.dto.ApartmentDto;
import com.academic.realassetmis.services.ApartmentService;
import com.academic.realassetmis.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping("/all-apartments")
    public List<Apartment> getAll() {

        return apartmentService.getAll();
    }

    @GetMapping("/all-apartments/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") UUID id) {

        Apartment apartment = apartmentService.getById(id);
        if (apartment != null) {
            return ResponseEntity.ok(apartment);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "Apartment not found"));
    }

    @PostMapping("/save-apartment")
    public ResponseEntity<?> saveApartment(@RequestBody ApartmentDto dto) {
        Apartment apartment = apartmentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apartment);
    }

    @PutMapping("/update-apartment")
    public ResponseEntity<?> editApartment(@RequestParam UUID id, @RequestBody ApartmentDto dto) {
        Apartment apartment = apartmentService.update(id, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(apartment);
    }

    @DeleteMapping("/delete-apartment")
    public ResponseEntity<?> deleteApartment(@RequestParam UUID id) {
        Apartment apartment = apartmentService.deleteApartment(id);
        return ResponseEntity.ok(apartment);
    }
}
