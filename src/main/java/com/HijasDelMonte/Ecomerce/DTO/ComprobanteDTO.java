package com.HijasDelMonte.Ecomerce.DTO;

import com.HijasDelMonte.Ecomerce.Models.CardColor;
import com.HijasDelMonte.Ecomerce.Models.CardType;
import com.HijasDelMonte.Ecomerce.Models.Comprobante;
import com.HijasDelMonte.Ecomerce.Models.Orden;

import java.time.LocalDateTime;

public class ComprobanteDTO {
    private long id;
    private CardType tipoTarjeta;
    private CardColor colorTarjeta;
    private LocalDateTime fecha;
    private Orden orden;

    public ComprobanteDTO(Comprobante comprobante) {
        this.id = comprobante.getId();
        this.tipoTarjeta = comprobante.getTipoTarjeta();
        this.colorTarjeta = comprobante.getColorTarjeta();
        this.fecha = comprobante.getFecha();
        this.orden = comprobante.getOrden();
    }

    public long getId() {return id;}
    public CardType getTipoTarjeta() {return tipoTarjeta;}
    public CardColor getColorTarjeta() {return colorTarjeta;}
    public LocalDateTime getFecha() {return fecha;}
    public Orden getOrden() {return orden;}
}
