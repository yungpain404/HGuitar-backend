package com.hguitar.assessment.infrastructure.persistence.repository;

import com.hguitar.assessment.domain.model.Answer;
import com.hguitar.assessment.domain.model.Question;
import com.hguitar.assessment.domain.model.Quiz;
import com.hguitar.assessment.domain.repository.QuizRepository;
import com.hguitar.assessment.infrastructure.persistence.entity.AnswerEntity;
import com.hguitar.assessment.infrastructure.persistence.entity.QuestionEntity;
import com.hguitar.assessment.infrastructure.persistence.entity.QuizEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class QuizRepositoryAdapter implements QuizRepository {

    private final JpaQuizRepository jpa;

    public QuizRepositoryAdapter(JpaQuizRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<Quiz> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Quiz> findByLessonId(UUID lessonId) {
        return jpa.findByLessonId(lessonId).map(this::toDomain);
    }

    private Quiz toDomain(QuizEntity e) {
        List<Question> questions = e.getQuestions().stream()
                .map(this::toQuestion)
                .toList();
        return new Quiz(e.getId(), e.getLessonId(), e.getTitleEn(), e.getTitleVi(),
                questions, e.getCreatedAt());
    }

    private Question toQuestion(QuestionEntity q) {
        List<Answer> answers = q.getAnswers().stream()
                .map(a -> new Answer(a.getId(), q.getId(), a.getTextEn(), a.getTextVi(), a.isCorrect()))
                .toList();
        return new Question(q.getId(), q.getQuiz().getId(), q.getSortOrder(),
                q.getTextEn(), q.getTextVi(), answers);
    }
}
