package com.HijasDelMonte.Ecomerce.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO , generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;
    private boolean validado;

    //CONSTRUCTORES
    public Clientes() {}
    public Clientes(String nombre, String apellido, String email, boolean validado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.validado = validado;
    }

    //GETTER Y SETTERS
    public long getId() {return id;}
    public String getNombre() {return nombre;}
    public String getApellido() {return apellido;}
    public String getEmail() {return email;}
    public boolean isValidado() {return validado;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setApellido(String apellido) {this.apellido = apellido;}
    public void setEmail(String email) {this.email = email;}
    public void setValidado(boolean validado) {this.validado = validado;}
}
