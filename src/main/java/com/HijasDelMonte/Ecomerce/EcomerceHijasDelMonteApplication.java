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
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104748/xjo2mxmzqerfpr0xe6cn.jpg", 12, 2000, true, Categorias.PLANTAS );
			productosRepositorio.save(parlorPalm);
			Productos pothos = new Productos(
					"Pothos", TipoProducto.EXTERIOR,
					"Los pothos llegan directos del bosque tropical a tu casa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104816/beupuqqvrsqgpb38trzf.jpg", 20, 2000, true, Categorias.PLANTAS );
			productosRepositorio.save(pothos);
			Productos maceta_aluminio = new Productos("Maceta Aluminio", TipoProducto.MACETAS,
					"Maceta con plato y pie de metal.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104101/maksfp9zfqtpxf0t5zcl.jpg",
					5, 20000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_aluminio);
			Productos maceta_ceramica = new Productos("Maceta Cerámica",
					TipoProducto.MACETAS, "Maceta amarilla de cerámica, pequeña",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104148/pmkmxz0ejnh7p8qd3zdj.jpg",
					10, 5000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_ceramica);

			Productos maceta_colgante= new Productos("Colgador De Plantas",TipoProducto.MACETAS,
					"Soporte de macramé para plantas.","https://res.cloudinary.com/dtis6pqyq/image/upload/v1686103503/cnulyqyqalolv5ziw14r.jpg",
					6,3000,true,Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_colgante);

			Productos maceta_matera= new Productos("Matera Para Plantas",TipoProducto.MACETAS,
					"Matera para plantas pequeñas.","https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104560/guleprrd3vtcdylqw536.jpg",
					2,1400,true,Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_matera);

			Productos curso1= new Productos("Taller Kokedamas",TipoProducto.PRESENCIAL,
					"Clase teórica de como hacer una Kokedama, 15 horas","https://res.cloudinary.com/dtis6pqyq/image/upload/v1686103672/ycermthrtckkcvsdgyoy.jpg",
					10,2000,true,Categorias.TALLERES);
			productosRepositorio.save(curso1);

			Productos curso2= new Productos("Cuadros Vivos",TipoProducto.PRESENCIAL,
					"Taller de jardines verticales artesanales, 3 horas.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686103753/v77uicy0ft0bwwa8ldqq.jpg",
					8,2000,true,Categorias.TALLERES);
			productosRepositorio.save(curso2);

			Productos curso3= new Productos("Cultivo de Plantas",TipoProducto.VIRTUAL,
					"Clases teóricas sobre el cultivo, 2 encuentros, de 15 a 17 hs (Google Meet)",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686103784/u8tm4wmpanxc3c4abcjq.jpg",
					11,8000,true,Categorias.TALLERES);
			productosRepositorio.save(curso3);

			Productos curso4= new Productos("Cultivo de Plantas Ornamentales",TipoProducto.VIRTUAL,
					"Sábado 17 Julio, 3 horas (Google Meet)",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686103812/f4mgk1ftwhjacvmdi90e.jpg",
					15,2000,true,Categorias.TALLERES);
			productosRepositorio.save(curso4);

			Productos maceta_artesanal = new Productos("Maceta Artesanal", TipoProducto.MACETAS,
					"Maceta artesanal con material reciclado.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104168/pcmtjpn0jw6ls8nbaryw.jpg",
					5, 2000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_artesanal);

			Productos maceta_cabeza = new Productos("Maceta de Cabeza", TipoProducto.MACETAS,
					"Maceta de cabeza para exterior.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104287/ptzubbwrln0suzdpv5hy.jpg",
					11, 3000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_cabeza);

			Productos maceta_cabeza_resina = new Productos("Maceta con Cabeza de Niña", TipoProducto.MACETAS,
					"Maceta de resina con cabeza de niña.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104460/qxpdw3mkvpxecwiy4kcp.jpg",
					8, 2000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_cabeza_resina);

			Productos maceta_mediana = new Productos("Maceta Mediana para Interior ", TipoProducto.MACETAS,
					"Maceta para interior.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104600/qrezj6gpai0btrbqvmcj.jpg",
					5, 2000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_mediana);

			Productos anthurium_rosa = new Productos(
					"Anthurium Rosa", TipoProducto.INTERIOR,
					"Dado que procede de zonas selváticas, el anthurium demanda buena luminosidad. Se mantiene en interiores precisamente por su necesidad de una temperatura cálida y sin oscilaciones.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686103300/qspfjnr9qf8itncieluu.jpg", 3, 5000, true, Categorias.PLANTAS );
			productosRepositorio.save(anthurium_rosa);

			Productos lirio_paz = new Productos(
					"Lirio de Paz", TipoProducto.INTERIOR,
					"Para interiores o lugares con sombra. Uno de los significados más especiales del lirio de paz es la curación y la creación del equilibrio psíquico adecuado para que la vida de quien la posee sea más pacífica y armoniosa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104061/j1hc8kpznw8fpwmwmpz0.jpg", 4, 3000, true, Categorias.PLANTAS );
			productosRepositorio.save(lirio_paz);

			Productos buganvilla = new Productos(
					"Buganvilla", TipoProducto.EXTERIOR,
					"Es una planta trepadora rústica que no necesita de muchos cuidados, para que la buganvilla crezca sana, frondosa y con mucho color colócala en un sitio en el que reciba tanto sol como sea posible.  ",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686103574/a1oa8uedsfkgmk9n5fow.jpg", 15, 5000, true, Categorias.PLANTAS );
			productosRepositorio.save(buganvilla);

			Productos clivia = new Productos(
					"Clivia", TipoProducto.INTERIOR,
					"No es muy exigente en cuanto a la luz y puede crecer y mantenerse con vida en los rincones menos luminosos.El exceso de sol, sin embargo, puede dañar las hojas verdes, creándoles quemaduras y amarillamientos.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686103631/ciat1twmwnir5yihkh6p.jpg", 9, 2000, true, Categorias.PLANTAS );
			productosRepositorio.save(clivia);

			Productos planta_monstera = new Productos("Monstera Mediana",
					TipoProducto.INTERIOR,
					"Planta trepadora, excelente para interiores muy iluminados. Necesita de mucha humedad ambiental y poco riego. No incluye maceta, la imagen es ilustrativa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104649/fsfo7rl6n4wgvbd6tigi.jpg" ,
					10, 5000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_monstera);

			Productos planta_paloDeAgua = new Productos("Palo de Agua Grande",
					TipoProducto.INTERIOR,
					"Excelente para interiores muy iluminados, pero de forma indirecta. Necesita muy poco riego, humedad eventual en sus hojas. No incluye maceta, la imagen es ilustrativa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104717/qynmw3vb4ewj5krmlbqd.jpg" ,
					5, 15000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_paloDeAgua);

			Productos planta_helecho = new Productos("Helecho Pequeño",
					TipoProducto.INTERIOR,
					"Excelente interiores, terrazas o balcones muy humedos. Necesita mucho riego, principalmente en epocas de calor, humedad eventual en sus hojas. No incluye maceta, la imagen es ilustrativa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104014/hmhwy5yj1bqh58ahahnd.jpg" ,
					5, 3000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_helecho);

			Productos planta_ficus = new Productos("Ficus Pandurata Grande",
					TipoProducto.INTERIOR,
					"Excelente interiores y lugares muy humedos. Necesita bastante riego, principalmente en epocas de calor, humedad eventual en sus hojas y mucha iluminacion indirecta. No incluye maceta, la imagen es ilustrativa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686103923/b7xecshifwabzkig8l3r.jpg" ,
					3, 20000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_ficus);

			Productos planta_gomero = new Productos("Ficus Elástica Grande",
					TipoProducto.INTERIOR,
					"Excelente interiores. Necesita bastante riego, principalmente en epocas de calor, humedad en sus hojas y mucha iluminacion. No incluye maceta, la imagen es ilustrativa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686103967/prlx9i0jtoanbsxmgvnu.jpg" ,
					1, 20000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_gomero);

			Productos planta_sanseviera = new Productos("Sansevieria Pequeña",
					TipoProducto.EXTERIOR,
					"Esta planta necesita cuidados mínimos para vivir. Se adapta tanto a interiores como a exteriores, puede vivir tanto con abundante luz como en ambientes mas oscuros. Riego muy moderado. No incluye maceta, la imagen es ilustrativa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104902/d6qbycb22vpbbjuxpowc.jpg" ,
					15, 2000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_sanseviera);

			Productos planta_philodendron = new Productos("Philodendron Pequeño",
					TipoProducto.INTERIOR,
					"Planta ideal para espacios con iluminacion moderada. Es una planta trepadora, es decir que conviene colocarle un tutor para que crezca hacia arriba. Riego moderado. No incluye maceta, la imagen es ilustrativa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104778/qzcap5bz9fpvruineyhs.jpg" ,
					8, 4500, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_philodendron);

			Productos planta_photus = new Productos("Photus Mediano",
					TipoProducto.INTERIOR,
					"Planta de cuidados sencillos, necesita un riego moderado y se adapta a lugares con poca luz. No incluye maceta, la imagen es ilustrativa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104874/bwm8lwcz7icpibmwzafk.png" ,
					11, 2500, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_photus);

			Productos maceta_plastica_rosa = new Productos("Maceta Plástica Rosa",
					TipoProducto.MACETAS,
					"Maceta plástica mediana rosa con patas de madera. No incluye planta, la imagen es ilustrativa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104500/bqisxi7rxmiakvxsftgt.jpg" ,
					9, 3500, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_plastica_rosa);

			Productos maceta_ceramica_rosa = new Productos("Maceta Cerámica Rosa",
					TipoProducto.MACETAS,
					"Maceta ceramica pequeña rosa. No incluye planta, la imagen es ilustrativa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686104319/cn6soxxs1p5vus6laua7.jpg" ,
					15, 3000, true, Categorias.ACCESORIOS);
			productosRepositorio.save(maceta_ceramica_rosa);

			Productos planta_difenbaquia_gigante = new Productos("Difenbaquia Gigante",
					TipoProducto.INTERIOR,
					"Planta que tolera muy bien la falta de luz. Es muy buena para principiantes. Riego moderado y mucha humedad. No incluye maceta, la imagen es ilustrativa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686103893/l99xt79vtjzszxjrzolc.jpg" ,
					2, 30000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_difenbaquia_gigante);

			Productos planta_difenbaquia_mini = new Productos("Difenbaquia Pequeña",
					TipoProducto.INTERIOR,
					"Planta que tolera muy bien la falta de luz. Es muy buena para principiantes. Riego moderado y mucha humedad. No incluye maceta, la imagen es ilustrativa.",
					"https://res.cloudinary.com/dtis6pqyq/image/upload/v1686103840/w9pgupxpoe9stn2kvpah.jpg" ,
					2, 3000, true, Categorias.PLANTAS);
			productosRepositorio.save(planta_difenbaquia_mini);
		};
	}

}
