package ru.softmachine.odyssey.backend.cms.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.CollectionMappingStrategy.ACCESSOR_ONLY;

@MapperConfig(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = ACCESSOR_ONLY,
        componentModel = "spring"
)
public class ConverterConfig {
}
