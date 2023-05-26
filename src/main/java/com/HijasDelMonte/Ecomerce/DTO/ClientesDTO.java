package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.Clientes;
import com.HijasDelMonte.Ecomerce.Models.Genero;

import java.time.LocalDate;

public class ClientesDTO {
    private Long id;

    private String nombre;

    private String apellido;

    private String cedulaCiudadania;

    private String telefono;

    private Genero genero;

    private LocalDate fechaNacimiento;
    private String email;

    private boolean valido;

    public ClientesDTO(Clientes cliente) {
        this.id=cliente.getId();
        this.nombre=cliente.getNombre();
        this.apellido=cliente.getApellido();
        this.cedulaCiudadania=cliente.getCedulaCiudadania();
        this.telefono=cliente.getTelefono();
        this.genero=cliente.getGenero();
        this.fechaNacimiento=cliente.getFechaNacimiento();
        this.email=cliente.getEmail();
        this.valido=cliente.getValido();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCedulaCiudadania() {
        return cedulaCiudadania;
    }

    public String getTelefono() {
        return telefono;
    }

    public Genero getGenero() {
        return genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public boolean isValido() {
        return valido;
    }
}
