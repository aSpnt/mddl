package ru.softmachine.odyssey.backend.cms.dto.geo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Простейшее представление точки, предполагается что клиент и сервер работают по одному SRID
 */
@Data
@Accessors(chain = true)
public class GeoPoint {
    private Double latitude;
    private Double longitude;
}
