package com.hguitar.music.infrastructure.persistence.repository;

import com.hguitar.music.domain.model.StrummingInstruction;
import com.hguitar.music.domain.repository.StrummingInstructionRepository;
import com.hguitar.music.infrastructure.persistence.entity.StrummingInstructionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class StrummingInstructionRepositoryAdapter implements StrummingInstructionRepository {

    private final JpaStrummingInstructionRepository jpa;

    public StrummingInstructionRepositoryAdapter(JpaStrummingInstructionRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<StrummingInstruction> findByPatternId(UUID patternId) {
        return jpa.findByPatternIdOrderByStepNumberAsc(patternId).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public StrummingInstruction save(StrummingInstruction instruction) {
        StrummingInstructionEntity entity = jpa.findById(instruction.getId())
                .orElse(new StrummingInstructionEntity(
                        instruction.getId(), instruction.getPatternId(), instruction.getStepNumber(),
                        instruction.getTitleVi(), instruction.getTitleEn(),
                        instruction.getContentVi(), instruction.getContentEn(),
                        instruction.getTipVi(), instruction.getTipEn()));
        entity.setPatternId(instruction.getPatternId());
        entity.setStepNumber(instruction.getStepNumber());
        entity.setTitleVi(instruction.getTitleVi());
        entity.setTitleEn(instruction.getTitleEn());
        entity.setContentVi(instruction.getContentVi());
        entity.setContentEn(instruction.getContentEn());
        entity.setTipVi(instruction.getTipVi());
        entity.setTipEn(instruction.getTipEn());
        return toDomain(jpa.save(entity));
    }

    private StrummingInstruction toDomain(StrummingInstructionEntity e) {
        return new StrummingInstruction(
                e.getId(), e.getPatternId(), e.getStepNumber(),
                e.getTitleVi(), e.getTitleEn(),
                e.getContentVi(), e.getContentEn(),
                e.getTipVi(), e.getTipEn());
    }
}
