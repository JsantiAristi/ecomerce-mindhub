package com.HijasDelMonte.Ecomerce.Servicios;

import com.HijasDelMonte.Ecomerce.DTO.OrdenDTO;
import com.HijasDelMonte.Ecomerce.DTO.PlantasDTO;
import com.HijasDelMonte.Ecomerce.Models.Orden;
import com.HijasDelMonte.Ecomerce.Models.Plantas;

import java.util.List;

public interface OrdenServicios {
    void guardarOrden(Orden orden);
//    List<OrdenDTO> obtenerOrdenesDTO();
}
