package ru.softmachine.odyssey.backend.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.softmachine.odyssey.backend.cms.entity.EntityComment;

import java.util.List;
import java.util.UUID;

public interface EntityCommentRepository extends JpaRepository<EntityComment, UUID> {

    @Query("select ec from EntityComment ec where ec.entity.id = :entityId")
    List<EntityComment> findByEntityId(UUID entityId);

    @Modifying
    @Query("delete from EntityComment ec where ec.entity.id = :entityId")
    void deleteByEntityId(UUID entityId);
}





