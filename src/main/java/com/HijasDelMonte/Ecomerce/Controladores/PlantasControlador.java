package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.PlantasDTO;
import com.HijasDelMonte.Ecomerce.Servicios.PlantasServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlantasControlador {
    @Autowired
    PlantasServicios plantasServicios;

    @GetMapping("/api/plantas")
    public List<PlantasDTO> obtenerPlantas(){return plantasServicios.obtenerPlantasDTO();}
}
