package com.HijasDelMonte.Ecomerce.Repositorios;

import com.HijasDelMonte.Ecomerce.Models.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;;


@RepositoryRestResource
public interface ClientesRepositorio extends JpaRepository<Clientes , Long>{
    Clientes findByEmail(String email);


}
