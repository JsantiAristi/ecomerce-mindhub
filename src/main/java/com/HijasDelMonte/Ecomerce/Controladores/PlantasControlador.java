package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.PlantasDTO;
import com.HijasDelMonte.Ecomerce.Models.Categorias;
import com.HijasDelMonte.Ecomerce.Models.Plantas;
import com.HijasDelMonte.Ecomerce.Models.TipoPlanta;
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

    @GetMapping("/api/plantas/{id}")
    public PlantasDTO obtenerPlantas( @PathVariable long id ){return plantasServicios.obtenerPlantaDTO(id);}

    @PostMapping("/api/plantas")
    public ResponseEntity<Object> addProduct(@RequestBody Plantas plantas){

        String description = null;
        String image = null;
        List<PlantasDTO> productsDTO = plantasServicios.obtenerPlantasDTO();

        if ( plantas.getNombre().isBlank() ){
            return new ResponseEntity<>("El nombre del producto no puede estar vacío", HttpStatus.FORBIDDEN);
        } else if ( !plantasServicios.obtenerPlantasDTO().stream().filter( plantaDB -> plantaDB.getNombre().equalsIgnoreCase(plantas.getNombre()) && plantaDB.isActivo()).collect(toList()).isEmpty() ){
            return new ResponseEntity<>("El nombre " + plantas.getNombre() + " ya está en uso", HttpStatus.FORBIDDEN);
        } else if ( plantas.getTipoPlanta().equals(TipoPlanta.EXTERIOR) && plantas.getTipoPlanta().equals(TipoPlanta.INTERIOR)){
            return new ResponseEntity<>("Ingresa una opción valida", HttpStatus.FORBIDDEN);
        } else if ( plantas.getColor().isBlank() || plantas.getColor().matches("^[a-z A-Z]*$") ){
            return new ResponseEntity<>("El color no puede estar vacío o contener números", HttpStatus.FORBIDDEN);
        } else if (plantas.getStock() < 0 ) {
            return new ResponseEntity<>("No puedes tener un stock negativo", HttpStatus.FORBIDDEN);
        } else if ( plantas.getPrecio() < 0 ) {
            return new ResponseEntity<>("No puedes tener un precio negativo", HttpStatus.FORBIDDEN);}

        if ( plantas.getDescripcion().isBlank() ){
            description = "Sin descripción";
        } else if ( !plantas.getDescripcion().isBlank()) {
            description = plantas.getDescripcion();}

        if ( plantas.getFoto().isBlank() ){
            image = "../../assets/agregar-producto.png";
        } else if ( !plantas.getFoto().isBlank()) {
            image = plantas.getFoto();}

        Plantas nuevaPlanta = new Plantas( plantas.getNombre(), plantas.getTipoPlanta(), plantas.getColor(),
                description, plantas.getFoto(), plantas.getStock(), plantas.getPrecio(), true, Categorias.PLANTAS);
        plantasServicios.guardarPlanta(nuevaPlanta);

        return new ResponseEntity<>(HttpStatus.CREATED);}

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
