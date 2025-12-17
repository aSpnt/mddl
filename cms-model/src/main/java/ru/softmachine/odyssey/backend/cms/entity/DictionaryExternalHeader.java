package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;

@jakarta.persistence.Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "dictionary_external_header", schema = "meta")
@Accessors(chain = true)
public class DictionaryExternalHeader extends BaseEntity {

    private String name;

    private String value;

    @Column(name = "is_spel")
    private Boolean isSpel;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "dictionary_external_id")
    private DictionaryExternal dictionaryExternal;
}
