package com.HijasDelMonte.Ecomerce.Servicios.Implementacion;

import com.HijasDelMonte.Ecomerce.DTO.ProductosDTO;
import com.HijasDelMonte.Ecomerce.Models.Productos;
import com.HijasDelMonte.Ecomerce.Repositorios.ProductosRepositorio;
import com.HijasDelMonte.Ecomerce.Servicios.ProductosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
public class ProductosServiciosImplementacion implements ProductosServicios {
    @Autowired
    ProductosRepositorio productosRepositorio;

    @Override
    public void guardarPlanta(Productos plantas) {
        productosRepositorio.save(plantas);
    }

    @Override
    public Productos obtenerPlanta(Long id) {
        return productosRepositorio.findById(id).orElse(null);
    }

    @Override
    public ProductosDTO obtenerProductoDTO(Long id) {
        return new ProductosDTO(productosRepositorio.findById(id).orElse(null));
    }

    @Override
    public List<ProductosDTO> obtenerProductosDTO() {
        return productosRepositorio.findAll().stream().map(ProductosDTO::new).collect(toList());
    }
}
