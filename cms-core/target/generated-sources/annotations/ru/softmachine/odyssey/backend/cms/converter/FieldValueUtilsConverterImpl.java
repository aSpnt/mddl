package ru.softmachine.odyssey.backend.cms.converter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.dto.FieldGeometryValueDto;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseExternalRef;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.dto.field.BaseFieldDefDto;
import ru.softmachine.odyssey.backend.cms.entity.Entity;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefMode;
import ru.softmachine.odyssey.backend.cms.entity.ExternalDictionaryValue;
import ru.softmachine.odyssey.backend.cms.entity.FieldGeometryValue;
import ru.softmachine.odyssey.backend.cms.entity.FieldValue;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T17:26:12+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class FieldValueUtilsConverterImpl extends FieldValueUtilsConverter {

    @Autowired
    private EntityConverter entityConverter;
    @Autowired
    private FieldGeometryValueConverter fieldGeometryValueConverter;
    @Autowired
    private DictionaryExternalValueConverter dictionaryExternalValueConverter;

    @Override
    public FieldValueDto mapWithoutId(FieldValue dto) {
        if ( dto == null ) {
            return null;
        }

        FieldValueDto fieldValueDto = new FieldValueDto();

        fieldValueDto.setGeometryValues( fieldGeometryValueListToFieldGeometryValueDtoList( dto.getGeometryValues() ) );
        fieldValueDto.setEntityValue( entityConverter.mapWithoutId( dto.getEntityValue() ) );
        fieldValueDto.setEntities( entityListToEntityDtoList( dto.getEntities() ) );
        fieldValueDto.setFieldDef( fieldDefToBaseFieldDefDto( dto.getFieldDef() ) );
        fieldValueDto.setBooleanValue( dto.getBooleanValue() );
        fieldValueDto.setDoubleValue( dto.getDoubleValue() );
        fieldValueDto.setIntValue( dto.getIntValue() );
        fieldValueDto.setTextValue( dto.getTextValue() );
        fieldValueDto.setDateValue( entityConverter.deserializeDate( dto.getDateValue() ) );
        fieldValueDto.setTimeValue( dto.getTimeValue() );
        fieldValueDto.setDatetimeValue( entityConverter.deserializeDateTime( dto.getDatetimeValue() ) );
        fieldValueDto.setRefValue( entityConverter.convertToDto( dto.getRefValue() ) );
        fieldValueDto.setVisibleOverride( dto.getVisibleOverride() );
        fieldValueDto.setDisabledOverride( dto.getDisabledOverride() );
        fieldValueDto.setRequiredOverride( dto.getRequiredOverride() );
        fieldValueDto.setSeqOverride( dto.getSeqOverride() );
        List<String> list2 = dto.getAllowTemplatesOverride();
        if ( list2 != null ) {
            fieldValueDto.setAllowTemplatesOverride( new ArrayList<String>( list2 ) );
        }
        List<String> list3 = dto.getPredefinedSelectionsOverride();
        if ( list3 != null ) {
            fieldValueDto.setPredefinedSelectionsOverride( new ArrayList<String>( list3 ) );
        }
        fieldValueDto.setRefValueCollection( entityListToBaseRefList( dto.getRefValueCollection() ) );
        List<String> list5 = dto.getArrayText();
        if ( list5 != null ) {
            fieldValueDto.setArrayText( new ArrayList<String>( list5 ) );
        }
        List<LocalDate> list6 = dto.getArrayDate();
        if ( list6 != null ) {
            fieldValueDto.setArrayDate( new ArrayList<LocalDate>( list6 ) );
        }
        List<ZonedDateTime> list7 = dto.getArrayDateTime();
        if ( list7 != null ) {
            fieldValueDto.setArrayDateTime( new ArrayList<ZonedDateTime>( list7 ) );
        }
        fieldValueDto.setExternalValues( externalDictionaryValueListToBaseExternalRefList( dto.getExternalValues() ) );

        return fieldValueDto;
    }

    protected List<FieldGeometryValueDto> fieldGeometryValueListToFieldGeometryValueDtoList(List<FieldGeometryValue> list) {
        if ( list == null ) {
            return null;
        }

        List<FieldGeometryValueDto> list1 = new ArrayList<FieldGeometryValueDto>( list.size() );
        for ( FieldGeometryValue fieldGeometryValue : list ) {
            list1.add( fieldGeometryValueConverter.mapWithoutId( fieldGeometryValue ) );
        }

        return list1;
    }

    protected List<EntityDto> entityListToEntityDtoList(List<Entity> list) {
        if ( list == null ) {
            return null;
        }

        List<EntityDto> list1 = new ArrayList<EntityDto>( list.size() );
        for ( Entity entity : list ) {
            list1.add( entityConverter.mapWithoutId( entity ) );
        }

        return list1;
    }

    protected BaseRef entityDefModeToBaseRef(EntityDefMode entityDefMode) {
        if ( entityDefMode == null ) {
            return null;
        }

        BaseRef baseRef = new BaseRef();

        baseRef.setId( entityDefMode.getId() );
        baseRef.setName( entityDefMode.getName() );

        return baseRef;
    }

    protected BaseFieldDefDto fieldDefToBaseFieldDefDto(FieldDef fieldDef) {
        if ( fieldDef == null ) {
            return null;
        }

        BaseFieldDefDto baseFieldDefDto = new BaseFieldDefDto();

        baseFieldDefDto.setId( fieldDef.getId() );
        baseFieldDefDto.setCreatedTs( entityConverter.deserializeDateTime( fieldDef.getCreatedTs() ) );
        baseFieldDefDto.setUpdatedTs( entityConverter.deserializeDateTime( fieldDef.getUpdatedTs() ) );
        baseFieldDefDto.setFieldDefType( fieldDef.getFieldDefType() );
        baseFieldDefDto.setName( fieldDef.getName() );
        baseFieldDefDto.setCode( fieldDef.getCode() );
        baseFieldDefDto.setType( fieldDef.getType() );
        baseFieldDefDto.setNote( fieldDef.getNote() );
        baseFieldDefDto.setPlaceholder( fieldDef.getPlaceholder() );
        baseFieldDefDto.setSuffix( fieldDef.getSuffix() );
        baseFieldDefDto.setPrefix( fieldDef.getPrefix() );
        baseFieldDefDto.setSeq( fieldDef.getSeq() );
        baseFieldDefDto.setTableSeq( fieldDef.getTableSeq() );
        baseFieldDefDto.setOrderInTable( fieldDef.isOrderInTable() );
        baseFieldDefDto.setDefaultOrder( fieldDef.isDefaultOrder() );
        baseFieldDefDto.setTableWidth( fieldDef.getTableWidth() );
        baseFieldDefDto.setVisibleCondition( fieldDef.getVisibleCondition() );
        baseFieldDefDto.setVisibleItemCondition( fieldDef.getVisibleItemCondition() );
        baseFieldDefDto.setDisableCondition( fieldDef.getDisableCondition() );
        baseFieldDefDto.setResetDependencyFieldCode( fieldDef.getResetDependencyFieldCode() );
        baseFieldDefDto.setRefCollectionFieldCode( fieldDef.getRefCollectionFieldCode() );
        List<String> list = fieldDef.getAllowedTemplates();
        if ( list != null ) {
            baseFieldDefDto.setAllowedTemplates( new ArrayList<String>( list ) );
        }
        baseFieldDefDto.setViewType( fieldDef.getViewType() );
        baseFieldDefDto.setSpan( fieldDef.getSpan() );
        baseFieldDefDto.setMultiple( fieldDef.isMultiple() );
        baseFieldDefDto.setAllowDuplicates( fieldDef.isAllowDuplicates() );
        baseFieldDefDto.setDisabled( fieldDef.isDisabled() );
        baseFieldDefDto.setVisibleTable( fieldDef.isVisibleTable() );
        baseFieldDefDto.setCompactTableView( fieldDef.isCompactTableView() );
        baseFieldDefDto.setVisibleView( fieldDef.isVisibleView() );
        baseFieldDefDto.setVisibleShort( fieldDef.isVisibleShort() );
        baseFieldDefDto.setVisibleForm( fieldDef.isVisibleForm() );
        baseFieldDefDto.setVisibleListView( fieldDef.isVisibleListView() );
        baseFieldDefDto.setVisibleHeader( fieldDef.isVisibleHeader() );
        baseFieldDefDto.setVisibleTemplate( fieldDef.isVisibleTemplate() );
        baseFieldDefDto.setHideOnCreate( fieldDef.isHideOnCreate() );
        baseFieldDefDto.setUseFilter( fieldDef.isUseFilter() );
        baseFieldDefDto.setExternalFilterName( fieldDef.getExternalFilterName() );
        baseFieldDefDto.setExternalFilterLowBoundaryName( fieldDef.getExternalFilterLowBoundaryName() );
        baseFieldDefDto.setExternalFilterUpBoundaryName( fieldDef.getExternalFilterUpBoundaryName() );
        baseFieldDefDto.setLabelInside( fieldDef.isLabelInside() );
        baseFieldDefDto.setUseSearchFilter( fieldDef.isUseSearchFilter() );
        baseFieldDefDto.setSerializeEnum( fieldDef.getSerializeEnum() );
        baseFieldDefDto.setSerializeFull( fieldDef.getSerializeFull() );
        baseFieldDefDto.setAllowCollectionRestriction( fieldDef.isAllowCollectionRestriction() );
        baseFieldDefDto.setAllowCollectionRemove( fieldDef.isAllowCollectionRemove() );
        baseFieldDefDto.setMode( entityDefModeToBaseRef( fieldDef.getMode() ) );

        return baseFieldDefDto;
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
