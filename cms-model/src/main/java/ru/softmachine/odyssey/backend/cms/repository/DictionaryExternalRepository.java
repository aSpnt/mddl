package ru.softmachine.odyssey.backend.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal;

import java.util.UUID;

public interface DictionaryExternalRepository extends JpaRepository<DictionaryExternal, UUID>,
        JpaSpecificationExecutor<DictionaryExternal> {
}
