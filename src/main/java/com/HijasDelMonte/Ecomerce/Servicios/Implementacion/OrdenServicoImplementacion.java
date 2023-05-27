package com.HijasDelMonte.Ecomerce.Servicios.Implementacion;

import com.HijasDelMonte.Ecomerce.Models.Orden;
import com.HijasDelMonte.Ecomerce.Repositorios.OrdenRepositorio;
import com.HijasDelMonte.Ecomerce.Servicios.OrdenServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenServicoImplementacion implements OrdenServicios {
    @Autowired
    OrdenRepositorio ordenRepositorio;
    @Override
    public void guardarOrden(Orden orden) {
        ordenRepositorio.save(orden);
    }
}
