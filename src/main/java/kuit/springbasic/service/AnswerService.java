package kuit.springbasic.service;

import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.domain.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public List<Answer> findAllByQuestionId(int questionId) {
        return answerRepository.findAllByQuestionId(questionId).stream().toList();
    }

    public Answer save(Answer answer) {
        return answerRepository.insert(answer);
    }
}
