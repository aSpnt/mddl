package ru.softmachine.odyssey.backend.cms.converter;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.field.BaseFieldDefDto;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefMode;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T17:26:12+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class CommonBaseRefConverterImpl extends CommonBaseRefConverter {

    @Autowired
    private ReferenceMapper referenceMapper;

    @Override
    public BaseFieldDefDto convertToBaseFieldDef(FieldDef entity) {
        if ( entity == null ) {
            return null;
        }

        BaseFieldDefDto baseFieldDefDto = new BaseFieldDefDto();

        baseFieldDefDto.setId( entity.getId() );
        baseFieldDefDto.setCreatedTs( entity.getCreatedTs() );
        baseFieldDefDto.setUpdatedTs( entity.getUpdatedTs() );
        baseFieldDefDto.setFieldDefType( entity.getFieldDefType() );
        baseFieldDefDto.setName( entity.getName() );
        baseFieldDefDto.setCode( entity.getCode() );
        baseFieldDefDto.setType( entity.getType() );
        baseFieldDefDto.setNote( entity.getNote() );
        baseFieldDefDto.setPlaceholder( entity.getPlaceholder() );
        baseFieldDefDto.setSuffix( entity.getSuffix() );
        baseFieldDefDto.setPrefix( entity.getPrefix() );
        baseFieldDefDto.setSeq( entity.getSeq() );
        baseFieldDefDto.setTableSeq( entity.getTableSeq() );
        baseFieldDefDto.setOrderInTable( entity.isOrderInTable() );
        baseFieldDefDto.setDefaultOrder( entity.isDefaultOrder() );
        baseFieldDefDto.setTableWidth( entity.getTableWidth() );
        baseFieldDefDto.setVisibleCondition( entity.getVisibleCondition() );
        baseFieldDefDto.setVisibleItemCondition( entity.getVisibleItemCondition() );
        baseFieldDefDto.setDisableCondition( entity.getDisableCondition() );
        baseFieldDefDto.setResetDependencyFieldCode( entity.getResetDependencyFieldCode() );
        baseFieldDefDto.setRefCollectionFieldCode( entity.getRefCollectionFieldCode() );
        List<String> list = entity.getAllowedTemplates();
        if ( list != null ) {
            baseFieldDefDto.setAllowedTemplates( new ArrayList<String>( list ) );
        }
        baseFieldDefDto.setViewType( entity.getViewType() );
        baseFieldDefDto.setSpan( entity.getSpan() );
        baseFieldDefDto.setMultiple( entity.isMultiple() );
        baseFieldDefDto.setAllowDuplicates( entity.isAllowDuplicates() );
        baseFieldDefDto.setDisabled( entity.isDisabled() );
        baseFieldDefDto.setVisibleTable( entity.isVisibleTable() );
        baseFieldDefDto.setCompactTableView( entity.isCompactTableView() );
        baseFieldDefDto.setVisibleView( entity.isVisibleView() );
        baseFieldDefDto.setVisibleShort( entity.isVisibleShort() );
        baseFieldDefDto.setVisibleForm( entity.isVisibleForm() );
        baseFieldDefDto.setVisibleListView( entity.isVisibleListView() );
        baseFieldDefDto.setVisibleHeader( entity.isVisibleHeader() );
        baseFieldDefDto.setVisibleTemplate( entity.isVisibleTemplate() );
        baseFieldDefDto.setHideOnCreate( entity.isHideOnCreate() );
        baseFieldDefDto.setUseFilter( entity.isUseFilter() );
        baseFieldDefDto.setExternalFilterName( entity.getExternalFilterName() );
        baseFieldDefDto.setExternalFilterLowBoundaryName( entity.getExternalFilterLowBoundaryName() );
        baseFieldDefDto.setExternalFilterUpBoundaryName( entity.getExternalFilterUpBoundaryName() );
        baseFieldDefDto.setLabelInside( entity.isLabelInside() );
        baseFieldDefDto.setUseSearchFilter( entity.isUseSearchFilter() );
        baseFieldDefDto.setSerializeEnum( entity.getSerializeEnum() );
        baseFieldDefDto.setSerializeFull( entity.getSerializeFull() );
        baseFieldDefDto.setAllowCollectionRestriction( entity.isAllowCollectionRestriction() );
        baseFieldDefDto.setAllowCollectionRemove( entity.isAllowCollectionRemove() );
        baseFieldDefDto.setMode( entityDefModeToBaseRef( entity.getMode() ) );

        return baseFieldDefDto;
    }

    @Override
    public FieldDef baseFieldDefMapperByBAseDto(BaseDto ref) {
        if ( ref == null ) {
            return null;
        }

        FieldDef fieldDef = referenceMapper.resolve( ref, FieldDef.class );

        return fieldDef;
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
}
