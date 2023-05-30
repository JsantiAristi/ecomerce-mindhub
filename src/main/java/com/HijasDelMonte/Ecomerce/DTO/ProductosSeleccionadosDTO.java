package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.ProductosSeleccionados;

public class ProductosSeleccionadosDTO {
    private long id;
    private long idOrden;
    private String nombre;
    private int cantidad;
    private double precio;
    private boolean activo;

    public ProductosSeleccionadosDTO(ProductosSeleccionados productosSeleccionados) {
        this.id = productosSeleccionados.getId();
        this.idOrden = productosSeleccionados.getOrden().getId();
        this.nombre = productosSeleccionados.getProductos().getNombre();
        this.cantidad = productosSeleccionados.getCantidad();
        this.precio = productosSeleccionados.getPrecio();
        this.activo = productosSeleccionados.isActivo();
    }

    public long getId() {return id;}
    public long getIdOrden() {return idOrden;}
    public String getNombre() {return nombre;}
    public int getCantidad() {return cantidad;}
    public double getPrecio() {return precio;}
    public boolean isActivo() {return activo;}
}
