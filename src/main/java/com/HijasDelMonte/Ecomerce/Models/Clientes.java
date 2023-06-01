package com.HijasDelMonte.Ecomerce.Models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String nombre;
    private String apellido;
    private String cedulaCiudadania;
    private long telefono;
    private Genero genero;
    private LocalDate fechaNacimiento;
    private String email;
    private String contraseña;
    private boolean valido;
    @OneToMany(mappedBy = "clientes", fetch = FetchType.EAGER)
    private Set<Orden> ordenes = new HashSet<>();

    @OneToMany(mappedBy = "clientes", fetch = FetchType.EAGER)
    private Set<Comprobante> comprobantes = new HashSet<>();

    public Clientes() {
    }

    public Clientes(String nombre, String apellido, String cedulaCiudadania, long telefono, Genero genero, LocalDate fechaNacimiento, String email, String contraseña, boolean valido){
        this.nombre=nombre;
        this.apellido=apellido;
        this.cedulaCiudadania=cedulaCiudadania;
        this.telefono =telefono;
        this.genero=genero;
        this.fechaNacimiento=fechaNacimiento;
        this.email=email;
        this.contraseña=contraseña;
        this.valido=valido;
    }

    // Método para añadir los productos seleccionados
    public void añadirOrden(Orden orden){
        orden.setClientes(this);
        ordenes.add(orden);
    }

    // Método para añadir los comprobantes
    public void añadirComprobante(Comprobante comprobante){
        comprobante.setClientes(this);
        comprobantes.add(comprobante);
    }

    public long getId() {return id;}
    public String getNombre() {return nombre;}
    public String getApellido() {return apellido;}
    public String getCedulaCiudadania() {return cedulaCiudadania;}
    public long getTelefono() {return telefono;}
    public Genero getGenero() {return genero;}
    public LocalDate getFechaNacimiento() {return fechaNacimiento;}
    public String getEmail() {return email;}
    public String getContraseña() {return contraseña;}
    public boolean isValido() {return valido;}
    public Set<Orden> getOrdenes() {return ordenes;}
    public Set<Comprobante> getComprobantes() {return comprobantes;}

    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setApellido(String apellido) {this.apellido = apellido;}
    public void setCedulaCiudadania(String cedulaCiudadania) {this.cedulaCiudadania = cedulaCiudadania;}
    public void setTelefono(long telefono) {this.telefono = telefono;}
    public void setGenero(Genero genero) {this.genero = genero;}
    public void setFechaNacimiento(LocalDate fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}
    public void setEmail(String email) {this.email = email;}
    public void setContraseña(String contraseña) {this.contraseña = contraseña;}
    public void setValido(boolean valido) {this.valido = valido;}
}
