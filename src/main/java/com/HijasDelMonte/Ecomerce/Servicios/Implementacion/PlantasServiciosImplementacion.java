package com.HijasDelMonte.Ecomerce.Servicios.Implementacion;

import com.HijasDelMonte.Ecomerce.DTO.PlantasDTO;
import com.HijasDelMonte.Ecomerce.Models.Plantas;
import com.HijasDelMonte.Ecomerce.Repositorios.PlantasRepositorio;
import com.HijasDelMonte.Ecomerce.Servicios.PlantasServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PlantasServiciosImplementacion implements PlantasServicios {
    @Autowired
    PlantasRepositorio plantasRepositorio;

    @Override
    public void guardarPlanta(Plantas plantas) {
        plantasRepositorio.save(plantas);
    }

    @Override
    public Plantas obtenerPlanta(Long id) {
        return plantasRepositorio.findById(id).orElse(null);
    }

    @Override
    public List<PlantasDTO> obtenerPlantasDTO() {
        return plantasRepositorio.findAll().stream().map(PlantasDTO::new).collect(toList());
    }
}
