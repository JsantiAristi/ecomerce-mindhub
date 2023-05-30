package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.Categorias;
import com.HijasDelMonte.Ecomerce.Models.Productos;
import com.HijasDelMonte.Ecomerce.Models.TipoProducto;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

public class ProductosDTO {
    private Long id;
    private String nombre;
    private TipoProducto tipoProducto;
    private String descripcion;
    private String foto;
    private int stock;
    private double precio;
    private boolean activo;
    private Categorias categorias;
    private Set<ProductosSeleccionadosDTO> productosSeleccionadosSet;

    public ProductosDTO(Productos productos) {
        this.id = productos.getId();
        this.nombre = productos.getNombre();
        this.tipoProducto = productos.getTipoProdcuto();
        this.descripcion = productos.getDescripcion();
        this.foto = productos.getFoto();
        this.stock = productos.getStock();
        this.precio = productos.getPrecio();
        this.activo = productos.isActivo();
        this.categorias = productos.getCategorias();
        this.productosSeleccionadosSet = productos.getProductosSeleccionadosSet().stream().map(ProductosSeleccionadosDTO::new).collect(toSet());
    }

    public Long getId() {return id;}
    public String getNombre() {return nombre;}
    public TipoProducto getTipoProducto() {return tipoProducto;}
    public Categorias getCategorias() {return categorias;}
    public String getDescripcion() {return descripcion;}
    public String getFoto() {return foto;}
    public int getStock() {return stock;}
    public double getPrecio() {return precio;}
    public boolean isActivo() {return activo;}
    public ProductosDTO(Categorias categorias) {
        this.categorias = categorias;
    }
    public Set<ProductosSeleccionadosDTO> getProductosSeleccionadosSet() {return productosSeleccionadosSet;}
}
