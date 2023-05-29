package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.Categorias;
import com.HijasDelMonte.Ecomerce.Models.ProductosSeleccionados;
import com.HijasDelMonte.Ecomerce.Models.TipoPlanta;

public class ProductosSeleccionadosDTO {
    private long id;
    private long idOrden;
    private String nombrePlanta;
    private int cantidad;
    private double precio;
    private boolean activo;
    private String nombreAccesorio;
    private String nombreCurso;

    public ProductosSeleccionadosDTO(ProductosSeleccionados productosSeleccionados) {
        this.id = productosSeleccionados.getId();
        this.idOrden = productosSeleccionados.getOrden().getId();
        this.nombrePlanta = productosSeleccionados.getPlantas().getNombre();
        this.cantidad = productosSeleccionados.getCantidad();
        this.precio = productosSeleccionados.getPrecio();
        this.activo = productosSeleccionados.isActivo();
        this.nombreAccesorio = productosSeleccionados.getAccesorios().getNombre();

    }

    public long getId() {return id;}
    public long getIdOrden() {return idOrden;}
    public String getNombrePlanta() {return nombrePlanta;}
    public int getCantidad() {return cantidad;}
    public double getPrecio() {return precio;}
    public boolean isActivo() {return activo;}
    public String getNombreAccesorio() { return nombreAccesorio;}
}
