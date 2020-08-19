package com.example.ebanking.dao;


import com.example.ebanking.ValidationException;
import com.example.ebanking.model.ClientModel;
import com.example.ebanking.model.LoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ClientModel getByUsernameAndPassword(LoginModel loginModel) throws ValidationException {
        String sql = "" +
                "SELECT " +
                "clienti.id, " +
                "clienti.nume, " +
                "clienti.prenume, " +
                "clienti.userName, " +
                "clienti.parola, " +
                "roluri.rol " +
                "FROM clienti " +
                "JOIN roluri on roluri.id_rol = clienti.id_rol " +
                "WHERE userName = ? AND parola = ?";
        try {
            return jdbcTemplate.queryForObject(sql,new Object[]{loginModel.getUsername(),loginModel.getPassword()},((resultSet, i) -> {
                ClientModel clientModel = new ClientModel();
                clientModel.setId(resultSet.getInt("id"));
                clientModel.setLastName(resultSet.getString("nume"));
                clientModel.setFirstName(resultSet.getString("prenume"));
                clientModel.setRole(resultSet.getString("rol"));
                return clientModel;
            }));
        } catch (DataAccessException ex) {
            throw new ValidationException("Username sau parola incorecta!");
        }
    }

    public List<ClientModel> getAll() {
        String sql = "" +
                "SELECT " +
                "clienti.id, " +
                "clienti.nume, " +
                "clienti.prenume, " +
                "roluri.rol " +
                "FROM clienti " +
                "JOIN roluri on roluri.id_rol = clienti.id_rol ";
        return jdbcTemplate.query(sql,((resultSet, i) -> {
            ClientModel clientModel = new ClientModel();
            clientModel.setId(resultSet.getInt("id"));
            clientModel.setLastName(resultSet.getString("nume"));
            clientModel.setFirstName(resultSet.getString("prenume"));
            clientModel.setRole(resultSet.getString("rol"));
            return clientModel;
        }));
    }
}
