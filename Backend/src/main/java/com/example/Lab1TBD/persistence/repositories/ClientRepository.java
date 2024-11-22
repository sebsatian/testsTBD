package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.ClientEntity;

public interface ClientRepository {
    ClientEntity findClientById(long idCliente);
    ClientEntity findClientByName(String nombre);
    ClientEntity findClientByEmail(String email);

    void saveClient(ClientEntity cliente);
    void updateClient(ClientEntity cliente);
    void deleteClientById(long id);
}
