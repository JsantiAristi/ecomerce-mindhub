package com.HijasDelMonte.Ecomerce;

import com.HijasDelMonte.Ecomerce.Models.*;
import com.HijasDelMonte.Ecomerce.Repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;



import java.time.LocalDate;

@SpringBootApplication
public class EcomerceHijasDelMonteApplication {

	@Autowired
	private PasswordEncoder passwordEnconder;
	public static void main(String[] args) {
		SpringApplication.run(EcomerceHijasDelMonteApplication.class, args);
	}

	@Bean
	public CommandLineRunner IniciarDatos(ClientesRepositorio clientesRepositorio, ProductosRepositorio productosRepositorio, ProductosSeleccionadosRepositorio productosSeleccionadosRepositorio, OrdenRepositorio ordenRepositorio){
		return (args) -> {
			//Clientes
			Clientes clientes= new Clientes("Juan", "Rojas", "129010101", 310101010, Genero.MASCULINO, LocalDate.now().minusYears(29), "juan@gmail.com",passwordEnconder.encode("1234"), true);
			clientesRepositorio.save(clientes);
			Clientes cliente2= new Clientes("Melba", "Morel", "1000000", 323242010, Genero.FEMENINO, LocalDate.now().minusYears(20), "melba@mindhub.com",passwordEnconder.encode("12345"), true);
			clientesRepositorio.save(cliente2);

			//Productos PLantas
			Productos orquidea_rosa = new Productos(
					"Orquidea Mini Premium Salitre en Cultivo", TipoProducto.INTERIOR,
					"Purifica el aire y filtra toxinas al interior de la casa.",
					"../../assets/orquidea-rosa.jpg", 12, 2000, true, Categorias.PLANTAS );
			productosRepositorio.save(orquidea_rosa);
			Productos bromelia_blanca = new Productos(
					"Bromelia Premium en Cultivo", TipoProducto.EXTERIOR,
					"Se adapta perfectamente en espacios interiores y exteriores.",
					"../../assets/bromelia-blanca.jpg", 20, 1000, true, Categorias.PLANTAS );
			productosRepositorio.save(bromelia_blanca);
			Productos anturio_rojo = new Productos(
					"Anturio Premium en Cultivo", TipoProducto.EXTERIOR,
					"Requiere cuidados mínimos siendo una planta perfecta para las personas con poco tiempo disponible.",
					"../../assets/anturio-rojo.jpg", 0, 5000, true , Categorias.PLANTAS);
			productosRepositorio.save(anturio_rojo);
			Productos orquidea_blanca = new Productos(
					"Orquidea Supreme Kasy en Cultivo", TipoProducto.INTERIOR,
					"Purifica el aire y filtra toxinas al interior de la casa.",
					"../../assets/orquidea-blanca.jpg", 4, 3000, true, Categorias.PLANTAS );
			productosRepositorio.save(orquidea_blanca);
			Productos maceta_aluminio = new Productos("Maceta Aluminio", TipoProducto.MACETAS,
					"Maceta con plato y pie de metal.",
					"../../assets/maceta1.jpeg",
					5, 20000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_aluminio);
			Productos maceta_ceramica = new Productos("Maceta ceramica",
					TipoProducto.MACETAS, "Maceta amarilla de ceramica, pequeña",
					"../../assets/maceta2.jpeg",
					10, 5000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_ceramica);

			Productos maceta_colgante= new Productos("Colgador De Plantas",TipoProducto.MACETAS,
					"Soporte de macramé para plantas.","../../assets/accesorios1.jpg",
					6,7000,true,Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_colgante);

			Productos maceta_matera= new Productos("Matera Para Plantas",TipoProducto.MACETAS,
					"Matera para plantas pequeñas.","../../assets/matera.jpg",
					2,4400,true,Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_matera);

			Productos curso1= new Productos("Curso De Plantas Aromáticas",TipoProducto.PRESENCIAL,
					"Clases teoricas y practicas, Lunes de 18 a 20 hs.","../../assets/curso_plantas_aromaticas.jpg",
					10,7700,true,Categorias.CURSOS);
			productosRepositorio.save(curso1);

			Productos curso2= new Productos("Curso De Plantas Medicinales",TipoProducto.VIRTUAL,
					"Clases teoricas y practicas, Lunes,Martes,Viernes de 15 a 18 hs.",
					"../../assets/curso_plantas_medicinales.png",
					8,8000,true,Categorias.CURSOS);
			productosRepositorio.save(curso2);

			Productos curso3= new Productos("Curso De Árboles",TipoProducto.PRESENCIAL,
					"Clases practicas, Todos los dias de la semana de 12 a 20 hs.",
					"../../assets/Curso-de-plantas-e-árbores.png",
					4,12000,true,Categorias.CURSOS);
			productosRepositorio.save(curso3);
		};
	};

}
