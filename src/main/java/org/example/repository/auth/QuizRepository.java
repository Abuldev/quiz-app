package org.example.repository.auth;

import org.example.config.HibernateConfigurer;
import org.example.domain.auth.Question;
import org.example.domain.auth.Quiz;
import org.example.repository.Repository;
import org.example.repository.RepositoryCRUD;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class QuizRepository implements Repository, RepositoryCRUD<
        Long, Quiz> {

    private static QuizRepository instance;

    public static QuizRepository getInstance() {
        if (instance == null) {
            instance = new QuizRepository();
        }
        return instance;
    }
    private final SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
    @Override
    public Optional<Boolean> save(Quiz domain) {
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
    public Optional<Boolean> update(Quiz domain) {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            session.merge(domain);
            session.getTransaction().commit();
            session.close();
            return Optional.of(true);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Quiz> get(Long aLong) {
        try {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Quiz quiz = session.get(Quiz.class, aLong);
        session.getTransaction().commit();
        session.close();
        return Optional.of(quiz);
    } catch (Exception e) {
        e.printStackTrace();
    }
        return Optional.empty();
    }

    @Override
    public Optional<List<Quiz>> getAll() {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            List<Quiz> from_quiz_ = session.createQuery("from Quiz ", Quiz.class).getResultList();
            session.getTransaction().commit();
            session.close();
            return Optional.of(from_quiz_);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> delete(Long aLong) {
        try {
            Optional<Quiz> quiz = get(aLong);
            if (quiz.isPresent()) {

                Quiz quiz1 = quiz.get();
                quiz1.setDeleted(false);
                Session session = sessionFactory.openSession();
                session.getTransaction().begin();
                session.merge(quiz1);
                session.getTransaction().commit();
                session.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Quiz getLast(){
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Quiz singleResult = session.createQuery("select t from Quiz t order by t.id desc", Quiz.class).setMaxResults(1).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return singleResult;
    }
}
