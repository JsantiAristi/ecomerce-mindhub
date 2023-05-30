package com.HijasDelMonte.Ecomerce.Servicios;

import com.HijasDelMonte.Ecomerce.DTO.ClientesDTO;
import com.HijasDelMonte.Ecomerce.Models.Clientes;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientesServicios {
    Clientes findById(long id);
    Clientes findByEmail(String email);
    void saveCliente(Clientes clientes);
    ClientesDTO getClienteDTO(long id);
    ClientesDTO obtenerClienteAutenticadoDTO(Authentication authentication);
    Clientes obtenerClienteAutenticado(Authentication authentication);
    List<ClientesDTO> obtenerClientesDTO();

}
