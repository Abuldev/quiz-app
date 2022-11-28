package org.example.repository.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.config.HibernateConfigurer;
import org.example.domain.auth.Subject;
import org.example.repository.Repository;
import org.example.repository.RepositoryCRUD;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubjectRepository implements Repository, RepositoryCRUD<
        Long, Subject> {

    private SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();

    private static SubjectRepository instance;

    public static SubjectRepository getInstance() {
        if (instance == null) {
            instance = new SubjectRepository();
        }
        return instance;
    }

    @Override
    public Optional<Boolean> save(Subject domain) {
        try {
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
    public Optional<Boolean> update(Subject domain) {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            session.merge(domain);
            session.getTransaction().commit();
            session.close();
            return Optional.of(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Subject> get(Long aLong) {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            Subject subject = session.get(Subject.class, aLong);
            session.getTransaction().commit();
            session.close();
            return Optional.of(subject);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Subject>> getAll() {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            List<Subject> from_subject_ = session.createQuery("from Subject ", Subject.class).getResultList();
            session.getTransaction().commit();
            session.close();
            return Optional.of(from_subject_);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> delete(Long aLong) {
        try {
            Optional<Subject> subject = get(aLong);
            if (subject.isPresent()) {
                Subject subject1 = subject.get();
                subject1.setDeleted(true);
                Session session = sessionFactory.openSession();
                session.getTransaction().begin();
                session.merge(subject1);
                session.getTransaction().commit();
                session.close();
                return Optional.of(true);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
