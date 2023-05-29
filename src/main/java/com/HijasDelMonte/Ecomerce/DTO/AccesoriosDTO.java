package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.Accesorios;
import com.HijasDelMonte.Ecomerce.Models.Categorias;
import com.HijasDelMonte.Ecomerce.Models.TipoAccesorio;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

public class AccesoriosDTO {
    private Long id;
    private String nombre;
    private TipoAccesorio tipoAccesorio;
    private String color;
    private String descripcion;
    private String foto;
    private int stock;
    private double precio;
    private boolean activo;
    private Categorias categorias;
    private Set<ProductosSeleccionadosDTO> productosSeleccionadosSet;

    //CONSTRUCTORES
    public AccesoriosDTO() {};

    public AccesoriosDTO(Accesorios accesorios) {
        this.nombre = accesorios.getNombre();
        this.tipoAccesorio = accesorios.getTipoAccesorio();
        this.color = accesorios.getColor();
        this.descripcion = accesorios.getDescripcion();
        this.foto = accesorios.getFoto();
        this.stock = accesorios.getStock();
        this.precio = accesorios.getPrecio();
        this.activo = accesorios.isActivo();
        this.productosSeleccionadosSet = accesorios.getProductosSeleccionadosSet().stream().map(ProductosSeleccionadosDTO::new).collect(toSet());
        this.categorias = accesorios.getCategorias();
    }

    //GETTERS
    public String getNombre() {return nombre;}
    public TipoAccesorio getTipoAccesorio() { return tipoAccesorio;}
    public String getColor() { return color;}
    public String getDescripcion() { return descripcion;}
    public String getFoto() { return foto;}
    public int getStock() { return stock;}
    public double getPrecio() { return precio;}
    public boolean isActivo() { return activo;}
    public Set<ProductosSeleccionadosDTO> getProductosSeleccionadosSet() {return productosSeleccionadosSet;}

    public Categorias getCategorias() {
        return categorias;
    }
}
