package org.example.repository.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.config.HibernateConfigurer;
import org.example.domain.auth.Question;
import org.example.domain.auth.QuizQuestion;
import org.example.repository.Repository;
import org.example.repository.RepositoryCRUD;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuizQuestionRepository implements Repository, RepositoryCRUD<
        Long, QuizQuestion> {

    private SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
    private static QuizQuestionRepository instance;

    public static QuizQuestionRepository getInstance() {
        if (instance == null) {
            instance = new QuizQuestionRepository();
        }
        return instance;
    }
    @Override
    public Optional<Boolean> save(QuizQuestion domain) {
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


    public QuizQuestion getLast(){
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        QuizQuestion singleResult = session.createQuery("select t from QuizQuestion t order by t.id desc", QuizQuestion.class).setMaxResults(1).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return singleResult;
    }

    @Override
    public Optional<Boolean> update(QuizQuestion domain) {
        return Optional.empty();
    }

    @Override
    public Optional<QuizQuestion> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<List<QuizQuestion>> getAll() {
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> delete(Long aLong) {
        return Optional.empty();
    }
}
