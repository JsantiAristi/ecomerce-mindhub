package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.Orden;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

public class OrdenDTO {
    private int unidadesTotales;
    private double precioTotal;
    private boolean activo;
    private Set<ProductosSeleccionadosDTO> productosSeleccionadosSet;

    public OrdenDTO(Orden orden) {
        this.unidadesTotales = orden.getUnidadesTotales();
        this.precioTotal = orden.getPrecioTotal();
        this.activo = orden.isActivo();
        this.productosSeleccionadosSet = orden.getProductosSeleccionadosSet().stream().map(ProductosSeleccionadosDTO::new).collect(toSet());
    }

    public int getUnidadesTotales() {return unidadesTotales;}
    public double getPrecioTotal() {return precioTotal;}
    public boolean isActivo() {return activo;}
    public Set<ProductosSeleccionadosDTO> getProductosSeleccionadosSet() {return productosSeleccionadosSet;}
}
