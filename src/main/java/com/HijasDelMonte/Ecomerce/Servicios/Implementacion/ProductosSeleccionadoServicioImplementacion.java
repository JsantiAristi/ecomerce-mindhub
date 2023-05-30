package com.HijasDelMonte.Ecomerce.Servicios.Implementacion;

import com.HijasDelMonte.Ecomerce.DTO.ProductosSeleccionadosDTO;
import com.HijasDelMonte.Ecomerce.Models.ProductosSeleccionados;
import com.HijasDelMonte.Ecomerce.Repositorios.ProductosSeleccionadosRepositorio;
import com.HijasDelMonte.Ecomerce.Servicios.ProductosSeleccionadosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductosSeleccionadoServicioImplementacion implements ProductosSeleccionadosServicio {
    @Autowired
    ProductosSeleccionadosRepositorio productosSeleccionadosRepositorio;

    @Override
    public void guardarProductoSeleccionado(ProductosSeleccionados productosSeleccionados) {
        productosSeleccionadosRepositorio.save(productosSeleccionados);
    }

    @Override
    public List<ProductosSeleccionadosDTO> obtenerProductosSeleccionadosDTO() {
        return productosSeleccionadosRepositorio.findAll().stream().map(ProductosSeleccionadosDTO::new).collect(toList());
    }

    @Override
    public ProductosSeleccionados obtenerProducto(long id) {
        return productosSeleccionadosRepositorio.findById(id).orElse(null);
    }
}
