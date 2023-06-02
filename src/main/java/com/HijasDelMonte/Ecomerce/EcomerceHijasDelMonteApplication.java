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
			Clientes clientes= new Clientes("Juan", "Rojas", "129010101", 310101010, LocalDate.now().minusYears(29), "juan@gmail.com",passwordEnconder.encode("1234"), true);
			clientesRepositorio.save(clientes);
			Clientes cliente2= new Clientes("Melba", "Morel", "1000000", 323242010, LocalDate.now().minusYears(20), "melba@mindhub.com",passwordEnconder.encode("12345"), true);
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

			Productos maceta_artesanal = new Productos("Maceta Artesanal", TipoProducto.MACETAS,
					"Maceta artesanal con material reciclado.",
					"../../assets/macetaArtesanal.jpg",
					5, 20000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_artesanal);

			Productos maceta_cabeza = new Productos("Maceta de cabeza", TipoProducto.MACETAS,
					"Maceta de cabeza para exterior.",
					"../../assets/macetaCabeza.jpg",
					11, 30000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_cabeza);

			Productos maceta_cabeza_resina = new Productos("Maceta con cabeza de niña", TipoProducto.MACETAS,
					"Maceta de resina con cabeza de niña.",
					"../../assets/macetaDeResinaConCabeza.jpg",
					8, 25000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_cabeza_resina);

			Productos maceta_mediana = new Productos("Maceta mediana para interior ", TipoProducto.MACETAS,
					"Maceta para interior.",
					"../../assets/materaMediana.jpg",
					5, 20000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_mediana);

			Productos anthurium_rosa = new Productos(
					"Anthurium Rosa", TipoProducto.INTERIOR,
					".",
					"../../assets/arthuriumRosa.jpg", 3, 50000, true, Categorias.PLANTAS );
			productosRepositorio.save(anthurium_rosa);

			Productos lirio_paz = new Productos(
					"Lirio de Paz", TipoProducto.INTERIOR,
					".",
					"../../assets/lirioPaz.jpg", 4, 35000, true, Categorias.PLANTAS );
			productosRepositorio.save(lirio_paz);

			Productos buganvilla = new Productos(
					"Buganvilla", TipoProducto.EXTERIOR,
					".",
					"../../assets/buganvilla.jpg", 15, 50000, true, Categorias.PLANTAS );
			productosRepositorio.save(buganvilla);

			Productos clivia = new Productos(
					"Clivia", TipoProducto.INTERIOR,
					".",
					"../../assets/clivia.jpg", 9, 27000, true, Categorias.PLANTAS );
			productosRepositorio.save(clivia);

			Productos planta_monstera = new Productos("Monstera mediana",
					TipoProducto.INTERIOR,
					"Planta trepadora, excelente para interiores muy iluminados. Necesita de mucha humedad ambiental y poco riego. No incluye maceta, la imagen es ilustrativa.",
					"../../assets/monstera.jpeg" ,
					10, 5000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_monstera);

			Productos planta_paloDeAgua = new Productos("Palo de Agua Grande",
					TipoProducto.INTERIOR,
					"Excelente para interiores muy iluminados, pero de forma indirecta. Necesita muy poco riego, humedad eventual en sus hojas. No incluye maceta, la imagen es ilustrativa.",
					"../../assets/palo-de-agua.jpeg" ,
					5, 15000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_paloDeAgua);

			Productos planta_helecho = new Productos("Helecho Pequeño",
					TipoProducto.INTERIOR,
					"Excelente interiores, terrazas o balcones muy humedos. Necesita mucho riego, principalmente en epocas de calor, humedad eventual en sus hojas. No incluye maceta, la imagen es ilustrativa.",
					"../../assets/helecho.jpeg" ,
					5, 3000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_helecho);

			Productos planta_ficus = new Productos("Ficus Pandurata Grande",
					TipoProducto.INTERIOR,
					"Excelente interiores y lugares muy humedos. Necesita bastante riego, principalmente en epocas de calor, humedad eventual en sus hojas y mucha iluminacion indirecta. No incluye maceta, la imagen es ilustrativa.",
					"../../assets/ficus-pandurata.jpeg" ,
					3, 20000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_ficus);

			Productos planta_gomero = new Productos("Ficus Elástica Grande",
					TipoProducto.INTERIOR,
					"Excelente interiores. Necesita bastante riego, principalmente en epocas de calor, humedad en sus hojas y mucha iluminacion. No incluye maceta, la imagen es ilustrativa.",
					"../../assets/gomero.jpeg" ,
					1, 20000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_gomero);

			Productos planta_sanseviera = new Productos("Sansevieria Pequeña",
					TipoProducto.EXTERIOR,
					"Esta planta necesita cuidados mínimos para vivir. Se adapta tanto a interiores como a exteriores, puede vivir tanto con abundante luz como en ambientes mas oscuros. Riego muy moderado. No incluye maceta, la imagen es ilustrativa.",
					"../../assets/sanseviera.jpg" ,
					15, 2000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_sanseviera);

			Productos planta_philodendron = new Productos("Philodendron Pequeño",
					TipoProducto.INTERIOR,
					"Planta ideal para espacios con iluminacion moderada. Es una planta trepadora, es decir que conviene colocarle un tutor para que crezca hacia arriba. Riego moderado. No incluye maceta, la imagen es ilustrativa.",
					"../../assets/philodendron.jpeg" ,
					8, 4500, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_philodendron);

			Productos planta_photus = new Productos("Photus Mediano",
					TipoProducto.INTERIOR,
					"Planta de cuidados sencillos, necesita un riego moderado y se adapta a lugares con poca luz. No incluye maceta, la imagen es ilustrativa.",
					"../../assets/pothus.png" ,
					11, 2500, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_photus);

			Productos maceta_plastica_rosa = new Productos("Maceta Plástica Rosa",
					TipoProducto.MACETAS,
					"Maceta plástica mediana rosa con patas de madera. No incluye planta, la imagen es ilustrativa.",
					"../../assets/maceta-plastica-rosa.jpeg" ,
					9, 3500, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_plastica_rosa);

			Productos maceta_ceramica_rosa = new Productos("Maceta Cerámica Rosa",
					TipoProducto.MACETAS,
					"Maceta ceramica pequeña rosa. No incluye planta, la imagen es ilustrativa.",
					"../../assets/maceta-ceramica-rosa.jpeg" ,
					15, 3000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_ceramica_rosa);

			Productos planta_difenbaquia_gigante = new Productos("Difenbaquia Gigante",
					TipoProducto.INTERIOR,
					"Planta que tolera muy bien la falta de luz. Es muy buena para principiantes. Riego moderado y mucha humedad. No incluye maceta, la imagen es ilustrativa.",
					"../../assets/difenbaquia-gigante.jpeg" ,
					2, 30000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_difenbaquia_gigante);

			Productos planta_difenbaquia_mini = new Productos("Difenbaquia Pequeña",
					TipoProducto.INTERIOR,
					"Planta que tolera muy bien la falta de luz. Es muy buena para principiantes. Riego moderado y mucha humedad. No incluye maceta, la imagen es ilustrativa.",
					"../../assets/difenbaquia.jpeg" ,
					2, 3000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_difenbaquia_mini);
		};
	}

}
