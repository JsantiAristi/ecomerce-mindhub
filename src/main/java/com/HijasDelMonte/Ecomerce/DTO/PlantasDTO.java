package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.Categorias;
import com.HijasDelMonte.Ecomerce.Models.Plantas;
import com.HijasDelMonte.Ecomerce.Models.TipoPlanta;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

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
    private Categorias categorias;
    private Set<ProductosSeleccionadosDTO> productosSeleccionadosSet;


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
        this.productosSeleccionadosSet = plantas.getProductosSeleccionadosSet().stream().map(ProductosSeleccionadosDTO::new).collect(toSet());
        this.categorias = plantas.getCategorias();
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
    public Set<ProductosSeleccionadosDTO> getProductosSeleccionadosSet() {return productosSeleccionadosSet;}

    public PlantasDTO(Categorias categorias) {
        this.categorias = categorias;
    }
}
