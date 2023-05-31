package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.OrdenDTO;
import com.HijasDelMonte.Ecomerce.DTO.PagarConTarjetaDTO;
import com.HijasDelMonte.Ecomerce.Models.Clientes;
import com.HijasDelMonte.Ecomerce.Models.Orden;
import com.HijasDelMonte.Ecomerce.Servicios.ClientesServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static java.util.stream.Collectors.toList;

@CrossOrigin(origins = {"*"})
@RestController
public class ComprobanteControlador {
    @Autowired
    ClientesServicios clientesServicios;

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
