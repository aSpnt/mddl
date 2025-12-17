package ru.softmachine.odyssey.backend.cms.dto.provider;

public enum ProviderType {
    /**
     * Провайдер по умолчанию хранит данные сущности в основном датасорсе приложения
     */
    DEFAULT,
    /**
     * Внешний провайдер взаимодействие с которым строится через протокол HTTP
     * (подразумевается REST принцип организации API на стороне удаленного сервиса)
     */
    EXTERNAL_REST,
    /**
     * Внешний датасорс (пока не поддерживается)
     */
    EXTERNAL_DATASOURCE,
}
