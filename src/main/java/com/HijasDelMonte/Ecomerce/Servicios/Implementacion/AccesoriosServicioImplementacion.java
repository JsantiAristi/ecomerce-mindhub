package com.HijasDelMonte.Ecomerce.Servicios.Implementacion;

import com.HijasDelMonte.Ecomerce.DTO.AccesoriosDTO;
import com.HijasDelMonte.Ecomerce.Models.Accesorios;
import com.HijasDelMonte.Ecomerce.Repositorios.AccesoriosRepositorio;
import com.HijasDelMonte.Ecomerce.Servicios.AccesoriosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccesoriosServicioImplementacion implements AccesoriosServicios {

    @Autowired
    AccesoriosRepositorio accesoriosRepositorio;

    @Override
    public void guardarAccesorio(Accesorios accesorios) { accesoriosRepositorio.save(accesorios); }
    @Override
    public Accesorios obtenerAccesorio(Long id) { return accesoriosRepositorio.findById(id).orElse(null); }
    @Override
    public List<AccesoriosDTO> obtenerAccesoriosDTO() {return accesoriosRepositorio.findAll().stream().map(AccesoriosDTO::new).collect(toList()); }
}
