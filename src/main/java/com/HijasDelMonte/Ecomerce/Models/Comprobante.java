package com.HijasDelMonte.Ecomerce.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comprobante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO , generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private CardType tipoTarjeta;
    private CardColor colorTarjeta;
    private LocalDateTime fecha;
    @ManyToOne(fetch = FetchType.EAGER)
    private Clientes clientes;
    @OneToOne(targetEntity = Orden.class, fetch = FetchType.EAGER)
    private Orden orden;

    public Comprobante() {}

    public Comprobante(CardType tipoTarjeta, CardColor colorTarjeta, LocalDateTime fecha, Orden orden) {
        this.orden = orden;
        this.tipoTarjeta = tipoTarjeta;
        this.colorTarjeta = colorTarjeta;
        this.fecha = fecha;
    }

//    GETTERS
    public long getId() {return id;}
    public CardType getTipoTarjeta() {return tipoTarjeta;}
    public CardColor getColorTarjeta() {return colorTarjeta;}
    public LocalDateTime getFecha() {return fecha;}
    public Clientes getClientes() {return clientes;}
    public Orden getOrden() {return orden;}

    // SETTERS
    public void setTipoTarjeta(CardType tipoTarjeta) {this.tipoTarjeta = tipoTarjeta;}
    public void setColorTarjeta(CardColor colorTarjeta) {this.colorTarjeta = colorTarjeta;}
    public void setFecha(LocalDateTime fecha) {this.fecha = fecha;}
    public void setClientes(Clientes clientes) {this.clientes = clientes;}
    public void setOrden(Orden orden) {this.orden = orden;}
}
