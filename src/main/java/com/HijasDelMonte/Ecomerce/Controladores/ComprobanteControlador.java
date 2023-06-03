package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.ComprobanteDTO;
import com.HijasDelMonte.Ecomerce.DTO.OrdenDTO;
import com.HijasDelMonte.Ecomerce.DTO.PagarConTarjetaDTO;
import com.HijasDelMonte.Ecomerce.Models.*;
import com.HijasDelMonte.Ecomerce.Servicios.ClientesServicios;
import com.HijasDelMonte.Ecomerce.Servicios.ComprobanteServicio;
import com.HijasDelMonte.Ecomerce.Servicios.Implementacion.EnviarCorreo;
import com.HijasDelMonte.Ecomerce.Servicios.ProductosServicios;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@CrossOrigin(origins = {"*"})
@RestController
public class ComprobanteControlador {
    @Autowired
    ClientesServicios clientesServicios;
    @Autowired
    ProductosServicios productosServicios;
    @Autowired
    ComprobanteServicio comprobanteServicio;
    @Autowired
    EnviarCorreo enviarCorreo;

    @GetMapping("/api/cliente/comprobante")
    public List<ComprobanteDTO> obtenerComprobanteDTO(Authentication authentication) {

        Clientes clientes = clientesServicios.obtenerClienteAutenticado(authentication);

        List<ComprobanteDTO> comprobantes = clientes.getComprobantes().stream().map(ComprobanteDTO::new).collect(toList());
        return comprobantes;
    }

