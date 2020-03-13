package cz.addai.persistence.dao;

import cz.addai.persistence.domain.Recipient;
import org.springframework.stereotype.Repository;

@Repository
public class RecipientDao extends AbstractJpaDao<Long, Recipient> implements IGenericDao<Long, Recipient> {

    public RecipientDao() {
        super.setClazz(Recipient.class);
    }
}
