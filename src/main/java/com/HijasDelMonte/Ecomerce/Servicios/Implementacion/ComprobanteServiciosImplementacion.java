package com.HijasDelMonte.Ecomerce.Servicios.Implementacion;

import com.HijasDelMonte.Ecomerce.Models.Comprobante;
import com.HijasDelMonte.Ecomerce.Repositorios.ComprobanteRepositorio;
import com.HijasDelMonte.Ecomerce.Servicios.ComprobanteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComprobanteServiciosImplementacion implements ComprobanteServicio {

    @Autowired
    ComprobanteRepositorio comprobanteRepositorio;

    @Override
    public void guardarComprobante(Comprobante comprobante) {
        comprobanteRepositorio.save(comprobante);
    }
}
