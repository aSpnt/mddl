package ru.softmachine.odyssey.backend.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition;

import java.util.UUID;

public interface FieldTransitionRepository extends JpaRepository<FieldValueTransition, UUID>,
        JpaSpecificationExecutor<FieldValueTransition> {

}
