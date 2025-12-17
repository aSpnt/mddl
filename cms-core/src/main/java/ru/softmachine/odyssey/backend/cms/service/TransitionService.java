package ru.softmachine.odyssey.backend.cms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.softmachine.odyssey.backend.cms.converter.FieldValueTransitionConverter;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueTransitionDto;
import ru.softmachine.odyssey.backend.cms.exception.EntityNotFoundException;
import ru.softmachine.odyssey.backend.cms.repository.FieldTransitionRepository;

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
