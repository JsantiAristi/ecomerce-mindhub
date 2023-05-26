package com.HijasDelMonte.Ecomerce.Servicios.Implementacion;

import com.HijasDelMonte.Ecomerce.DTO.ClientesDTO;
import com.HijasDelMonte.Ecomerce.Models.Clientes;
import com.HijasDelMonte.Ecomerce.Repositorios.ClientesRepositorio;
import com.HijasDelMonte.Ecomerce.Servicios.ClientesServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ClientesServicioImplementación implements ClientesServicios {

    @Autowired
    private ClientesRepositorio clientesRepositorio;

    @Override
    public Clientes findById(long id) {
        return clientesRepositorio.findById(id).orElse(null);
    }

    @Override
    public void saveCliente(Clientes clientes) {
        clientesRepositorio.save(clientes);
    }

    @Override
    public ClientesDTO getClienteDTO(long id) {
        return new ClientesDTO(findById(id));
    }


}
