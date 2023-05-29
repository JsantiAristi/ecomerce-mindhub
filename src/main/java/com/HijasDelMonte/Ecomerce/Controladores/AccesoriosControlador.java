package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.AccesoriosDTO;
import com.HijasDelMonte.Ecomerce.Models.Accesorios;
import com.HijasDelMonte.Ecomerce.Servicios.AccesoriosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class AccesoriosControlador {

    @Autowired
    AccesoriosServicios accesoriosServicios;

    @GetMapping("/api/accesorios")
    public List<AccesoriosDTO> obtenerAccesorios(){return accesoriosServicios.obtenerAccesoriosDTO(); }

    @PostMapping("/api/accesorios")
    public ResponseEntity<Object> addProduct(@RequestBody Accesorios accesorios){

        String description = null;
        String image = null;
        List<AccesoriosDTO> productsDTO = accesoriosServicios.obtenerAccesoriosDTO();

        if ( accesorios.getDescripcion().isBlank() ){
            description = "Sin descripción";
        } else if ( !accesorios.getDescripcion().isBlank()) {
            description = accesorios.getDescripcion();
        }

        if ( accesorios.getFoto().isBlank() ){
            image = "../../assets/agregar-producto.png";
        } else if ( !accesorios.getFoto().isBlank()) {
            image = accesorios.getFoto();
        }

        if( !(productsDTO.stream().filter( productsDB -> productsDB.getNombre().equalsIgnoreCase(accesorios.getNombre())).collect(toList()).size() == 0) ){
            return new ResponseEntity<>("Este nombre ya existe en otro producto" , HttpStatus.BAD_REQUEST);
        }

        Accesorios nuevoAccesorio = new Accesorios( accesorios.getNombre(), accesorios.getTipoAccesorio(), accesorios.getColor(),
                description, accesorios.getFoto(), accesorios.getStock(), accesorios.getPrecio(), true);
        accesoriosServicios.guardarAccesorio(nuevoAccesorio);

        return new ResponseEntity<>(HttpStatus.CREATED);}

    @PutMapping("/api/accesorios")
    public ResponseEntity<Object> changeInfo(@RequestBody Accesorios accesorios){

        Accesorios accesorioACambiar = accesoriosServicios.obtenerAccesorio(accesorios.getId());

        if( accesorioACambiar != null ){
            if ( !accesorios.getFoto().equalsIgnoreCase(accesorioACambiar.getFoto()) ){
                accesorioACambiar.setFoto( accesorios.getFoto() );
                accesoriosServicios.guardarAccesorio( accesorioACambiar );
                return new ResponseEntity<>("La foto fue cambiada" , HttpStatus.ACCEPTED);
            } else if ( !accesorios.getNombre().equalsIgnoreCase(accesorioACambiar.getNombre()) ) {
                accesorioACambiar.setNombre( accesorios.getNombre() );
                accesoriosServicios.guardarAccesorio( accesorioACambiar );
                return new ResponseEntity<>("El nombre fue cambiado" , HttpStatus.ACCEPTED);
            } else if ( !accesorios.getColor().equalsIgnoreCase(accesorioACambiar.getColor()) ){
                accesorioACambiar.setColor( accesorios.getColor() );
                accesoriosServicios.guardarAccesorio( accesorioACambiar );
                return new ResponseEntity<>("El color fue cambiado" , HttpStatus.ACCEPTED);
            } else if ( !accesorios.getDescripcion().equalsIgnoreCase(accesorioACambiar.getDescripcion()) ){
                accesorioACambiar.setDescripcion( accesorios.getDescripcion() );
                accesoriosServicios.guardarAccesorio( accesorioACambiar );
                return new ResponseEntity<>("La descripción fue cambiada" , HttpStatus.ACCEPTED);
            }else if ( !(accesorios.getPrecio() == accesorioACambiar.getPrecio()) ) {
                accesorioACambiar.setPrecio( accesorios.getPrecio() );
                accesoriosServicios.guardarAccesorio( accesorioACambiar );
                return new ResponseEntity<>("El precio fue cambiado" , HttpStatus.ACCEPTED);
            } else if ( !(accesorios.getStock() == accesorioACambiar.getStock()) ) {
                accesorioACambiar.setStock( accesorios.getStock() );
                accesoriosServicios.guardarAccesorio( accesorioACambiar );
                return new ResponseEntity<>("La cantidad fue cambiada" , HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("No hubo cambios", HttpStatus.ACCEPTED);}
        } else {
            return new ResponseEntity<>("Este producto no existe" , HttpStatus.BAD_REQUEST);}
    }
}
