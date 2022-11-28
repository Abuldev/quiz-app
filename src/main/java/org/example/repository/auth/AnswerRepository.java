package org.example.repository.auth;

import org.example.config.HibernateConfigurer;
import org.example.domain.auth.Answer;
import org.example.repository.Repository;
import org.example.repository.RepositoryCRUD;
import org.example.utils.Writer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

/**
 * @Author :  Asliddin Ziyodullaev
 * @Date :  10:09   22/07/22
 * @Project :  QuizAppTeam
 */
public class AnswerRepository implements Repository, RepositoryCRUD<Long, Answer> {

    private static SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();

    private AnswerRepository() {
    }

    private static AnswerRepository instance;

    public static AnswerRepository getInstance() {
        if (instance == null) {
            instance = new AnswerRepository();
        }
        return instance;
    }


    @Override
    public Optional<Boolean> save(Answer domain) {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            session.persist(domain);
            session.getTransaction().commit();
            session.close();
            return Optional.of(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> update(Answer domain) {
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
    public Optional<Answer> get(Long aLong) {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
//            Query<Answer> query = session.createQuery("from Answer where id = :ans_id", Answer.class);
//            query.setParameter("ans_id", aLong);
//            Answer singleResult = query.getSingleResult();
            Answer answer = session.get(Answer.class, aLong);
            session.getTransaction().commit();
            session.close();
            return Optional.of(answer);
        } catch (Exception e) {
            Writer.println(e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Answer>> getAll() {
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            List<Answer> from_answer = session.createQuery("from Answer", Answer.class).getResultList();
            session.getTransaction().commit();
            session.close();
            return Optional.of(from_answer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> delete(Long aLong) {
        try {
            Optional<Answer> answer = get(aLong);
            if (answer.isPresent()) {
                Answer answer1 = answer.get();
                answer1.setDeleted(true);
                Session session = sessionFactory.openSession();
                session.getTransaction().begin();
                session.merge(answer1);
//                session.createQuery("update Answer set deleted = '1' where id = :ans_id", Answer.class).setParameter("ans_id", aLong);
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
