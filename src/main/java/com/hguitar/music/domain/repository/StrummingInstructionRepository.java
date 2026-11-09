package com.hguitar.music.domain.repository;

import com.hguitar.music.domain.model.StrummingInstruction;

import java.util.List;
import java.util.UUID;

public interface StrummingInstructionRepository {
    List<StrummingInstruction> findByPatternId(UUID patternId);
    StrummingInstruction save(StrummingInstruction instruction);
}
