package cz.addai.service;

import cz.addai.persistence.dao.ClientDao;
import cz.addai.persistence.domain.Client;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClientService {

    @Resource
    private ClientDao clientDao;

    public Client getClient(String clientId) {
        return clientDao.findOne(clientId);
    }
}
