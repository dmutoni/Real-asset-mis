package com.academic.realassetmis.repositories;

import com.academic.realassetmis.models.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IApartmentRepository extends JpaRepository<Apartment, UUID> {
}
