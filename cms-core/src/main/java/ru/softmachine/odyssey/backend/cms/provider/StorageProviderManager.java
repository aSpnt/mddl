package ru.softmachine.odyssey.backend.cms.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.dto.provider.ProviderType;
import ru.softmachine.odyssey.backend.cms.provider.impl.OwnEntityStorageProvider;
import ru.softmachine.odyssey.backend.cms.provider.impl.RestEntityStorageProvider;

@Slf4j
@Component
@RequiredArgsConstructor
public class StorageProviderManager {

    private final OwnEntityStorageProvider ownEntityStorageProvider;
    private final RestEntityStorageProvider restEntityStorageProvider;

    public EntityStorageProvider getProviderByEntityDef(ProviderType providerType) {
       return switch (providerType) {
           case DEFAULT -> ownEntityStorageProvider;
           case EXTERNAL_REST -> restEntityStorageProvider;
           case EXTERNAL_DATASOURCE -> throw new UnsupportedOperationException("Not implemented yet");
       };
    }
}
