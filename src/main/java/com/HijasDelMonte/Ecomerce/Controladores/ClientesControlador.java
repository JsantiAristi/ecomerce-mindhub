package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.ClientesDTO;
import com.HijasDelMonte.Ecomerce.Models.Clientes;
import com.HijasDelMonte.Ecomerce.Servicios.ClientesServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ClientesControlador {

    @Autowired
    private ClientesServicios clientesServicios;
    @Autowired
    private PasswordEncoder passwordEnconder;

    @GetMapping("/api/clientes/actual")
    public ClientesDTO getClient (Authentication authentication) {
        return clientesServicios.obtenerClienteAutenticadoDTO(authentication);}

    @GetMapping("/api/clientes")
    public List<ClientesDTO> obtenerClientes(){
        return clientesServicios.obtenerClientesDTO();}

    @PutMapping("/api/clientes/actual")
    public ResponseEntity<Object> editarInformacion(@RequestBody Clientes clientes){

        Clientes cliente = clientesServicios.findById(clientes.getId());

        if ( clientes.getNombre().isBlank() || !clientes.getNombre().matches("^[a-z A-Z]*$")){
            return new ResponseEntity<>("Tu nombre no puede estar vacio, tener números o caracteres especiales (la ñ cuenta como caracter especial).", HttpStatus.FORBIDDEN);
        } else if ( clientes.getApellido().isBlank() || !clientes.getApellido().matches("^[a-z A-Z]*$") ){
            return new ResponseEntity<>("Tu apellido no puede estar vacio, tener números o caracteres especiales (la ñ cuenta como caracter especial).", HttpStatus.FORBIDDEN);
        } else if( clientes.getDni().length() < 5 || !clientes.getDni().matches("^[0-9]+$") ){
            return new ResponseEntity<>("Tú dni no puede ser menor a 5 digitos y no puede contener letras.", HttpStatus.FORBIDDEN);
        }
        else if( clientes.getTelefono() < 5 ) {
            return new ResponseEntity<>("Tú número de celular no puede ser menor a 5 digitos y no puede contener letras.", HttpStatus.FORBIDDEN);
        }

        if(cliente!=null) {
            if(!(cliente.getNombre().equalsIgnoreCase(clientes.getNombre()))
                    || !(cliente.getApellido().equalsIgnoreCase(clientes.getApellido()))
                    || !(cliente.getDni().equalsIgnoreCase(clientes.getDni()))
                    || cliente.getTelefono() != clientes.getTelefono()
                    || !(cliente.getFechaNacimiento().equals(clientes.getFechaNacimiento()))
            ){
                cliente.setNombre(clientes.getNombre());
                cliente.setApellido(clientes.getApellido());
                cliente.setDni(clientes.getDni());
                cliente.setTelefono(clientes.getTelefono());
                cliente.setFechaNacimiento(clientes.getFechaNacimiento());
                clientesServicios.saveCliente(cliente);
                return new ResponseEntity<>("Se han cambiado tus datos" , HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity
                        <>("No se ha modificado la informacion", HttpStatus.ACCEPTED);}
        } else {
            return new ResponseEntity<>("No se encontro el cliente" , HttpStatus.ACCEPTED);}
    }

    @PostMapping ("/api/clientes")
    public ResponseEntity<Object> registro(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam String contraseña){
        if (nombre.isBlank() || apellido.isBlank()){
            return new ResponseEntity<>("Campos vacios", HttpStatus.FORBIDDEN);
        }
        if (clientesServicios.findByEmail(email) != null){
            return new ResponseEntity<>("Ese email ya está en uso", HttpStatus.FORBIDDEN);
        }
        if (!apellido.matches("^[a-z A-ZñÑ]*$")){
            return new ResponseEntity<>("Apellido inválido, solo se permiten letras.", HttpStatus.FORBIDDEN);
        }
        if (!nombre.matches("^[a-z A-ZñÑ]*$")){
            return new ResponseEntity<>("Nombre inválido, solo se permiten letras.", HttpStatus.FORBIDDEN);
        }
        if ( email.isBlank() || !email.contains("@") ) {
            return new ResponseEntity<>("Error, por favor ingrese una dirección de correo electrónico válida.", HttpStatus.FORBIDDEN);
        }
        if ( contraseña.isBlank()) {
            return new ResponseEntity<>("Contraseña Requerida", HttpStatus.FORBIDDEN);}

        Clientes nuevoCliente = new Clientes(nombre, apellido, " ", 0, LocalDate.now(), email, passwordEnconder.encode(contraseña), true);
        clientesServicios.saveCliente(nuevoCliente);
        return new ResponseEntity<>("Bienvenido!", HttpStatus.CREATED);

    }

    @PutMapping ("/api/clientes")
    public ResponseEntity<Object> eliminarCliente(
            @RequestParam String email){
        Clientes clienteEliminado = clientesServicios.findByEmail(email);

        if (clienteEliminado != null){
            clienteEliminado.setValido(false);
            clientesServicios.saveCliente(clienteEliminado);
            return new ResponseEntity<>("Cliente eliminado", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Cliente no encontrado", HttpStatus.ACCEPTED);
    }

}
