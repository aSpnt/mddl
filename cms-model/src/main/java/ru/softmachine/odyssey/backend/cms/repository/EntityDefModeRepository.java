package ru.softmachine.odyssey.backend.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefMode;

import java.util.UUID;

public interface EntityDefModeRepository extends JpaRepository<EntityDefMode, UUID>,
        JpaSpecificationExecutor<EntityDefMode> {
}
