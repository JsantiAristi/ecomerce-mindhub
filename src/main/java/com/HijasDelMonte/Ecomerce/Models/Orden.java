package com.HijasDelMonte.Ecomerce.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO , generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private int unidadesTotales;
    private double precioTotal;
    private boolean activo;
    @OneToMany(mappedBy = "orden", fetch = FetchType.EAGER)
    private Set<ProductosSeleccionados> productosSeleccionadosSet = new HashSet<>();

    public Orden() {}

    public Orden(int unidadesTotales, double precioTotal, boolean activo) {
        this.unidadesTotales = unidadesTotales;
        this.precioTotal = precioTotal;
        this.activo = activo;
    }

    // Método para añadir los productos seleccionados
    public void añadirProducto(ProductosSeleccionados productosSeleccionados){
        productosSeleccionados.setOrden(this);
        productosSeleccionadosSet.add(productosSeleccionados);
    }

//    GETTERS
    public long getId() {return id;}
    public int getUnidadesTotales() {return unidadesTotales;}
    public double getPrecioTotal() {return precioTotal;}
    public boolean isActivo() {return activo;}
    public Set<ProductosSeleccionados> getProductosSeleccionadosSet() {return productosSeleccionadosSet;}

//    SETTERS
    public void setUnidadesTotales(int unidadesTotales) {this.unidadesTotales = unidadesTotales;}
    public void setPrecioTotal(double precioTotal) {this.precioTotal = precioTotal;}
    public void setActivo(boolean activo) {this.activo = activo;}
}
