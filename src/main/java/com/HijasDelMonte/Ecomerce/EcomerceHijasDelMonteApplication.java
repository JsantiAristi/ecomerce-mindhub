package com.HijasDelMonte.Ecomerce;

import com.HijasDelMonte.Ecomerce.Models.Clientes;
import com.HijasDelMonte.Ecomerce.Models.Genero;
import com.HijasDelMonte.Ecomerce.Models.Plantas;
import com.HijasDelMonte.Ecomerce.Models.TipoPlanta;
import com.HijasDelMonte.Ecomerce.Repositorios.ClientesRepositorio;
import com.HijasDelMonte.Ecomerce.Repositorios.PlantasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class EcomerceHijasDelMonteApplication {

	@Autowired
	private ClientesRepositorio clientesRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(EcomerceHijasDelMonteApplication.class, args);
	}

	@Bean
	public CommandLineRunner IniciarDatos(PlantasRepositorio plantasRepositorio){
		return (args) -> {
			//Productos PLantas
			Plantas orquidea_rosa = new Plantas(
					"Orquidea Mini Premium Salitre en Cultivo", TipoPlanta.ORQUIDEA, "Rosa",
					"Purifica el aire y filtra toxinas al interior de la casa.",
					"../../assets/orquidea-rosa.jpg", 12, 2000, true );
			plantasRepositorio.save(orquidea_rosa);
			Plantas bromelia_blanca = new Plantas(
					"Bromelia Premium en Cultivo", TipoPlanta.BROMELIAS, "Blanco",
					"Se adapta perfectamente en espacios interiores y exteriores.",
					"../../assets/bromelia-blanca.jpg", 20, 1000, true );
			plantasRepositorio.save(bromelia_blanca);
			Plantas anturio_rojo = new Plantas(
					"Anturio Premium en Cultivo", TipoPlanta.ANTURIOS, "Rojo",
					"Requiere cuidados mínimos siendo una planta perfecta para las personas con poco tiempo disponible.",
					"../../assets/anturio-rojo.jpg", 0, 5000, true );
			plantasRepositorio.save(anturio_rojo);
			Plantas orquidea_blanca = new Plantas(
					"Orquidea Supreme Kasy en Cultivo", TipoPlanta.ORQUIDEA, "Blanco",
					"Purifica el aire y filtra toxinas al interior de la casa.",
					"../../assets/orquidea-blanca.jpg", 4, 3000, true );
			plantasRepositorio.save(orquidea_blanca);

			//Clientes
			Clientes clientes= new Clientes("Juan", "Rojas", "129010101", "310101010", Genero.MASCULINO, LocalDate.now().minusYears(29), "juan@gmail.com","1234");
			clientesRepositorio.save(clientes);
		};
	};

}
