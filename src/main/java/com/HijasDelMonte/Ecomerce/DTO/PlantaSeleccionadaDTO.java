package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.TipoPlanta;

public class PlantaSeleccionadaDTO {
    private Long id;
    private Long idCliente;
    private int unidadesSeleccionadas;

    public PlantaSeleccionadaDTO() {}

    public Long getId() {return id;}
    public Long getIdCliente() {return idCliente;}
    public int getUnidadesSeleccionadas() {return unidadesSeleccionadas;}
}
