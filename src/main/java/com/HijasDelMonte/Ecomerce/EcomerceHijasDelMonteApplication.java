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
			Productos parlorPalm = new Productos(
					"Parlor Palm", TipoProducto.INTERIOR,
					"Parlor Palm es ideal porque crece muy lentamente y prospera en espacios reducidos.",
					"../../assets/parlor.jpg", 12, 2000, true, Categorias.PLANTAS );
			productosRepositorio.save(parlorPalm);
			Productos pothos = new Productos(
					"Pothos", TipoProducto.EXTERIOR,
					"Los pothos llegan directos del bosque tropical a tu casa.",
					"../../assets/pothos.jpg", 20, 2000, true, Categorias.PLANTAS );
			productosRepositorio.save(pothos);
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
					6,3000,true,Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_colgante);

			Productos maceta_matera= new Productos("Matera Para Plantas",TipoProducto.MACETAS,
					"Matera para plantas pequeñas.","../../assets/matera.jpg",
					2,1400,true,Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_matera);

			Productos curso1= new Productos("Curso De Plantas Aromáticas",TipoProducto.PRESENCIAL,
					"Clases teoricas y practicas, Lunes de 18 a 20 hs.","../../assets/curso_plantas_aromaticas.jpg",
					10,7700,true,Categorias.TALLERES);
			productosRepositorio.save(curso1);

			Productos curso2= new Productos("Curso De Plantas Medicinales",TipoProducto.VIRTUAL,
					"Clases teoricas y practicas, Lunes,Martes,Viernes de 15 a 18 hs.",
					"../../assets/curso_plantas_medicinales.png",

					8,8000,true,Categorias.TALLERES);

			productosRepositorio.save(curso2);

			Productos curso3= new Productos("Curso De Árboles",TipoProducto.PRESENCIAL,
					"Clases practicas, Todos los dias de la semana de 12 a 20 hs.",
					"../../assets/Curso-de-plantas-e-árbores.png",
					4,12000,true,Categorias.TALLERES);
			productosRepositorio.save(curso3);

			Productos maceta_artesanal = new Productos("Maceta Artesanal", TipoProducto.MACETAS,
					"Maceta artesanal con material reciclado.",
					"../../assets/macetaArtesanal.jpg",
					5, 2000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_artesanal);

			Productos maceta_cabeza = new Productos("Maceta de cabeza", TipoProducto.MACETAS,
					"Maceta de cabeza para exterior.",
					"../../assets/macetaCabeza.jpg",
					11, 3000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_cabeza);

			Productos maceta_cabeza_resina = new Productos("Maceta con cabeza de niña", TipoProducto.MACETAS,
					"Maceta de resina con cabeza de niña.",
					"../../assets/macetaDeResinaConCabeza.jpg",
					8, 2000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_cabeza_resina);

			Productos maceta_mediana = new Productos("Maceta mediana para interior ", TipoProducto.MACETAS,
					"Maceta para interior.",
					"../../assets/materaMediana.jpg",
					5, 2000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_mediana);

			Productos anthurium_rosa = new Productos(
					"Anthurium Rosa", TipoProducto.INTERIOR,
					"Dado que procede de zonas selváticas, el anthurium demanda buena luminosidad. Se mantiene en interior interiores precisamente por su necesidad de una temperatura cálida y sin oscilaciones.",
					"../../assets/arthuriumRosa.jpg", 3, 5000, true, Categorias.PLANTAS );
			productosRepositorio.save(anthurium_rosa);

			Productos lirio_paz = new Productos(
					"Lirio de Paz", TipoProducto.INTERIOR,
					"Para interiores o lugares con sombra. Uno de los significados más especiales del lirio de paz es la curación y la creación del equilibrio psíquico adecuado para que la vida de quien la posee sea más pacífica y armoniosa.",
					"../../assets/lirioPaz.jpg", 4, 3000, true, Categorias.PLANTAS );
			productosRepositorio.save(lirio_paz);

			Productos buganvilla = new Productos(
					"Buganvilla", TipoProducto.EXTERIOR,
					"Es una planta trepadora rústica que no necesita de muchos cuidados, para que la buganvilla crezca sana, frondosa y con mucho color colócala en un sitio en el que reciba tanto sol como sea posible.  ",
					"../../assets/buganvilla.jpg", 15, 5000, true, Categorias.PLANTAS );
			productosRepositorio.save(buganvilla);

			Productos clivia = new Productos(
					"Clivia", TipoProducto.INTERIOR,
					"No es muy exigente en cuanto a la luz y puede crecer y mantenerse con vida en los rincones menos luminosos.El exceso de sol, sin embargo, puede dañar las hojas verdes, creándoles quemaduras y amarillamientos.",
					"../../assets/clivia.jpg", 9, 2000, true, Categorias.PLANTAS );
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
