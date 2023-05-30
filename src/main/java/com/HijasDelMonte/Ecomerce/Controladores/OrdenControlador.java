package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.OrdenDTO;
import com.HijasDelMonte.Ecomerce.Models.Clientes;
import com.HijasDelMonte.Ecomerce.Models.Orden;
import com.HijasDelMonte.Ecomerce.Servicios.ClientesServicios;
import com.HijasDelMonte.Ecomerce.Servicios.OrdenServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
public class OrdenControlador {
    @Autowired
    OrdenServicios ordenServicios;
    @Autowired
    ClientesServicios clientesServicios;

    @GetMapping("/api/cliente/orden/{id}")
    public List<OrdenDTO> obtenerOrdenesDTO( @PathVariable Long id ){

        Clientes clientes = clientesServicios.findById(id);

        List<OrdenDTO> ordenes = clientes.getOrdenes().stream().map(OrdenDTO::new).collect(toList());
        return ordenes;
    }

    @PostMapping("/api/cliente/orden")
    public ResponseEntity<Object> crearOrden(@RequestParam long idCliente){

        Clientes clientes = clientesServicios.findById(idCliente);

        if ( clientes == null ){
            return new ResponseEntity<>("El cliente no existe", HttpStatus.FORBIDDEN);}

        if (clientes.getOrdenes().stream().filter( ordenPagada -> !ordenPagada.isComprado()).collect(toList()).size() > 1){
            return new ResponseEntity<>("Ya tienes una orden en proceso", HttpStatus.FORBIDDEN);
        } else if (clientes.getOrdenes().stream().filter( ordenPagada -> !ordenPagada.isComprado()).collect(toList()).size() == 1){
            return new ResponseEntity<>("Orden en proceso", HttpStatus.OK);}

        Orden nuevaOrden = new Orden(0, 0,true, false);
        clientes.añadirOrden(nuevaOrden);
        ordenServicios.guardarOrden(nuevaOrden);
        return new ResponseEntity<>("Orden creada", HttpStatus.CREATED);
    }
}
