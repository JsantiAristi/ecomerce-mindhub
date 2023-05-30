package com.HijasDelMonte.Ecomerce.Models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String nombre;
    private TipoProducto tipoProdcuto;
    private String descripcion;
    private String foto;
    private int stock;
    private double precio;
    private boolean activo;
    private Categorias categorias;
    @OneToMany(mappedBy = "productos", fetch = FetchType.EAGER)
    private Set<ProductosSeleccionados> productosSeleccionadosSet = new HashSet<>();

    public Productos() {}

//    Constructor
    public Productos(String nombre, TipoProducto tipoProdcuto, String descripcion, String foto, int stock, double precio, boolean activo, Categorias categorias) {
        this.nombre = nombre;
        this.tipoProdcuto = tipoProdcuto;
        this.descripcion = descripcion;
        this.foto = foto;
        this.stock = stock;
        this.precio = precio;
        this.activo = activo;
        this.categorias = categorias;
    }

    // Método para añadir los productos seleccionados
    public void añadirProducto(ProductosSeleccionados productosSeleccionados){
        productosSeleccionados.setProductos(this);
        productosSeleccionadosSet.add(productosSeleccionados);
    }

//    Getters
    public Long getId() {return id;}
    public String getNombre() {return nombre;}
    public TipoProducto getTipoProdcuto() {return tipoProdcuto;}
    public String getDescripcion() {return descripcion;}
    public String getFoto() {return foto;}
    public int getStock() {return stock;}
    public double getPrecio() {return precio;}
    public boolean isActivo() {return activo;}
    public Categorias getCategorias() {return categorias;}
    public Set<ProductosSeleccionados> getProductosSeleccionadosSet() {return productosSeleccionadosSet;}

    //    Setters
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setTipoProdcuto(TipoProducto tipoProdcuto) {this.tipoProdcuto = tipoProdcuto;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setFoto(String foto) {this.foto = foto;}
    public void setStock(int stock) {this.stock = stock;}
    public void setPrecio(double precio) {this.precio = precio;}
    public void setActivo(boolean activo) {this.activo = activo;}
    public void setCategorias(Categorias categorias) {this.categorias = categorias;}
}
