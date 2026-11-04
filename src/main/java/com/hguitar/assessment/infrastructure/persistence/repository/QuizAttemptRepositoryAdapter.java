package com.hguitar.assessment.infrastructure.persistence.repository;

import com.hguitar.assessment.domain.model.AttemptAnswer;
import com.hguitar.assessment.domain.model.QuizAttempt;
import com.hguitar.assessment.domain.repository.QuizAttemptRepository;
import com.hguitar.assessment.infrastructure.persistence.entity.QuizAttemptAnswerEntity;
import com.hguitar.assessment.infrastructure.persistence.entity.QuizAttemptEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class QuizAttemptRepositoryAdapter implements QuizAttemptRepository {

    private final JpaQuizAttemptRepository jpa;

    public QuizAttemptRepositoryAdapter(JpaQuizAttemptRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public QuizAttempt save(QuizAttempt attempt) {
        QuizAttemptEntity entity = jpa.findById(attempt.getId())
                .orElse(new QuizAttemptEntity(attempt.getId(), attempt.getQuizId(),
                        attempt.getUserId(), attempt.getAttemptNumber(),
                        attempt.getStartedAt(), attempt.getStatus()));

        entity.setStatus(attempt.getStatus());
        entity.setScore(attempt.getScore());
        entity.setSubmittedAt(attempt.getSubmittedAt());

        if (attempt.getAnswers() != null) {
            entity.getAnswers().clear();
            for (AttemptAnswer aa : attempt.getAnswers()) {
                entity.getAnswers().add(new QuizAttemptAnswerEntity(
                        aa.getId(), entity, aa.getQuestionId(),
                        aa.getSelectedAnswerId(), aa.isCorrect()));
            }
        }

        return toDomain(jpa.save(entity));
    }

    @Override
    public Optional<QuizAttempt> findById(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public int countByQuizIdAndUserId(UUID quizId, UUID userId) {
        return jpa.countByQuizIdAndUserId(quizId, userId);
    }

    private QuizAttempt toDomain(QuizAttemptEntity e) {
        List<AttemptAnswer> answers = e.getAnswers().stream()
                .map(a -> new AttemptAnswer(a.getId(), e.getId(), a.getQuestionId(),
                        a.getSelectedAnswerId(), a.isCorrect()))
                .toList();
        return new QuizAttempt(e.getId(), e.getQuizId(), e.getUserId(),
                e.getAttemptNumber(), e.getStartedAt(), e.getSubmittedAt(),
                e.getScore(), e.getStatus(), answers);
    }
}
