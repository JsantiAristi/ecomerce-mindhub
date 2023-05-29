package com.HijasDelMonte.Ecomerce.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Accesorios {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String nombre;
    private TipoAccesorio tipoAccesorio;
    private String color;
    private String descripcion;
    private String foto;
    private int stock;
    private double precio;
    private boolean activo;
    @OneToMany(mappedBy = "accesorios", fetch = FetchType.EAGER)
    private Set<ProductosSeleccionados> productosSeleccionadosSet = new HashSet<>();
    public Accesorios() {}

    //    Constructor
    public Accesorios(String nombre, TipoAccesorio tipoAccesorio, String color, String descripcion, String foto, int stock, double precio, boolean activo) {
        this.nombre = nombre;
        this.tipoAccesorio = tipoAccesorio;
        this.color = color;
        this.descripcion = descripcion;
        this.foto = foto;
        this.stock = stock;
        this.precio = precio;
        this.activo = activo;
    }

    //Metodos
    public void añadirProducto(ProductosSeleccionados productosSeleccionados){
        productosSeleccionados.setAccesorios(this);
        productosSeleccionadosSet.add(productosSeleccionados);
    }


    //    Getters
    public Long getId() {return id;}
    public String getNombre() {return nombre;}
    public TipoAccesorio getTipoAccesorio() {return tipoAccesorio;}
    public String getColor() {return color;}
    public String getDescripcion() {return descripcion;}
    public String getFoto() {return foto;}
    public int getStock() {return stock;}
    public double getPrecio() {return precio;}
    public boolean isActivo() {return activo;}
    public Set<ProductosSeleccionados> getProductosSeleccionadosSet() {return productosSeleccionadosSet;}


    //    Setters
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setTipoAccesorio(TipoAccesorio tipoAccesorio) {this.tipoAccesorio = tipoAccesorio;}
    public void setColor(String color) {this.color = color;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setFoto(String foto) {this.foto = foto;}
    public void setStock(int stock) {this.stock = stock;}
    public void setPrecio(double precio) {this.precio = precio;}
    public void setActivo(boolean activo) {this.activo = activo;}
}
