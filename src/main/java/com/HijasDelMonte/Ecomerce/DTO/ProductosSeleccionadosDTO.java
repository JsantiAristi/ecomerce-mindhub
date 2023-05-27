package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.ProductosSeleccionados;
import com.HijasDelMonte.Ecomerce.Models.TipoPlanta;

public class ProductosSeleccionadosDTO {
    private long id;
    private long idOrden;
    private String foto;
    private String nombre;
    private TipoPlanta tipoPlanta;
    private int cantidad;
    private double precio;
    private boolean activo;

    public ProductosSeleccionadosDTO(ProductosSeleccionados productosSeleccionados) {
        this.id = productosSeleccionados.getId();
        this.idOrden = productosSeleccionados.getOrden().getId();
        this.foto = productosSeleccionados.getPlantas().getFoto();
        this.nombre = productosSeleccionados.getPlantas().getNombre();
        this.tipoPlanta = productosSeleccionados.getPlantas().getTipoPlanta();
        this.cantidad = productosSeleccionados.getCantidad();
        this.precio = productosSeleccionados.getPrecio();
        this.activo = productosSeleccionados.isActivo();
    }

    public long getId() {return id;}
    public long getIdOrden() {return idOrden;}
    public String getFoto() {return foto;}
    public String getNombre() {return nombre;}
    public TipoPlanta getTipoPlanta() {return tipoPlanta;}
    public int getCantidad() {return cantidad;}
    public double getPrecio() {return precio;}
    public boolean isActivo() {return activo;}
}
