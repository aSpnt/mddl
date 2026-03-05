package com.aspnt.mddl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aspnt.mddl.converter.FieldValueTransitionConverter;
import com.aspnt.mddl.dto.FieldValueTransitionDto;
import com.aspnt.mddl.exception.EntityNotFoundException;
import com.aspnt.mddl.repository.FieldTransitionRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransitionService {

    private final FieldTransitionRepository fieldTransitionRepository;
    private final FieldValueTransitionConverter fieldValueTransitionConverter;

    @Transactional
    public FieldValueTransitionDto getFieldValueTransition(UUID fieldTransitionId) {
        return fieldValueTransitionConverter.convertToDto(
                fieldTransitionRepository.findById(fieldTransitionId)
                        .orElseThrow(() -> new EntityNotFoundException("Field Transition was not found",
                                fieldTransitionId.toString())));
    }
}
