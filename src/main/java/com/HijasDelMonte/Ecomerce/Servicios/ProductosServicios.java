package com.HijasDelMonte.Ecomerce.Servicios;

import com.HijasDelMonte.Ecomerce.DTO.ProductosDTO;
import com.HijasDelMonte.Ecomerce.Models.Productos;
import java.util.List;

public interface ProductosServicios {
    void guardarPlanta(Productos plantas);
    List<ProductosDTO> obtenerProductosDTO();
    Productos obtenerPlanta(Long id);
    ProductosDTO obtenerProductoDTO(Long id);
}
