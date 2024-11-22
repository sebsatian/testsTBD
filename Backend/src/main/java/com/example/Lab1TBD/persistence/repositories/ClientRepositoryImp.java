package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class ClientRepositoryImp implements ClientRepository {

    @Autowired
    private Sql2o sql2o;

    // Encontrar cliente por ID
    @Override
    public ClientEntity findClientById(long id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM client WHERE client_id = :client_id")
                    .addParameter("client_id", id)
                    .executeAndFetchFirst(ClientEntity.class); // Obtener un solo cliente
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejo básico en caso de error
        }
    }

    // Encontrar cliente por nombre
    @Override
    public ClientEntity findClientByName(String name) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM client WHERE client_name = :client_name")
                    .addParameter("client_name", name)
                    .executeAndFetchFirst(ClientEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Encontrar cliente por email
    @Override
    public ClientEntity findClientByEmail(String email) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM client WHERE email = :email")
                    .addParameter("email", email)
                    .executeAndFetchFirst(ClientEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Guardar cliente
    @Override
    public void saveClient(ClientEntity client) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("INSERT INTO client (client_name, address, email, password, phone_number) " +
                            "VALUES (:client_name, :address, :email, :password, :phone_number)")
                    .addParameter("client_name", client.getClient_name())
                    .addParameter("address", client.getAddress())
                    .addParameter("email", client.getEmail())
                    .addParameter("password", client.getPassword())
                    .addParameter("phone_number", client.getPhone_number())
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Actualizar cliente por ID
    @Override
    public void updateClient(ClientEntity client) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("UPDATE client SET client_name = :client_name, address = :address, " +
                            "email = :email, password = :password, phone_number = :phone_number WHERE client_id = :client_id")
                    .addParameter("client_id", client.getClient_id())
                    .addParameter("client_name", client.getClient_name())
                    .addParameter("address", client.getAddress())
                    .addParameter("email", client.getEmail())
                    .addParameter("password", client.getPassword())
                    .addParameter("phone_number", client.getPhone_number())
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Borrar cliente por ID
    @Override
    public void deleteClientById(long id) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("DELETE FROM client WHERE client_id = :client_id")
                    .addParameter("client_id", id)
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
