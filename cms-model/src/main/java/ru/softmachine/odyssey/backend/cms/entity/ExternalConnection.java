package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryHttpMethod;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;

import java.util.List;

@jakarta.persistence.Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "external_connection", schema = "meta")
@Accessors(chain = true)
public class ExternalConnection extends BaseEntity {

    private String url;

    @Enumerated(EnumType.STRING)
    private DictionaryHttpMethod method;

    private List<String> param;

    @Column(name = "response_param")
    private List<String> responseParam;
}
