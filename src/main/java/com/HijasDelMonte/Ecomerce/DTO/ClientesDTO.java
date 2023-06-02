package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.Clientes;

import java.time.LocalDate;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ClientesDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private long telefono;
    private LocalDate fechaNacimiento;
    private String email;
    private boolean valido;
    private Set<OrdenDTO> ordenDTO;

    public ClientesDTO(Clientes cliente) {
        this.id=cliente.getId();
        this.nombre=cliente.getNombre();
        this.apellido=cliente.getApellido();
        this.dni=cliente.getDni();
        this.telefono=cliente.getTelefono();
        this.fechaNacimiento=cliente.getFechaNacimiento();
        this.email=cliente.getEmail();
        this.valido=cliente.isValido();
        this.ordenDTO=cliente.getOrdenes().stream().map(OrdenDTO::new).collect(toSet());
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
    public String getDni() {
        return dni;
    }
    public long getTelefono() {
        return telefono;
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
    public Set<OrdenDTO> getOrdenDTO() {return ordenDTO;}
}
