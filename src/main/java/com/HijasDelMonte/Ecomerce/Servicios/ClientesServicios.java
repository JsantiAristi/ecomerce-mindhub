package com.HijasDelMonte.Ecomerce.Servicios;

import com.HijasDelMonte.Ecomerce.DTO.ClientesDTO;
import com.HijasDelMonte.Ecomerce.DTO.PlantasDTO;
import com.HijasDelMonte.Ecomerce.Models.Clientes;

import java.util.List;

public interface ClientesServicios {
    Clientes findById(long id);

    void saveCliente(Clientes clientes);

    ClientesDTO getClienteDTO(long id);

    List<ClientesDTO> obtenerClientesDTO();

}
