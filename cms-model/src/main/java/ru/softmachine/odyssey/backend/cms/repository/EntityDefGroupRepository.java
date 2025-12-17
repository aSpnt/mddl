package ru.softmachine.odyssey.backend.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefGroup;

import java.util.UUID;

public interface EntityDefGroupRepository extends JpaRepository<EntityDefGroup, UUID>,
        JpaSpecificationExecutor<EntityDefGroup> {
}
