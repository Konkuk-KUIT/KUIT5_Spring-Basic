package kuit.springbasic.db;

import kuit.springbasic.domain.Answer;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AnswerRepository {
    Answer insert(Answer answer);

    Collection<Answer> findAllByQuestionId(int questionId);

}
