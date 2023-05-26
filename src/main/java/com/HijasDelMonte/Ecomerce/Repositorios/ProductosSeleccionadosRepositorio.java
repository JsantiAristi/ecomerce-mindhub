package com.HijasDelMonte.Ecomerce.Repositorios;

import com.HijasDelMonte.Ecomerce.Models.ProductosSeleccionados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductosSeleccionadosRepositorio extends JpaRepository<ProductosSeleccionados, Long> {

}
