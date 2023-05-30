package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.ProductoSeleccionadoDTO;
import com.HijasDelMonte.Ecomerce.DTO.ProductosSeleccionadosDTO;
import com.HijasDelMonte.Ecomerce.Models.*;
import com.HijasDelMonte.Ecomerce.Servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> añadirProductoSeleccionado(@RequestBody ProductoSeleccionadoDTO productoSeleccionadoDTO){

        Clientes clientes = clientesServicios.findById(productoSeleccionadoDTO.getIdCliente());
        List<Orden> ordenes = clientes.getOrdenes().stream().filter( ordenPagada -> !ordenPagada.isComprado()).collect(toList());
        Orden orden = ordenes.get(0);
        Productos producto = productosServicios.obtenerPlanta(productoSeleccionadoDTO.getId());

        if( orden == null ){
            return new ResponseEntity<>("Ya tienes una orden" , HttpStatus.FORBIDDEN);
        }

            ProductosSeleccionados nuevoProductosSeleccionado = new ProductosSeleccionados(productoSeleccionadoDTO.getUnidadesSeleccionadas(), producto.getPrecio()*productoSeleccionadoDTO.getUnidadesSeleccionadas(), true);
            producto.añadirProducto(nuevoProductosSeleccionado);
            orden.añadirProducto(nuevoProductosSeleccionado);
            productosSeleccionadosServicio.guardarProductoSeleccionado(nuevoProductosSeleccionado);

            orden.setUnidadesTotales(orden.getUnidadesTotales() + productoSeleccionadoDTO.getUnidadesSeleccionadas());
            orden.setPrecioTotal(orden.getPrecioTotal() + producto.getPrecio()*productoSeleccionadoDTO.getUnidadesSeleccionadas());
            ordenServicios.guardarOrden(orden);

        return new ResponseEntity<>("Producto añadido", HttpStatus.CREATED);
    }
}
