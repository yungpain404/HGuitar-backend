package com.hguitar.assessment.application.service;

import com.hguitar.assessment.application.dto.request.SubmitAttemptRequest;
import com.hguitar.assessment.application.dto.response.*;
import com.hguitar.assessment.domain.model.*;
import com.hguitar.assessment.domain.repository.QuizAttemptRepository;
import com.hguitar.assessment.domain.repository.QuizRepository;
import com.hguitar.common.domain.Language;
import com.hguitar.common.exception.BusinessRuleException;
import com.hguitar.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizAttemptRepository attemptRepository;

    public QuizService(QuizRepository quizRepository,
                       QuizAttemptRepository attemptRepository) {
        this.quizRepository = quizRepository;
        this.attemptRepository = attemptRepository;
    }

    @Transactional(readOnly = true)
    public QuizResponse getQuizForLesson(UUID lessonId, String lang) {
        Language language = Language.fromCode(lang);
        Quiz quiz = quizRepository.findByLessonId(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("No quiz found for this lesson."));
        return toResponse(quiz, language);
    }

    @Transactional
    public AttemptResponse startAttempt(UUID quizId, UUID userId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found."));

        int existingCount = attemptRepository.countByQuizIdAndUserId(quizId, userId);

        QuizAttempt attempt = new QuizAttempt(
                UUID.randomUUID(), quizId, userId, existingCount + 1,
                Instant.now(), null, null, QuizAttempt.Status.IN_PROGRESS, new ArrayList<>());

        attempt = attemptRepository.save(attempt);
        return toAttemptResponse(attempt);
    }

    @Transactional
    public AttemptResponse submitAttempt(UUID attemptId, UUID userId, SubmitAttemptRequest request) {
        QuizAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("Attempt not found."));

        if (!attempt.getUserId().equals(userId)) {
            throw new BusinessRuleException("You do not own this attempt.");
        }

        if (attempt.getStatus() == QuizAttempt.Status.SUBMITTED) {
            throw new BusinessRuleException("This attempt has already been submitted.");
        }

        Quiz quiz = quizRepository.findById(attempt.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found."));

        // Map questions by id for fast lookup
        Map<UUID, Question> questionMap = quiz.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));

        List<AttemptAnswer> answers = new ArrayList<>();
        int correct = 0;

        for (SubmitAttemptRequest.AnswerEntry entry : request.answers()) {
            Question question = questionMap.get(entry.questionId());
            if (question == null) {
                throw new BusinessRuleException("Question " + entry.questionId() + " does not belong to this quiz.");
            }
            boolean isCorrect = question.isCorrectAnswer(entry.selectedAnswerId());
            if (isCorrect) correct++;
            answers.add(new AttemptAnswer(UUID.randomUUID(), attemptId,
                    entry.questionId(), entry.selectedAnswerId(), isCorrect));
        }

        int total = quiz.getQuestions().size();
        BigDecimal score = total > 0
                ? BigDecimal.valueOf(correct * 100.0 / total).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        attempt.submit(answers, score);
        attempt = attemptRepository.save(attempt);
        return toAttemptResponse(attempt);
    }

    @Transactional(readOnly = true)
    public AttemptResponse getAttempt(UUID attemptId, UUID userId) {
        QuizAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("Attempt not found."));

        if (!attempt.getUserId().equals(userId)) {
            throw new BusinessRuleException("You do not own this attempt.");
        }
        return toAttemptResponse(attempt);
    }

    // ── mappers ──────────────────────────────────────────────────────────────

    private QuizResponse toResponse(Quiz quiz, Language lang) {
        List<QuestionResponse> questions = quiz.getQuestions().stream()
                .map(q -> {
                    List<AnswerResponse> answers = q.getAnswers().stream()
                            .map(a -> new AnswerResponse(a.getId(),
                                    lang == Language.VI ? a.getTextVi() : a.getTextEn()))
                            .toList();
                    return new QuestionResponse(q.getId(), q.getSortOrder(),
                            lang == Language.VI ? q.getTextVi() : q.getTextEn(), answers);
                })
                .toList();
        String title = lang == Language.VI ? quiz.getTitleVi() : quiz.getTitleEn();
        return new QuizResponse(quiz.getId(), quiz.getLessonId(), title, questions);
    }

    private AttemptResponse toAttemptResponse(QuizAttempt a) {
        List<AttemptAnswerResponse> answers = a.getAnswers() == null ? List.of() :
                a.getAnswers().stream()
                        .map(aa -> new AttemptAnswerResponse(
                                aa.getQuestionId(), aa.getSelectedAnswerId(), aa.isCorrect()))
                        .toList();
        return new AttemptResponse(a.getId(), a.getQuizId(), a.getAttemptNumber(),
                a.getStatus(), a.getStartedAt(), a.getSubmittedAt(), a.getScore(), answers);
    }
}
