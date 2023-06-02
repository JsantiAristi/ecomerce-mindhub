package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.OrdenDTO;
import com.HijasDelMonte.Ecomerce.DTO.ProductoSeleccionadoDTO;
import com.HijasDelMonte.Ecomerce.Models.Clientes;
import com.HijasDelMonte.Ecomerce.Models.Orden;
import com.HijasDelMonte.Ecomerce.Models.ProductosSeleccionados;
import com.HijasDelMonte.Ecomerce.Servicios.ClientesServicios;
import com.HijasDelMonte.Ecomerce.Servicios.OrdenServicios;
import com.HijasDelMonte.Ecomerce.Servicios.ProductosSeleccionadosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    @Autowired
    ProductosSeleccionadosServicio productosSeleccionadosServicio;

    @GetMapping("/api/cliente/orden")
    public List<OrdenDTO> obtenerOrdenesDTO(Authentication authentication){

        Clientes clientes = clientesServicios.obtenerClienteAutenticado(authentication);

        List<OrdenDTO> ordenes = clientes.getOrdenes().stream().map(OrdenDTO::new).collect(toList());
        return ordenes;
    }

    @PostMapping("/api/orden/actualizacion")
    public ResponseEntity<Object>actualizacionCompra(Authentication authentication, @RequestBody ProductoSeleccionadoDTO productoSeleccionadoDTO){
        Clientes cliente = clientesServicios.obtenerClienteAutenticado(authentication);
        ProductosSeleccionados producto = productosSeleccionadosServicio.obtenerProducto(productoSeleccionadoDTO.getId());
        Orden orden = cliente.getOrdenes().stream().filter(ordenPagada -> !ordenPagada.isComprado()).collect(toList()).get(0);
        double precioTotal = 0;
        int cantidadTotal = 0;

        if ( productoSeleccionadoDTO.getUnidadesSeleccionadas() > producto.getProductos().getStock() ){
            return new ResponseEntity<>("No se puede añadir más de " + producto.getProductos().getNombre() + " revisa el stock, por favor", HttpStatus.FORBIDDEN);
        }

        producto.setCantidad(productoSeleccionadoDTO.getUnidadesSeleccionadas());
        productosSeleccionadosServicio.guardarProductoSeleccionado(producto);

        for ( ProductosSeleccionados productoSelec : orden.getProductosSeleccionadosSet() ){
            productoSelec.setPrecioTotal( productoSelec.getPrecio()*productoSelec.getCantidad() );
            precioTotal += productoSelec.getPrecioTotal();
            cantidadTotal += productoSelec.getCantidad();
        };

        orden.setUnidadesTotales(cantidadTotal);
        orden.setPrecioTotal(precioTotal);
        ordenServicios.guardarOrden(orden);

        return new ResponseEntity<>("", HttpStatus.ACCEPTED);
    }


    @PostMapping("/api/cliente/orden")
    public ResponseEntity<Object> crearOrden(Authentication authentication){

        Clientes clientes = clientesServicios.obtenerClienteAutenticado(authentication);

        if ( clientes == null ){
            return new ResponseEntity<>("El cliente no existe", HttpStatus.FORBIDDEN);
        } else if ( !clientes.isValido() ) {
            return new ResponseEntity<>("Tu cuenta ha sido desactivada.", HttpStatus.FORBIDDEN);}

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
