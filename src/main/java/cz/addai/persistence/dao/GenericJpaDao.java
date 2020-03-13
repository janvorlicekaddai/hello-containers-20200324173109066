package cz.addai.persistence.dao;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericJpaDao<K extends Serializable, T extends Serializable> extends AbstractJpaDao<K,T> implements IGenericDao<K,T> {
    //
}