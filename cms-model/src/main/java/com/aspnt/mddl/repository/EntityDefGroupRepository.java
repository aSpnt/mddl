package com.aspnt.mddl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.aspnt.mddl.entity.EntityDefGroup;

import java.util.UUID;

public interface EntityDefGroupRepository extends JpaRepository<EntityDefGroup, UUID>,
        JpaSpecificationExecutor<EntityDefGroup> {
}