    @Transactional
    @PostMapping("/api/cliente/comprobante")
    public ResponseEntity<Object> pagarConTarjeta(Authentication authentication, @RequestBody PagarConTarjetaDTO pagarConTarjetaDTO) {

        Clientes cliente = clientesServicios.obtenerClienteAutenticado(authentication);
        List<Orden> ordenes = cliente.getOrdenes().stream().filter(ordenPagada -> !ordenPagada.isComprado()).collect(toList());
        Orden orden = ordenes.get(0);

        try {
            URL url = new URL("https://mindhubbankcowboy.up.railway.app/api/clients/current/cards/postnet");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String solicitudPost = "{\"type\": \"" + pagarConTarjetaDTO.getType() + "\", " +
                    "\"color\": \"" + pagarConTarjetaDTO.getColor() + "\", " +
                    "\"number\": \"" + pagarConTarjetaDTO.getNumber() + "\", " +
                    "\"cvv\": \"" + pagarConTarjetaDTO.getCvv() + "\", "+
                    "\"email\": \"" + pagarConTarjetaDTO.getEmail() + "\", " +
                    "\"amount\": \"" + pagarConTarjetaDTO.getAmount() + "\"}";

            connection.getOutputStream().write(solicitudPost.getBytes());
            int codigoDeRespuesta = connection.getResponseCode();
            if (codigoDeRespuesta == HttpURLConnection.HTTP_CREATED) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                System.out.println("Respuesta del servidor: " + response);

                connection.getInputStream().close();
                connection.disconnect();
                orden.setComprado(true);

                for (ProductosSeleccionados productoSelect : orden.getProductosSeleccionadosSet() ){
                    productoSelect.getProductos().setStock( productoSelect.getProductos().getStock() - productoSelect.getCantidad() );
                    productosServicios.guardarPlanta( productoSelect.getProductos() );
                }

                Comprobante nuevoComprobante = new Comprobante( pagarConTarjetaDTO.getType(), pagarConTarjetaDTO.getColor(), LocalDateTime.now(), orden);
                cliente.añadirComprobante(nuevoComprobante);
                comprobanteServicio.guardarComprobante(nuevoComprobante);

                //        Trabajamos en el documento PDF
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Document document = new Document();
                PdfWriter.getInstance( document, outputStream);
                document.open();

                Image logo = Image.getInstance("https://res.cloudinary.com/dtis6pqyq/image/upload/v1685806609/LOGO_HIJAS_DEL_MONTE-01_bih7yr.png");
                logo.scaleToFit(100, 100);
                document.add(logo);

                Font Title = new Font(Font.FontFamily.TIMES_ROMAN, 30, Font.BOLD);
                Paragraph title = new Paragraph("HIJAS DEL MONTE", Title);
                title.setAlignment(Paragraph.ALIGN_CENTER);
                title.setSpacingAfter(20);
                title.setSpacingBefore(20);
                document.add(title);

                Font Title2 = new Font(Font.FontFamily.TIMES_ROMAN, 25, Font.BOLD);
                    Paragraph title2 = new Paragraph("Hola " + cliente.getNombre() +" "+ cliente.getApellido(), Title2);
                title2.setAlignment(Paragraph.ALIGN_CENTER);
                title2.setSpacingAfter(20);
                title2.setSpacingBefore(20);
                document.add(title2);

                Font SubTitle  = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
                Paragraph clientText = new Paragraph("Aqui tienes tu comprobante de pago de la orden N° "+orden.getId()+".", SubTitle);
                clientText.setAlignment(Paragraph.ALIGN_CENTER);
                clientText.setSpacingAfter(20);
                clientText.setSpacingBefore(20);
                document.add(clientText);

                Font ordenFont  = new Font(Font.FontFamily.TIMES_ROMAN, 14);
                Paragraph montoTotal = new Paragraph("Total Pagado: $" + NumberFormat.getNumberInstance(Locale.US).format(orden.getPrecioTotal()), ordenFont);
                montoTotal.setAlignment(Paragraph.ALIGN_LEFT);
                montoTotal.setSpacingAfter(5);
                montoTotal.setSpacingBefore(10);
                document.add(montoTotal);

                Paragraph unidadesTotales = new Paragraph("Total Unidades: " + orden.getUnidadesTotales(), ordenFont);
                unidadesTotales.setAlignment(Paragraph.ALIGN_LEFT);
                unidadesTotales.setSpacingAfter(10);
                unidadesTotales.setSpacingBefore(10);
                document.add(unidadesTotales);

                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);

                PdfPCell nombreCelda = new PdfPCell( new Paragraph("Nombre"));
                nombreCelda.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(nombreCelda);

                PdfPCell CantidadCelda = new PdfPCell( new Paragraph("Cantidad"));
                CantidadCelda.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(CantidadCelda);

                PdfPCell PrecioCelda = new PdfPCell( new Paragraph("Precio"));
                PrecioCelda.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(PrecioCelda);

                for ( ProductosSeleccionados productosSeleccionados : orden.getProductosSeleccionadosSet() ){

                    PdfPCell nombreCeldaInfo = new PdfPCell(new Paragraph(productosSeleccionados.getProductos().getNombre()));
                    nombreCeldaInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                    nombreCeldaInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    nombreCeldaInfo.setFixedHeight(50);
                    table.addCell(nombreCeldaInfo);

                    PdfPCell cantidadCeldaInfo = new PdfPCell(new Paragraph(NumberFormat.getNumberInstance(Locale.US).format(productosSeleccionados.getCantidad())));
                    cantidadCeldaInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cantidadCeldaInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cantidadCeldaInfo.setFixedHeight(50);
                    table.addCell(cantidadCeldaInfo);

                    PdfPCell precioCeldaInfo = new PdfPCell(new Paragraph( "$" + NumberFormat.getNumberInstance(Locale.US).format(productosSeleccionados.getPrecioTotal()) ));
                    precioCeldaInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                    precioCeldaInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    precioCeldaInfo.setFixedHeight(50);
                    table.addCell(precioCeldaInfo);
                }

                document.add(table);
                document.close();

                enviarCorreo.sendEmail( cliente.getEmail(), "Comprobante",
                        "Se adjunta el comprobante de la compra de la orden " + orden.getId() , outputStream.toByteArray() );

                return new ResponseEntity<>("Pago aceptado", HttpStatus.CREATED);
            } else {
                connection.getInputStream().close();
                connection.disconnect();

                return new ResponseEntity<>("Pago rechazado", HttpStatus.FORBIDDEN);
            }
        } catch (Exception err) {
            err.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al realizar el pago");
        }
    }

}
