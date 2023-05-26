package com.HijasDelMonte.Ecomerce.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Accesorios {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String nombre;
    private TipoPlanta tipoPlanta;
    private String color;
    private String descripcion;
    private String foto;
    private int stock;
    private double precio;
    private boolean activo;

    public Accesorios() {}

    //    Constructor
    public Accesorios(String nombre, TipoPlanta tipoPlanta, String color, String descripcion, String foto, int stock, double precio, boolean activo) {
        this.nombre = nombre;
        this.tipoPlanta = tipoPlanta;
        this.color = color;
        this.descripcion = descripcion;
        this.foto = foto;
        this.stock = stock;
        this.precio = precio;
        this.activo = activo;
    }

    //    Getters
    public Long getId() {return id;}
    public String getNombre() {return nombre;}
    public TipoPlanta getTipoPlanta() {return tipoPlanta;}
    public String getColor() {return color;}
    public String getDescripcion() {return descripcion;}
    public String getFoto() {return foto;}
    public int getStock() {return stock;}
    public double getPrecio() {return precio;}
    public boolean isActivo() {return activo;}

    //    Setters
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setTipoPlanta(TipoPlanta tipoPlanta) {this.tipoPlanta = tipoPlanta;}
    public void setColor(String color) {this.color = color;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setFoto(String foto) {this.foto = foto;}
    public void setStock(int stock) {this.stock = stock;}
    public void setPrecio(double precio) {this.precio = precio;}
    public void setActivo(boolean activo) {this.activo = activo;}
}
