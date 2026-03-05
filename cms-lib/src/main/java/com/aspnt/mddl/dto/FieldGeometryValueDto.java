package com.aspnt.mddl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.aspnt.mddl.dto.base.BaseDto;
import com.aspnt.mddl.dto.geo.GeoPoint;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldGeometryValueDto extends BaseDto {

    private String title;

    private String message;

    private GeoPoint geom;
}
