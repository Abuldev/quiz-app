package org.example.repository;

import org.example.config.HibernateConfigurer;
import org.example.domain.Domain;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface RepositoryCRUD <
        ID extends Serializable,
        AUTH extends Domain>{

    SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
    Optional<Boolean> save(AUTH domain);
    Optional<Boolean> update(AUTH domain);
    Optional<AUTH> get(ID id);
    Optional<List<AUTH>> getAll();
    Optional<Boolean> delete(ID id);

}
