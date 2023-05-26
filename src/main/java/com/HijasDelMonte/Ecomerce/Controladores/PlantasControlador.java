package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.PlantasDTO;
import com.HijasDelMonte.Ecomerce.Models.Plantas;
import com.HijasDelMonte.Ecomerce.Servicios.PlantasServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class PlantasControlador {
    @Autowired
    PlantasServicios plantasServicios;

    @GetMapping("/api/plantas")
    public List<PlantasDTO> obtenerPlantas(){return plantasServicios.obtenerPlantasDTO();}

    @PostMapping("/api/plantas")
    public ResponseEntity<Object> addProduct(@RequestBody Plantas plantas){

        String description = null;
        String image = null;
        List<PlantasDTO> productsDTO = plantasServicios.obtenerPlantasDTO();

        if ( plantas.getDescripcion().isBlank() ){
            description = "Sin descripción";
        } else if ( !plantas.getDescripcion().isBlank()) {
            description = plantas.getDescripcion();
        }

        if ( plantas.getFoto().isBlank() ){
            image = "../../assets/agregar-producto.png";
        } else if ( !plantas.getFoto().isBlank()) {
            image = plantas.getFoto();
        }

        if( !(productsDTO.stream().filter( productsDB -> productsDB.getNombre().equalsIgnoreCase(plantas.getNombre())).collect(toList()).size() == 0) ){
            return new ResponseEntity<>("Este nombre ya existe en otro producto" , HttpStatus.BAD_REQUEST);
        }

        Plantas nuevaPlanta = new Plantas( plantas.getNombre(), plantas.getTipoPlanta(), plantas.getColor(),
                description, plantas.getFoto(), plantas.getStock(), plantas.getPrecio(), true);
        plantasServicios.guardarPlanta(nuevaPlanta);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/api/plantas")
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
            } else if ( !plantas.getColor().equalsIgnoreCase(plantaACambiar.getColor()) ){
                plantaACambiar.setColor( plantas.getColor() );
                plantasServicios.guardarPlanta( plantaACambiar );
                return new ResponseEntity<>("El color fué cambiado" , HttpStatus.ACCEPTED);
            } else if ( !plantas.getDescripcion().equalsIgnoreCase(plantaACambiar.getDescripcion()) ){
                plantaACambiar.setDescripcion( plantas.getDescripcion() );
                plantasServicios.guardarPlanta( plantaACambiar );
                return new ResponseEntity<>("La descripción fué cambiada" , HttpStatus.ACCEPTED);
            }else if ( !(plantas.getPrecio() == plantaACambiar.getPrecio()) ) {
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
