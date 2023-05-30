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


    @PutMapping("/api/cliente/carrito/suma")
    public ResponseEntity<Object> sumarProducto(@RequestParam long idProducto){
        ProductosSeleccionados productosSeleccionado= productosSeleccionadosServicio.obtenerProducto(idProducto);
        Orden orden= productosSeleccionado.getOrden();
        productosSeleccionado.setCantidad(productosSeleccionado.getCantidad()+1);
        productosSeleccionadosServicio.guardarProductoSeleccionado(productosSeleccionado);
        orden.setUnidadesTotales(orden.getUnidadesTotales()+1);
        ordenServicios.guardarOrden(orden);
        return new ResponseEntity<>("Producto añadido", HttpStatus.OK);
    }

    @PutMapping("/api/cliente/carrito/resta")
    public ResponseEntity<Object> restarProducto(@RequestParam long idProducto){
        ProductosSeleccionados productosSeleccionado= productosSeleccionadosServicio.obtenerProducto(idProducto);
        Orden orden= productosSeleccionado.getOrden();
        if(productosSeleccionado.getCantidad()==1){
            return new ResponseEntity<>("La unidade seleccionada actualmente es uno no puede seguir restando, si deseas puedes eliminarlo" , HttpStatus.FORBIDDEN);
        }
        productosSeleccionado.setCantidad(productosSeleccionado.getCantidad()-1);
        productosSeleccionadosServicio.guardarProductoSeleccionado(productosSeleccionado);
        orden.setUnidadesTotales(orden.getUnidadesTotales()-1);
        ordenServicios.guardarOrden(orden);
        return new ResponseEntity<>("Unidad eliminada", HttpStatus.OK);
    }
}
