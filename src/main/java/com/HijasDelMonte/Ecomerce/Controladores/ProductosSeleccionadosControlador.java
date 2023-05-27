package com.HijasDelMonte.Ecomerce.Controladores;

import ch.qos.logback.core.net.server.Client;
import com.HijasDelMonte.Ecomerce.DTO.PlantaSeleccionadaDTO;
import com.HijasDelMonte.Ecomerce.DTO.ProductosSeleccionadosDTO;
import com.HijasDelMonte.Ecomerce.Models.Clientes;
import com.HijasDelMonte.Ecomerce.Models.Orden;
import com.HijasDelMonte.Ecomerce.Models.Plantas;
import com.HijasDelMonte.Ecomerce.Models.ProductosSeleccionados;
import com.HijasDelMonte.Ecomerce.Servicios.ClientesServicios;
import com.HijasDelMonte.Ecomerce.Servicios.OrdenServicios;
import com.HijasDelMonte.Ecomerce.Servicios.PlantasServicios;
import com.HijasDelMonte.Ecomerce.Servicios.ProductosSeleccionadosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class ProductosSeleccionadosControlador {
    @Autowired
    ProductosSeleccionadosServicio productosSeleccionadosServicio;
    @Autowired
    PlantasServicios plantasServicios;
    @Autowired
    OrdenServicios ordenServicios;
    @Autowired
    ClientesServicios clientesServicios;

    @GetMapping("/api/carrito")
    public List<ProductosSeleccionadosDTO> obtenerProductos() {
        return productosSeleccionadosServicio.obtenerProductosSeleccionadosDTO();
    }

    @PostMapping("/api/cliente/carrito")
    public ResponseEntity<Object> añadirProductoSeleccionado(@RequestBody PlantaSeleccionadaDTO plantaSeleccionadaDTO){

        Clientes clientes = clientesServicios.findById(plantaSeleccionadaDTO.getIdCliente());
        List<Orden> ordenes = clientes.getOrdenes().stream().filter( ordenPagada -> !ordenPagada.isComprado()).collect(toList());
        Orden orden = ordenes.get(0);
        Plantas planta = plantasServicios.obtenerPlanta(plantaSeleccionadaDTO.getId());

        if( orden == null ){
            return new ResponseEntity<>("Ya tienes una orden" , HttpStatus.FORBIDDEN);
        }

        ProductosSeleccionados nuevoProductosSeleccionado = new ProductosSeleccionados(plantaSeleccionadaDTO.getUnidadesSeleccionadas(), planta.getPrecio()*plantaSeleccionadaDTO.getUnidadesSeleccionadas(), false, true);
        planta.añadirProducto(nuevoProductosSeleccionado);
        orden.añadirProducto(nuevoProductosSeleccionado);
        productosSeleccionadosServicio.guardarProductoSeleccionado(nuevoProductosSeleccionado);

        orden.setUnidadesTotales(orden.getUnidadesTotales() + plantaSeleccionadaDTO.getUnidadesSeleccionadas());
        orden.setPrecioTotal(orden.getPrecioTotal() + planta.getPrecio()*plantaSeleccionadaDTO.getUnidadesSeleccionadas());
        ordenServicios.guardarOrden(orden);

        return new ResponseEntity<>("Producto añadido", HttpStatus.CREATED);
    }
}
