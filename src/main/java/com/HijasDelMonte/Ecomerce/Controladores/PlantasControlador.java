package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.PlantasDTO;
import com.HijasDelMonte.Ecomerce.Models.Plantas;
import com.HijasDelMonte.Ecomerce.Servicios.PlantasServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlantasControlador {
    @Autowired
    PlantasServicios plantasServicios;

    @GetMapping("/api/plantas")
    public List<PlantasDTO> obtenerPlantas(){return plantasServicios.obtenerPlantasDTO();}

    @PatchMapping("/api/clients/products")
    public ResponseEntity<Object> changeInfo(@RequestBody Plantas plantas){

        Plantas plantaACambiar = plantasServicios.obtenerPlanta(plantas.getId());

        if( plantaACambiar != null ){
            if ( !plantas.getFoto().equalsIgnoreCase(plantaACambiar.getFoto()) ){
                plantaACambiar.setFoto( plantas.getFoto() );
                plantasServicios.guardarPlanta( plantaACambiar );
                return new ResponseEntity<>("La foto fué cambiada" , HttpStatus.ACCEPTED);
            } else if ( !plantas.getNombre().equalsIgnoreCase(plantaACambiar.getNombre()) ) {
                plantaACambiar.setNombre( plantas.getNombre() );
                plantasServicios.guardarPlanta( plantaACambiar );
                return new ResponseEntity<>("El nombre fué cambiado" , HttpStatus.ACCEPTED);
            } else if ( !plantas.getDescripcion().equalsIgnoreCase(plantaACambiar.getDescripcion()) ){
                plantaACambiar.setDescripcion( plantas.getDescripcion() );
                plantasServicios.guardarPlanta( plantaACambiar );
                return new ResponseEntity<>("La descripción fué cambiada" , HttpStatus.ACCEPTED);
            } else if ( !(plantas.getPrecio() == plantaACambiar.getPrecio()) ) {
                plantaACambiar.setPrecio( plantas.getPrecio() );
                plantasServicios.guardarPlanta( plantaACambiar );
                return new ResponseEntity<>("El precio fué cambiado" , HttpStatus.ACCEPTED);
            } else if ( !(plantas.getStock() == plantaACambiar.getStock()) ) {
                plantaACambiar.setStock( plantas.getStock() );
                plantasServicios.guardarPlanta( plantaACambiar );
                return new ResponseEntity<>("La cantidad fué cambiada" , HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("No hubo cambios", HttpStatus.ACCEPTED);}
        } else {
            return new ResponseEntity<>("Este producto no existe" , HttpStatus.BAD_REQUEST);}
    }
}
