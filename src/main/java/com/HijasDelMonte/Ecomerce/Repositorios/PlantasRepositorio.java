package com.HijasDelMonte.Ecomerce.Repositorios;

import com.HijasDelMonte.Ecomerce.Models.Plantas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PlantasRepositorio extends JpaRepository<Plantas, Long> {
}
