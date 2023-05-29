package com.HijasDelMonte.Ecomerce.Servicios;

import com.HijasDelMonte.Ecomerce.DTO.AccesoriosDTO;
import com.HijasDelMonte.Ecomerce.Models.Accesorios;

import java.util.List;

public interface AccesoriosServicios {
    void guardarAccesorio (Accesorios accesorios);
    List<AccesoriosDTO> obtenerAccesoriosDTO();
    Accesorios obtenerAccesorio(Long id);
}
