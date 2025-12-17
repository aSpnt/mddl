package ru.softmachine.odyssey.backend.cms.entity.entitydef;

import ru.softmachine.odyssey.backend.cms.dto.provider.ProviderType;

import java.util.UUID;

public record EntityDefProviderProj(UUID id, ProviderType type) {
}
