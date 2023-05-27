package com.HijasDelMonte.Ecomerce.Servicios;

import com.HijasDelMonte.Ecomerce.DTO.ProductosSeleccionadosDTO;
import com.HijasDelMonte.Ecomerce.Models.Plantas;
import com.HijasDelMonte.Ecomerce.Models.ProductosSeleccionados;

import java.util.List;

public interface ProductosSeleccionadosServicio {
    void guardarProductoSeleccionado(ProductosSeleccionados productosSeleccionados);
    List<ProductosSeleccionadosDTO> obtenerProductosSeleccionadosDTO();
}
