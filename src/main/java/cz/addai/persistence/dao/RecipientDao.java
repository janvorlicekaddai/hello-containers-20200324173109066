package cz.addai.persistence.dao;

import cz.addai.persistence.domain.Recipient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RecipientDao extends AbstractJpaDao<Long, Recipient> implements IGenericDao<Long, Recipient> {

    public RecipientDao() {
        super.setClazz(Recipient.class);
    }

    public List<Recipient> findExactMatch(String nameAndSurname) {
        String fixedName = nameAndSurname.trim().toLowerCase();
        return super.findAll().stream()
                .filter(r->r.getName().toLowerCase().equals(fixedName))
                .collect(Collectors.toList());
    }

    public List<Recipient> findMatch(String word) {
        String fixedWord = word.trim().toLowerCase();
        return super.findAll().stream()
                .filter(r->r.getName().toLowerCase().contains(fixedWord))
                .collect(Collectors.toList());
    }
}
