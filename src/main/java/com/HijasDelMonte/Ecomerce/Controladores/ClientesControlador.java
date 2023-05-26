package com.HijasDelMonte.Ecomerce.Controladores;

import com.HijasDelMonte.Ecomerce.DTO.ClientesDTO;
import com.HijasDelMonte.Ecomerce.Models.Clientes;
import com.HijasDelMonte.Ecomerce.Servicios.ClientesServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;

@RestController
public class ClientesControlador {

    @Autowired
    private ClientesServicios clientesServicios;


    @GetMapping("/api/clientes/actual/{id}")
    public ClientesDTO getClient (@PathVariable  long id) {
        //id=1;
        Clientes cliente= clientesServicios.findById(id);
        return new ClientesDTO(cliente);
    }
    @PutMapping("/api/clientes/actual")
    public ResponseEntity<Object> editarInformacion(@RequestBody Clientes clientes){

        Clientes cliente= clientesServicios.findById(clientes.getId());
        if(cliente!=null) {
            if(cliente.getNombre().equals(clientes.getNombre())){
                cliente.setNombre(cliente.getNombre());
                clientesServicios.saveCliente(cliente);
                return new ResponseEntity<>("Se ha cambiado nombre" , HttpStatus.ACCEPTED);
            }
            if(cliente.getApellido().equals(clientes.getApellido())){
                cliente.setApellido(cliente.getApellido());
                clientesServicios.saveCliente(cliente);
                return new ResponseEntity<>("Se ha cambiado apellido" , HttpStatus.ACCEPTED);
            }
            if(cliente.getCedulaCiudadania().equals(clientes.getCedulaCiudadania())){
                cliente.setCedulaCiudadania(cliente.getCedulaCiudadania());
                clientesServicios.saveCliente(cliente);
                return new ResponseEntity<>("Se ha cambiado el numero de cedula" , HttpStatus.ACCEPTED);
            }
            if(cliente.getTelefono().equals(clientes.getTelefono())){
                cliente.setCedulaCiudadania(cliente.getTelefono());
                clientesServicios.saveCliente(cliente);
                return new ResponseEntity<>("Se ha cambiado el numero de telefono" , HttpStatus.ACCEPTED);
            }
            if(cliente.getGenero().equals(clientes.getGenero())){
                cliente.setGenero(cliente.getGenero());
                clientesServicios.saveCliente(cliente);
                return new ResponseEntity<>("Se ha cambiado el genero" , HttpStatus.ACCEPTED);
            }
            if(cliente.getFechaNacimiento().equals(clientes.getFechaNacimiento())){
                cliente.setFechaNacimiento(cliente.getFechaNacimiento());
                clientesServicios.saveCliente(cliente);
                return new ResponseEntity<>("Se ha cambiado su fecha de nacimiento" , HttpStatus.ACCEPTED);
            }
            else {
                return new ResponseEntity<>("No se ha modificado la informacion", HttpStatus.ACCEPTED);}
        }else{
            return new ResponseEntity<>("No se encontro el cliente" , HttpStatus.ACCEPTED);
        }

    }
}
