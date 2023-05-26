package com.HijasDelMonte.Ecomerce.Repositorios;

import com.HijasDelMonte.Ecomerce.DTO.ClientesDTO;
import com.HijasDelMonte.Ecomerce.DTO.PlantasDTO;
import com.HijasDelMonte.Ecomerce.Models.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;;import java.util.List;


@RepositoryRestResource
public interface ClientesRepositorio extends JpaRepository<Clientes , Long>{



}
