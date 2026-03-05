package com.aspnt.mddl.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.aspnt.mddl.dto.provider.ProviderType;
import com.aspnt.mddl.provider.impl.OwnEntityStorageProvider;
import com.aspnt.mddl.provider.impl.RestEntityStorageProvider;

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
