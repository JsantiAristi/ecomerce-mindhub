package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.ClientesDTO;
import com.HijasDelMonte.Ecomerce.Models.Clientes;
import com.HijasDelMonte.Ecomerce.Models.Genero;
import com.HijasDelMonte.Ecomerce.Servicios.ClientesServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/api/clientes/actual/{id}")
    public ClientesDTO getClient (@PathVariable  long id) {
        //id=1;
        Clientes cliente = clientesServicios.findById(id);
        return new ClientesDTO(cliente);}

    @GetMapping("api/clientes")
    public List<ClientesDTO> obtenerClientes(){
        return clientesServicios.obtenerClientesDTO();}

    @PutMapping("/api/clientes/actual")
    public ResponseEntity<Object> editarInformacion(@RequestBody Clientes clientes){

        Clientes cliente = clientesServicios.findById(clientes.getId());

        if ( clientes.getNombre().isBlank() || !clientes.getNombre().matches("^[a-z A-Z]*$")){
            return new ResponseEntity<>("Tu nombre no puede estar vacio, tener números o caracteres especiales (la ñ cuenta como caracter especial).", HttpStatus.FORBIDDEN);
        } else if ( clientes.getApellido().isBlank() || !clientes.getApellido().matches("^[a-z A-Z]*$") ){
            return new ResponseEntity<>("Tu apellido no puede estar vacio, tener números o caracteres especiales (la ñ cuenta como caracter especial).", HttpStatus.FORBIDDEN);
        } else if( clientes.getCedulaCiudadania().length() < 5 || !clientes.getCedulaCiudadania().matches("^[0-9]+$") ){
            return new ResponseEntity<>("Tú identificación no puede ser menor a 5 digitos y no puede contener letras.", HttpStatus.FORBIDDEN);
        } else if( clientes.getTelefono() < 5 ) {
            return new ResponseEntity<>("Tú número de celular no puede ser menor a 5 digitos y no puede contener letras.", HttpStatus.FORBIDDEN);
        } else if ( !clientes.getGenero().equals(Genero.FEMENINO) && !clientes.getGenero().equals(Genero.MASCULINO) && !clientes.getGenero().equals(Genero.OTRO) ){
            return new ResponseEntity<>("Ingresa una opción valida", HttpStatus.FORBIDDEN);
        }

        if(cliente!=null) {
            if(!(cliente.getNombre().equalsIgnoreCase(clientes.getNombre()))
                    || !(cliente.getApellido().equalsIgnoreCase(clientes.getApellido()))
                    || !(cliente.getCedulaCiudadania().equalsIgnoreCase(clientes.getCedulaCiudadania()))
                    || cliente.getTelefono() != clientes.getTelefono()
                    || !(cliente.getGenero().equals(clientes.getGenero()))
                    || !(cliente.getFechaNacimiento().equals(clientes.getFechaNacimiento()))
            ){
                cliente.setNombre(clientes.getNombre());
                cliente.setApellido(clientes.getApellido());
                cliente.setCedulaCiudadania(clientes.getCedulaCiudadania());
                cliente.setTelefono(clientes.getTelefono());
                cliente.setGenero(clientes.getGenero());
                cliente.setFechaNacimiento(clientes.getFechaNacimiento());
                clientesServicios.saveCliente(cliente);
                return new ResponseEntity<>("Se han cambiado tus datos" , HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity
                        <>("No se ha modificado la informacion", HttpStatus.ACCEPTED);}
        } else {
            return new ResponseEntity<>("No se encontro el cliente" , HttpStatus.ACCEPTED);}
    }

    @PostMapping ("api/clientes")
    public ResponseEntity<Object> registro(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestBody String contraseña){
        if (nombre.isBlank() || apellido.isBlank() || email.isBlank() || contraseña.isBlank()){
            return new ResponseEntity<>("Campos vacios", HttpStatus.FORBIDDEN);
        }
        if (clientesServicios.findByEmail(email) != null){
            return new ResponseEntity<>("Ese email ya está en uso", HttpStatus.FORBIDDEN);
        }

        Clientes nuevoCliente = new Clientes(nombre, apellido, " ", 0, Genero.OTRO, LocalDate.now(), email, passwordEnconder.encode(contraseña), true);
        return new ResponseEntity<>("Bienvenido!", HttpStatus.CREATED);

    }

}
