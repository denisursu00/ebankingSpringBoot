package com.example.ebanking.service;

import com.example.ebanking.ValidationException;
import com.example.ebanking.dao.ClientDao;
import com.example.ebanking.model.ClientModel;
import com.example.ebanking.model.LoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Service
public class ClientService {
    private ClientDao clientDao;

    @Autowired
    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @ModelAttribute("client")
    public ClientModel getByUsernameAndPassword(LoginModel loginModel) throws ValidationException {
        ClientModel clientModel = clientDao.getByUsernameAndPassword(loginModel);
        if (clientModel == null) {
            throw new ValidationException("Username sau parola incorecta!");
        }
        return clientModel;
    }

    public List<ClientModel> getAll() {
        return clientDao.getAll();
    }
}
