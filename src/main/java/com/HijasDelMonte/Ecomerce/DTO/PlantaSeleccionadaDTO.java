package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.Categorias;
import com.HijasDelMonte.Ecomerce.Models.TipoPlanta;

public class PlantaSeleccionadaDTO {
    private Long id;
    private Long idCliente;
    private int unidadesSeleccionadas;
    private Categorias categorias;

    public PlantaSeleccionadaDTO() {}

    public Long getId() {return id;}
    public Long getIdCliente() {return idCliente;}
    public int getUnidadesSeleccionadas() {return unidadesSeleccionadas;}

    public Categorias getCategorias() {
        return categorias;
    }
}
