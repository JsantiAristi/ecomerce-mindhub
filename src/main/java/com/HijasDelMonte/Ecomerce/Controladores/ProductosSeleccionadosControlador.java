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
        if(productosSeleccionado.getCantidad()==0){
            return new ResponseEntity<>("Las unidades seleccionadas actualmente son cero no puede seguir restando" , HttpStatus.FORBIDDEN);
        }
        productosSeleccionado.setCantidad(productosSeleccionado.getCantidad()-1);
        productosSeleccionadosServicio.guardarProductoSeleccionado(productosSeleccionado);
        orden.setUnidadesTotales(orden.getUnidadesTotales()-1);
        ordenServicios.guardarOrden(orden);
        return new ResponseEntity<>("Unidad eliminada", HttpStatus.OK);
    }
}
