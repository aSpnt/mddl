package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Point;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;


@jakarta.persistence.Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "geometry_value", schema = "meta")
@Accessors(chain = true)
public class FieldGeometryValue extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_value_id")
    private FieldValue fieldValue;

    @Column
    private String title;

    @Column
    private String message;

    @Column
    private Point geom;
}
