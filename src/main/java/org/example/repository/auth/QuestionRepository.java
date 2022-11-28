package org.example.repository.auth;

import org.example.config.HibernateConfigurer;
import org.example.domain.auth.Answer;
import org.example.domain.auth.Question;
import org.example.repository.Repository;
import org.example.repository.RepositoryCRUD;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Shoniyazova Matlyuba
 * @project QuizAppTeam
 * @since 22/07/22  13:45 (Friday)
 */
public class QuestionRepository  implements Repository, RepositoryCRUD<Long, Question> {
    private QuestionRepository() {
    }

    private final SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();
    private static QuestionRepository instance;

    public static QuestionRepository getInstance() {
        if (instance == null) {
            instance = new QuestionRepository();
        }
        return instance;
    }


    @Override
    public Optional<Boolean> save(Question domain) {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            session.persist(domain);
            session.getTransaction().commit();
            session.close();
            return Optional.of(true);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public  Question  getLast(){
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Question singleResult = session.createQuery("select t from Question t order by t.id desc", Question.class).setMaxResults(1).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return singleResult;
    }
    @Override
    public Optional<Boolean> update(Question domain) {
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
    public Optional<Question> get(Long aLong) {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            Question question = session.get(Question.class, aLong);
            session.getTransaction().commit();
            session.close();
            return Optional.of(question);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Question>> getAll() {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            List<Question> from_question_ = session.createQuery("from Question ", Question.class).getResultList();
            session.getTransaction().commit();
            session.close();
            return Optional.of(from_question_);
        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> delete(Long aLong) {
        try {
            Optional<Question> question = get(aLong);
            if (question.isPresent()) {
                Question question1 = question.get();
                question1.setDeleted(true);
                Session session = sessionFactory.openSession();
                session.getTransaction().begin();
                session.merge(question1);
                session.getTransaction().commit();
                session.close();
                return Optional.of(true);
            }
        }catch ( Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
