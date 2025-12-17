package ru.softmachine.odyssey.backend.cms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.softmachine.odyssey.backend.cms.repository.FieldValueRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final FieldValueRepository fieldValueRepository;

    @Value("${app.page.default-tag-size}")
    private Integer defaultTagSize;

    @Transactional
    public List<String> searchTags(UUID fieldDefId, String search) {
        return fieldValueRepository.findTagsByField(fieldDefId, Optional.ofNullable(search).orElse(""), defaultTagSize);
    }
}
