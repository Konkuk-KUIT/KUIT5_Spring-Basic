package kuit.springbasic.db;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kuit.springbasic.domain.Question;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryQuestionRepository implements QuestionRepository {
    private Map<String, Question> questions = new HashMap<>();
    private static int PK = 0;

    public MemoryQuestionRepository() {
        insert(new Question("조하상", "저는 회식이 너무 좋아요", "회식을 하면 저를 꼭 불러주세요", 1));
        insert(new Question("박지원", "집가고 싶어요", "잡이 보약이다", 0));
    }

    public static int getPK() {
        return ++PK;
    }

    public Question findByQuestionId(int id) {
        return questions.get(String.valueOf(id));
    }

    public void update(Question question) {
        Question repoQuestion = questions.get(Integer.toString(question.getQuestionId()));
        repoQuestion.update(question);
    }

    public void insert(Question question) {
        question.setQuestionId(getPK());
        questions.put(Integer.toString(question.getQuestionId()), question);
    }

    public void updateCountOfAnswer(Question question) {
        Question repoQuestion = questions.get(Integer.toString(question.getQuestionId()));
        repoQuestion.updateCountofAnswer(question);
    }

    public List<Question> findAll() {
        return questions.values().stream().toList();
    }
}
