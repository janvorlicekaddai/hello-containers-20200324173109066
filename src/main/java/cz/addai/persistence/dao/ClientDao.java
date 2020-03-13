package cz.addai.persistence.dao;

import cz.addai.persistence.domain.Client;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao extends AbstractJpaDao<String, Client> implements IGenericDao<String, Client> {

    public ClientDao() {
        super.setClazz(Client.class);
    }
}
