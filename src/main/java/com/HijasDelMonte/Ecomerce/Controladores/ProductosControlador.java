package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.ProductosDTO;
import com.HijasDelMonte.Ecomerce.Models.Categorias;
import com.HijasDelMonte.Ecomerce.Models.Productos;
import com.HijasDelMonte.Ecomerce.Models.TipoProducto;
import com.HijasDelMonte.Ecomerce.Servicios.ProductosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ProductosControlador {
    @Autowired
    ProductosServicios productosServicios;

    @GetMapping("/api/productos")
    public List<ProductosDTO> obtenerProductos(){return productosServicios.obtenerProductosDTO();}
    @GetMapping("/api/productos/plantas")
    public List<ProductosDTO> obtenerPlantas(){return productosServicios.obtenerProductosDTO().stream().filter( productosDTO -> productosDTO.getCategorias().equals(Categorias.PLANTAS)).collect(toList());}
    @GetMapping("/api/productos/accesorios")
    public List<ProductosDTO> obtenerAccesorios(){return productosServicios.obtenerProductosDTO().stream().filter( productosDTO -> productosDTO.getCategorias().equals(Categorias.ACCESORIOS)).collect(toList());}
    @GetMapping("/api/productos/cursos")
    public List<ProductosDTO> obtenerCursos(){return productosServicios.obtenerProductosDTO().stream().filter( productosDTO -> productosDTO.getCategorias().equals(Categorias.TALLERES)).collect(toList());}
    @GetMapping("/api/productos/{id}")
    public ProductosDTO obtenerPlantas(@PathVariable long id ){return productosServicios.obtenerProductoDTO(id);}

    @PostMapping("/api/plantas")
    public ResponseEntity<Object> addProduct(@RequestBody Productos plantas){

        String description = null;
        String image = null;
        List<ProductosDTO> productsDTO = productosServicios.obtenerProductosDTO();

        if ( plantas.getNombre().isBlank() ){
            return new ResponseEntity<>("El nombre del producto no puede estar vacío", HttpStatus.FORBIDDEN);
        } else if ( !productosServicios.obtenerProductosDTO().stream().filter( plantaDB -> plantaDB.getNombre().equalsIgnoreCase(plantas.getNombre()) && plantaDB.isActivo()).collect(toList()).isEmpty() ){
            return new ResponseEntity<>("El nombre " + plantas.getNombre() + " ya está en uso", HttpStatus.FORBIDDEN);
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

        Productos nuevaPlanta = new Productos( plantas.getNombre(), plantas.getTipoProdcuto(),
                description, plantas.getFoto(), plantas.getStock(), plantas.getPrecio(), true, plantas.getCategorias());
        productosServicios.guardarPlanta(nuevaPlanta);

        return new ResponseEntity<>(HttpStatus.CREATED);}

    @PutMapping("/api/plantas")
    public ResponseEntity<Object> changeInfo(@RequestBody Productos plantas){

        Productos plantaACambiar = productosServicios.obtenerPlanta(plantas.getId());

        if( plantaACambiar != null ){
            if ( !plantas.getFoto().equalsIgnoreCase(plantaACambiar.getFoto()) ){
                plantaACambiar.setFoto( plantas.getFoto() );
                productosServicios.guardarPlanta( plantaACambiar );
                return new ResponseEntity<>("La foto fué cambiada" , HttpStatus.ACCEPTED);
            } else if ( !plantas.getNombre().equalsIgnoreCase(plantaACambiar.getNombre()) ) {
                plantaACambiar.setNombre( plantas.getNombre() );
                productosServicios.guardarPlanta( plantaACambiar );
                return new ResponseEntity<>("El nombre fué cambiado" , HttpStatus.ACCEPTED);
            }  else if ( !plantas.getDescripcion().equalsIgnoreCase(plantaACambiar.getDescripcion()) ){
                plantaACambiar.setDescripcion( plantas.getDescripcion() );
                productosServicios.guardarPlanta( plantaACambiar );
                return new ResponseEntity<>("La descripción fué cambiada" , HttpStatus.ACCEPTED);
            }else if ( !(plantas.getPrecio() == plantaACambiar.getPrecio()) ) {
                plantaACambiar.setPrecio( plantas.getPrecio() );
                productosServicios.guardarPlanta( plantaACambiar );
                return new ResponseEntity<>("El precio fué cambiado" , HttpStatus.ACCEPTED);
            } else if ( !(plantas.getStock() == plantaACambiar.getStock()) ) {
                plantaACambiar.setStock( plantas.getStock() );
                productosServicios.guardarPlanta( plantaACambiar );
                return new ResponseEntity<>("La cantidad fué cambiada" , HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("No hubo cambios", HttpStatus.ACCEPTED);}
        } else {
            return new ResponseEntity<>("Este producto no existe" , HttpStatus.BAD_REQUEST);}
    }
}
