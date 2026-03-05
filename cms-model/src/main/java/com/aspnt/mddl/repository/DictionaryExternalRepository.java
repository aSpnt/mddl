package com.aspnt.mddl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.aspnt.mddl.entity.DictionaryExternal;

import java.util.UUID;

public interface DictionaryExternalRepository extends JpaRepository<DictionaryExternal, UUID>,
        JpaSpecificationExecutor<DictionaryExternal> {
}
