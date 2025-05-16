package kuit.springbasic.db;

import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface QuestionRepository {
    void insert(Question question);

    Question findByQuestionId(int questionId);

    Collection<Question> findAll();

    void update(Question question);
}
