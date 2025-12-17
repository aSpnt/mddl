package ru.softmachine.odyssey.backend.cms.converter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.FieldGeometryValueDto;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseExternalRef;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.entity.Entity;
import ru.softmachine.odyssey.backend.cms.entity.ExternalDictionaryValue;
import ru.softmachine.odyssey.backend.cms.entity.FieldGeometryValue;
import ru.softmachine.odyssey.backend.cms.entity.FieldValue;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T17:26:12+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class FieldValueConverterImpl extends FieldValueConverter {

    @Autowired
    private ReferenceMapper referenceMapper;
    @Autowired
    private CommonBaseRefConverter commonBaseRefConverter;
    @Autowired
    private EntityConverter entityConverter;
    @Autowired
    private DictionaryExternalValueConverter dictionaryExternalValueConverter;
    @Autowired
    private FieldGeometryValueConverter fieldGeometryValueConverter;

    @Override
    public BaseRef convertToBaseRef(FieldValue entity) {
        if ( entity == null ) {
            return null;
        }

        BaseRef baseRef = new BaseRef();

        baseRef.setId( entity.getId() );

        return baseRef;
    }

    @Override
    public FieldValue convertToModel(FieldValueDto dto) {
        if ( dto == null ) {
            return null;
        }

        FieldValue fieldValue = referenceMapper.resolve( dto, FieldValue.class );

        fieldValue.setRefValue( entityConverter.baseEntityMapperByRef( dto.getRefValue() ) );
        fieldValue.setFieldDef( commonBaseRefConverter.baseFieldDefMapperByBAseDto( dto.getFieldDef() ) );
        fieldValue.setId( dto.getId() );
        fieldValue.setCreatedTs( entityConverter.deserializeDateTime( dto.getCreatedTs() ) );
        fieldValue.setUpdatedTs( entityConverter.deserializeDateTime( dto.getUpdatedTs() ) );
        fieldValue.setExternalValues( baseExternalRefListToExternalDictionaryValueList( dto.getExternalValues() ) );
        fieldValue.setEntities( entityDtoListToEntityList( dto.getEntities() ) );
        fieldValue.setRefValueCollection( baseRefListToEntityList( dto.getRefValueCollection() ) );
        fieldValue.setGeometryValues( fieldGeometryValueDtoListToFieldGeometryValueList( dto.getGeometryValues() ) );
        fieldValue.setVisibleOverride( dto.getVisibleOverride() );
        fieldValue.setDisabledOverride( dto.getDisabledOverride() );
        fieldValue.setSeqOverride( dto.getSeqOverride() );
        fieldValue.setRequiredOverride( dto.getRequiredOverride() );
        List<String> list4 = dto.getAllowTemplatesOverride();
        if ( list4 != null ) {
            fieldValue.setAllowTemplatesOverride( new ArrayList<String>( list4 ) );
        }
        List<String> list5 = dto.getPredefinedSelectionsOverride();
        if ( list5 != null ) {
            fieldValue.setPredefinedSelectionsOverride( new ArrayList<String>( list5 ) );
        }
        fieldValue.setBooleanValue( dto.getBooleanValue() );
        fieldValue.setDoubleValue( dto.getDoubleValue() );
        fieldValue.setIntValue( dto.getIntValue() );
        fieldValue.setTextValue( dto.getTextValue() );
        fieldValue.setDateValue( entityConverter.deserializeDate( dto.getDateValue() ) );
        fieldValue.setTimeValue( dto.getTimeValue() );
        fieldValue.setDatetimeValue( entityConverter.deserializeDateTime( dto.getDatetimeValue() ) );
        List<String> list6 = dto.getArrayText();
        if ( list6 != null ) {
            fieldValue.setArrayText( new ArrayList<String>( list6 ) );
        }
        List<LocalDate> list7 = dto.getArrayDate();
        if ( list7 != null ) {
            fieldValue.setArrayDate( new ArrayList<LocalDate>( list7 ) );
        }
        List<ZonedDateTime> list8 = dto.getArrayDateTime();
        if ( list8 != null ) {
            fieldValue.setArrayDateTime( new ArrayList<ZonedDateTime>( list8 ) );
        }
        fieldValue.setEntityValue( entityConverter.convertToModel( dto.getEntityValue() ) );

        mapFieldValueFields( fieldValue );
        filterDuplicateExternalValues( fieldValue );

        return fieldValue;
    }

    @Override
    public FieldValueDto convertToDto(FieldValue entity) {
        if ( entity == null ) {
            return null;
        }

        FieldValueDto fieldValueDto = new FieldValueDto();

        fieldValueDto.setFieldDef( commonBaseRefConverter.convertToBaseFieldDef( entity.getFieldDef() ) );
        fieldValueDto.setEntityValue( entityToDtoWithFullView( entity.getEntityValue() ) );
        fieldValueDto.setId( entity.getId() );
        fieldValueDto.setCreatedTs( entityConverter.deserializeDateTime( entity.getCreatedTs() ) );
        fieldValueDto.setUpdatedTs( entityConverter.deserializeDateTime( entity.getUpdatedTs() ) );
        fieldValueDto.setBooleanValue( entity.getBooleanValue() );
        fieldValueDto.setDoubleValue( entity.getDoubleValue() );
        fieldValueDto.setIntValue( entity.getIntValue() );
        fieldValueDto.setTextValue( entity.getTextValue() );
        fieldValueDto.setDateValue( entityConverter.deserializeDate( entity.getDateValue() ) );
        fieldValueDto.setTimeValue( entity.getTimeValue() );
        fieldValueDto.setDatetimeValue( entityConverter.deserializeDateTime( entity.getDatetimeValue() ) );
        fieldValueDto.setRefValue( entityConverter.convertToDto( entity.getRefValue() ) );
        fieldValueDto.setVisibleOverride( entity.getVisibleOverride() );
        fieldValueDto.setDisabledOverride( entity.getDisabledOverride() );
        fieldValueDto.setRequiredOverride( entity.getRequiredOverride() );
        fieldValueDto.setSeqOverride( entity.getSeqOverride() );
        List<String> list = entity.getAllowTemplatesOverride();
        if ( list != null ) {
            fieldValueDto.setAllowTemplatesOverride( new ArrayList<String>( list ) );
        }
        List<String> list1 = entity.getPredefinedSelectionsOverride();
        if ( list1 != null ) {
            fieldValueDto.setPredefinedSelectionsOverride( new ArrayList<String>( list1 ) );
        }
        fieldValueDto.setRefValueCollection( entityListToBaseRefList( entity.getRefValueCollection() ) );
        List<String> list3 = entity.getArrayText();
        if ( list3 != null ) {
            fieldValueDto.setArrayText( new ArrayList<String>( list3 ) );
        }
        List<LocalDate> list4 = entity.getArrayDate();
        if ( list4 != null ) {
            fieldValueDto.setArrayDate( new ArrayList<LocalDate>( list4 ) );
        }
        List<ZonedDateTime> list5 = entity.getArrayDateTime();
        if ( list5 != null ) {
            fieldValueDto.setArrayDateTime( new ArrayList<ZonedDateTime>( list5 ) );
        }
        fieldValueDto.setEntities( entityListToEntityDtoList( entity.getEntities() ) );
        fieldValueDto.setGeometryValues( fieldGeometryValueListToFieldGeometryValueDtoList( entity.getGeometryValues() ) );
        fieldValueDto.setExternalValues( externalDictionaryValueListToBaseExternalRefList( entity.getExternalValues() ) );

        sortFieldValueDtoFields( fieldValueDto );

        return fieldValueDto;
    }

    protected List<ExternalDictionaryValue> baseExternalRefListToExternalDictionaryValueList(List<BaseExternalRef> list) {
        if ( list == null ) {
            return null;
        }

        List<ExternalDictionaryValue> list1 = new ArrayList<ExternalDictionaryValue>( list.size() );
        for ( BaseExternalRef baseExternalRef : list ) {
            list1.add( dictionaryExternalValueConverter.convertToModel( baseExternalRef ) );
        }

        return list1;
    }

    protected List<Entity> entityDtoListToEntityList(List<EntityDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Entity> list1 = new ArrayList<Entity>( list.size() );
        for ( EntityDto entityDto : list ) {
            list1.add( entityConverter.convertToModel( entityDto ) );
        }

        return list1;
    }

    protected Entity baseRefToEntity(BaseRef baseRef) {
        if ( baseRef == null ) {
            return null;
        }

        Entity entity = referenceMapper.resolve( baseRef, Entity.class );

        entity.setId( baseRef.getId() );

        return entity;
    }

    protected List<Entity> baseRefListToEntityList(List<BaseRef> list) {
        if ( list == null ) {
            return null;
        }

        List<Entity> list1 = new ArrayList<Entity>( list.size() );
        for ( BaseRef baseRef : list ) {
            list1.add( baseRefToEntity( baseRef ) );
        }

        return list1;
    }

    protected List<FieldGeometryValue> fieldGeometryValueDtoListToFieldGeometryValueList(List<FieldGeometryValueDto> list) {
        if ( list == null ) {
            return null;
        }

        List<FieldGeometryValue> list1 = new ArrayList<FieldGeometryValue>( list.size() );
        for ( FieldGeometryValueDto fieldGeometryValueDto : list ) {
            list1.add( fieldGeometryValueConverter.convertToModel( fieldGeometryValueDto ) );
        }

        return list1;
    }

    protected List<BaseRef> entityListToBaseRefList(List<Entity> list) {
        if ( list == null ) {
            return null;
        }

        List<BaseRef> list1 = new ArrayList<BaseRef>( list.size() );
        for ( Entity entity : list ) {
            list1.add( entityConverter.convertToBaseRef( entity ) );
        }

        return list1;
    }

    protected List<FieldGeometryValueDto> fieldGeometryValueListToFieldGeometryValueDtoList(List<FieldGeometryValue> list) {
        if ( list == null ) {
            return null;
        }

        List<FieldGeometryValueDto> list1 = new ArrayList<FieldGeometryValueDto>( list.size() );
        for ( FieldGeometryValue fieldGeometryValue : list ) {
            list1.add( fieldGeometryValueConverter.convertToDto( fieldGeometryValue ) );
        }

        return list1;
    }

    protected List<BaseExternalRef> externalDictionaryValueListToBaseExternalRefList(List<ExternalDictionaryValue> list) {
        if ( list == null ) {
            return null;
        }

        List<BaseExternalRef> list1 = new ArrayList<BaseExternalRef>( list.size() );
        for ( ExternalDictionaryValue externalDictionaryValue : list ) {
            list1.add( dictionaryExternalValueConverter.convertToDto( externalDictionaryValue ) );
        }

        return list1;
    }
}
