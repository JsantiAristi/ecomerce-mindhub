package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.Plantas;
import com.HijasDelMonte.Ecomerce.Models.TipoPlanta;

public class PlantasDTO {
    private Long id;
    private String nombre;
    private TipoPlanta tipoPlanta;
    private String color;
    private String descripcion;
    private String foto;
    private int stock;
    private double precio;
    private boolean activo;

    public PlantasDTO(Plantas plantas) {
        this.id = plantas.getId();
        this.nombre = plantas.getNombre();
        this.tipoPlanta = plantas.getTipoPlanta();
        this.color = plantas.getColor();
        this.descripcion = plantas.getDescripcion();
        this.foto = plantas.getFoto();
        this.stock = plantas.getStock();
        this.precio = plantas.getPrecio();
        this.activo = plantas.isActivo();
    }

    public Long getId() {return id;}
    public String getNombre() {return nombre;}
    public TipoPlanta getTipoPlanta() {return tipoPlanta;}
    public String getColor() {return color;}
    public String getDescripcion() {return descripcion;}
    public String getFoto() {return foto;}
    public int getStock() {return stock;}
    public double getPrecio() {return precio;}
    public boolean isActivo() {return activo;}
}
