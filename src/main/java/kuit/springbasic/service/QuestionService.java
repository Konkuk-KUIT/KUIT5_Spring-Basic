package kuit.springbasic.service;

import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> findAll() {
        return questionRepository.findAll().stream().toList();
    }

    public Question findById(int questionId) {
        return questionRepository.findByQuestionId(questionId);
    }

    public void save(Question question) {
        questionRepository.insert(question);
    }

    public void update(Question question) {
        questionRepository.update(question);
    }

    public Question findByQuestionId(int questionId) {
        return questionRepository.findByQuestionId(questionId);
    }

    public void updateCountOfAnswer(Question question) {
        question.increaseCountOfAnswer();
        questionRepository.update(question);
    }
}
