package com.HijasDelMonte.Ecomerce.Repositorios;

import com.HijasDelMonte.Ecomerce.Models.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ComprobanteRepositorio extends JpaRepository<Comprobante, Long> {
}
