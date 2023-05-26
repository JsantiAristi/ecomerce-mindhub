package com.HijasDelMonte.Ecomerce.Servicios;

import com.HijasDelMonte.Ecomerce.DTO.PlantasDTO;
import com.HijasDelMonte.Ecomerce.Models.Plantas;
import com.HijasDelMonte.Ecomerce.Repositorios.PlantasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface PlantasServicios {

    void guardarPlanta(Plantas plantas);
    List<PlantasDTO> obtenerPlantasDTO();

    Plantas obtenerPlanta(Long id);
}
