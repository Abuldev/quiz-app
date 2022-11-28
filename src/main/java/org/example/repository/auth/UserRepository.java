package org.example.repository.auth;

import org.example.config.HibernateConfigurer;
import org.example.domain.auth.AuthUser;
import org.example.repository.Repository;
import org.example.repository.RepositoryCRUD;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository, RepositoryCRUD<
        Long, AuthUser> {

   private SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();

    private UserRepository() {
    }

    private static UserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }


    public Optional<AuthUser> login(String username, String password){
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            Query<AuthUser> query = session.createQuery("select t from AuthUser t where t.username = :_username and t.password = :_password", AuthUser.class);
            query.setParameter("_username", username);
            query.setParameter("_password", password);
            AuthUser singleResult = query.getSingleResult();
            session.getTransaction().commit();
            session.close();
            return Optional.of(singleResult);
        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> save(AuthUser domain) {
       try{
           Session session = sessionFactory.openSession();
           session.getTransaction().begin();
           session.persist(domain);
           session.getTransaction().commit();
           session.close();
           return Optional.of(true);
       } catch (Exception e){
         e.printStackTrace();
       }
       return Optional.empty();
    }

    @Override
    public Optional<Boolean> update(AuthUser domain) {
        try {

            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            session.merge(domain);
            session.getTransaction().commit();
            session.close();
            return Optional.of(true);
        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<AuthUser> get(Long aLong) {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            Query<AuthUser> query = session.createQuery("from AuthUser t where t.id = :user_id", AuthUser.class);
            query.setParameter("user_id",aLong);
            AuthUser authUser = query.getSingleResult();

//            AuthUser authUser = session.get(AuthUser.class, aLong);
            session.getTransaction().commit();
            session.close();
            return Optional.of(authUser);
        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<AuthUser>> getAll() {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            List<AuthUser> authUser = session.createQuery("from AuthUser ", AuthUser.class).getResultList();
            session.getTransaction().commit();
            session.close();
            return Optional.of(authUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> delete(Long aLong) {
        try {
            Optional<AuthUser> authUser = get(aLong);
            if (authUser.isPresent()) {
                AuthUser authUser1 = authUser.get();
                authUser1.setDeleted(true);
                Session session = sessionFactory.openSession();
                session.getTransaction().begin();
                session.merge(authUser1);
                session.getTransaction().commit();
                session.close();
                return Optional.of(true);
            }
        }  catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
