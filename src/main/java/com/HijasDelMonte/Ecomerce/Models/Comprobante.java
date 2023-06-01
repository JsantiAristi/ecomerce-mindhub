package com.HijasDelMonte.Ecomerce.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

}
