package cz.addai.persistence.dao;

import cz.addai.persistence.domain.AnswerLog;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerLogDao extends AbstractJpaDao<Long, AnswerLog> implements IGenericDao<Long, AnswerLog> {
    public AnswerLogDao() {
        super.setClazz(AnswerLog.class);
    }
}
