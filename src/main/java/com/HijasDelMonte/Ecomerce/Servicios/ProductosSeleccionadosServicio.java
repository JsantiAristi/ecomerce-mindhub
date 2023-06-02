package com.HijasDelMonte.Ecomerce.Servicios;

import com.HijasDelMonte.Ecomerce.DTO.ProductosSeleccionadosDTO;
import com.HijasDelMonte.Ecomerce.Models.ProductosSeleccionados;

import java.util.List;

public interface ProductosSeleccionadosServicio {
    void guardarProductoSeleccionado(ProductosSeleccionados productosSeleccionados);
    void eliminarProducto(ProductosSeleccionados productosSeleccionados);
    List<ProductosSeleccionadosDTO> obtenerProductosSeleccionadosDTO();
    ProductosSeleccionados obtenerProducto(long id);
}
