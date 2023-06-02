package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.ProductoSeleccionadoDTO;
import com.HijasDelMonte.Ecomerce.DTO.ProductosSeleccionadosDTO;
import com.HijasDelMonte.Ecomerce.Models.*;
import com.HijasDelMonte.Ecomerce.Servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ProductosSeleccionadosControlador {
    @Autowired
    ProductosSeleccionadosServicio productosSeleccionadosServicio;
    @Autowired
    ProductosServicios productosServicios;
    @Autowired
    OrdenServicios ordenServicios;
    @Autowired
    ClientesServicios clientesServicios;

    @GetMapping("/api/carrito")
    public List<ProductosSeleccionadosDTO> obtenerProductos() {
        return productosSeleccionadosServicio.obtenerProductosSeleccionadosDTO();
    }

    @PostMapping("/api/cliente/carrito")
    public ResponseEntity<Object> añadirProductoSeleccionado(Authentication authentication, @RequestBody ProductoSeleccionadoDTO productoSeleccionadoDTO){

        Clientes cliente = clientesServicios.obtenerClienteAutenticado(authentication);
        List<Orden> ordenes = cliente.getOrdenes().stream().filter( ordenPagada -> !ordenPagada.isComprado()).collect(toList());
        Orden orden = ordenes.get(0);
        Productos producto = productosServicios.obtenerPlanta(productoSeleccionadoDTO.getId());
        double precioTotal = 0;
        int cantidadTotal = 0;

        if( orden == null ){
            return new ResponseEntity<>("Ya tienes una orden" , HttpStatus.FORBIDDEN);
        } else if ( !cliente.isValido() ) {
            return new ResponseEntity<>("Tu cuenta a sido cancelada" , HttpStatus.FORBIDDEN);
        } else if ( producto == null ) {
            return new ResponseEntity<>("Ha ocurrido un error, el producto que intentas añadir no existe, por favor, vuelve a intentarlo" , HttpStatus.FORBIDDEN);}

        if ( productoSeleccionadoDTO.getUnidadesSeleccionadas() < 1 ){
            return new ResponseEntity<>("Ha ocurrido un error, el producto que intentas añadir tiene unidades menores a 1, por favor, vuelve a intentarlo" , HttpStatus.FORBIDDEN);
        } else if ( producto.getStock() == 0 ) {
            return new ResponseEntity<>("Ha ocurrido un error, el producto que intentas añadir no tiene stock, por favor, vuelve a intentarlo" , HttpStatus.FORBIDDEN);
        } else if ( productoSeleccionadoDTO.getUnidadesSeleccionadas() > producto.getStock() ) {
            return new ResponseEntity<>("Ha ocurrido un error, el producto que intentas añadir no tiene el stock que tratas de añadir a la orden, por favor, vuelve a intentarlo" , HttpStatus.FORBIDDEN);}

        ProductosSeleccionados nuevoProductosSeleccionado = new ProductosSeleccionados(productoSeleccionadoDTO.getUnidadesSeleccionadas(), producto.getPrecio(), producto.getPrecio()*productoSeleccionadoDTO.getUnidadesSeleccionadas(),true);
            producto.añadirProducto(nuevoProductosSeleccionado);
            orden.añadirProducto(nuevoProductosSeleccionado);
            productosSeleccionadosServicio.guardarProductoSeleccionado(nuevoProductosSeleccionado);

        for ( ProductosSeleccionados productoSelec : orden.getProductosSeleccionadosSet() ){
            precioTotal += productoSelec.getPrecio();
            cantidadTotal += productoSelec.getCantidad();
        };

        orden.setUnidadesTotales(cantidadTotal);
        orden.setPrecioTotal(precioTotal);
        ordenServicios.guardarOrden(orden);

        return new ResponseEntity<>("Producto añadido a la orden", HttpStatus.CREATED);
    }
}
