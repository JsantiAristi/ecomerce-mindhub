package com.HijasDelMonte.Ecomerce.Servicios.Implementacion;

import com.HijasDelMonte.Ecomerce.DTO.ClientesDTO;
import com.HijasDelMonte.Ecomerce.Models.Clientes;
import com.HijasDelMonte.Ecomerce.Repositorios.ClientesRepositorio;
import com.HijasDelMonte.Ecomerce.Servicios.ClientesServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClientesServicioImplementación implements ClientesServicios {

    @Autowired
    private ClientesRepositorio clientesRepositorio;

    @Override
    public Clientes findById(long id) {
        return clientesRepositorio.findById(id).orElse(null);
    }

    @Override
    public Clientes findByEmail(String email) {return clientesRepositorio.findByEmail(email);}

    @Override
    public void saveCliente(Clientes clientes) {
        clientesRepositorio.save(clientes);
    }

    @Override
    public ClientesDTO getClienteDTO(long id) {
        return new ClientesDTO(findById(id));
    }

    @Override
    public ClientesDTO obtenerClienteAutenticadoDTO(Authentication authentication) {
        return new ClientesDTO(clientesRepositorio.findByEmail(authentication.getName()));
    }

    @Override
    public Clientes obtenerClienteAutenticado(Authentication authentication) {
        return clientesRepositorio.findByEmail(authentication.getName());
    }

    @Override
    public List<ClientesDTO> obtenerClientesDTO() {
        return clientesRepositorio.findAll().stream().map(clientes -> new ClientesDTO(clientes)).collect(Collectors.toList());
    }


}
