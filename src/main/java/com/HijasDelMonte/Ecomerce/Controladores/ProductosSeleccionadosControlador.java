package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.ProductosSeleccionadosDTO;
import com.HijasDelMonte.Ecomerce.Models.Orden;
import com.HijasDelMonte.Ecomerce.Models.Plantas;
import com.HijasDelMonte.Ecomerce.Models.ProductosSeleccionados;
import com.HijasDelMonte.Ecomerce.Servicios.OrdenServicios;
import com.HijasDelMonte.Ecomerce.Servicios.PlantasServicios;
import com.HijasDelMonte.Ecomerce.Servicios.ProductosSeleccionadosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductosSeleccionadosControlador {
    @Autowired
    ProductosSeleccionadosServicio productosSeleccionadosServicio;
    @Autowired
    PlantasServicios plantasServicios;
    @Autowired
    OrdenServicios ordenServicios;

    @GetMapping("/api/carrito")
    public List<ProductosSeleccionadosDTO> obtenerProductos() {
        return productosSeleccionadosServicio.obtenerProductosSeleccionadosDTO();
    }

    @PostMapping("/api/cliente/carrito")
    public ResponseEntity<Object> agregarCarrito(@RequestParam long idProducto){

        Plantas plantas = plantasServicios.obtenerPlanta(idProducto);

        Orden nuevaOrden = new Orden(1,plantas.getPrecio(),true);
        ordenServicios.guardarOrden(nuevaOrden);

        ProductosSeleccionados nuevoProductoCarrito = new ProductosSeleccionados(1,plantas.getPrecio(),true);
        plantas.añadirProducto(nuevoProductoCarrito);
        nuevaOrden.añadirProducto(nuevoProductoCarrito);
        productosSeleccionadosServicio.guardarProductoSeleccionado(nuevoProductoCarrito);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
