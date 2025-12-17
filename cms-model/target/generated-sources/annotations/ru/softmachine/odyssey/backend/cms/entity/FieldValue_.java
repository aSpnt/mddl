package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;

@StaticMetamodel(FieldValue.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class FieldValue_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

	public static final String DATE_VALUE = "dateValue";
	public static final String GEOMETRY_VALUES = "geometryValues";
	public static final String FIELD_DEF = "fieldDef";
	public static final String DISABLED_OVERRIDE = "disabledOverride";
	public static final String TIME_VALUE = "timeValue";
	public static final String REF_VALUE = "refValue";
	public static final String REF_VALUE_COLLECTION = "refValueCollection";
	public static final String VISIBLE_OVERRIDE = "visibleOverride";
	public static final String ENTITY_VALUE = "entityValue";
	public static final String ALLOW_TEMPLATES_OVERRIDE = "allowTemplatesOverride";
	public static final String SEQ_OVERRIDE = "seqOverride";
	public static final String REQUIRED_OVERRIDE = "requiredOverride";
	public static final String TEXT_VALUE = "textValue";
	public static final String INT_VALUE = "intValue";
	public static final String DOUBLE_VALUE = "doubleValue";
	public static final String ARRAY_DATE = "arrayDate";
	public static final String PREDEFINED_SELECTIONS_OVERRIDE = "predefinedSelectionsOverride";
	public static final String ARRAY_DATE_TIME = "arrayDateTime";
	public static final String ENTITY_TEMPLATE = "entityTemplate";
	public static final String EXTERNAL_VALUES = "externalValues";
	public static final String ENTITIES = "entities";
	public static final String DATETIME_VALUE = "datetimeValue";
	public static final String ARRAY_TEXT = "arrayText";
	public static final String BOOLEAN_VALUE = "booleanValue";
	public static final String ENTITY = "entity";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#dateValue
	 **/
	public static volatile SingularAttribute<FieldValue, LocalDate> dateValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#geometryValues
	 **/
	public static volatile ListAttribute<FieldValue, FieldGeometryValue> geometryValues;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#fieldDef
	 **/
	public static volatile SingularAttribute<FieldValue, FieldDef> fieldDef;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#disabledOverride
	 **/
	public static volatile SingularAttribute<FieldValue, Boolean> disabledOverride;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#timeValue
	 **/
	public static volatile SingularAttribute<FieldValue, LocalTime> timeValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#refValue
	 **/
	public static volatile SingularAttribute<FieldValue, Entity> refValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#refValueCollection
	 **/
	public static volatile ListAttribute<FieldValue, Entity> refValueCollection;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#visibleOverride
	 **/
	public static volatile SingularAttribute<FieldValue, Boolean> visibleOverride;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#entityValue
	 **/
	public static volatile SingularAttribute<FieldValue, Entity> entityValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#allowTemplatesOverride
	 **/
	public static volatile SingularAttribute<FieldValue, List<String>> allowTemplatesOverride;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#seqOverride
	 **/
	public static volatile SingularAttribute<FieldValue, Integer> seqOverride;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue
	 **/
	public static volatile EntityType<FieldValue> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#requiredOverride
	 **/
	public static volatile SingularAttribute<FieldValue, Boolean> requiredOverride;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#textValue
	 **/
	public static volatile SingularAttribute<FieldValue, String> textValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#intValue
	 **/
	public static volatile SingularAttribute<FieldValue, Long> intValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#doubleValue
	 **/
	public static volatile SingularAttribute<FieldValue, Double> doubleValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#arrayDate
	 **/
	public static volatile SingularAttribute<FieldValue, List<LocalDate>> arrayDate;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#predefinedSelectionsOverride
	 **/
	public static volatile SingularAttribute<FieldValue, List<String>> predefinedSelectionsOverride;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#arrayDateTime
	 **/
	public static volatile SingularAttribute<FieldValue, List<ZonedDateTime>> arrayDateTime;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#entityTemplate
	 **/
	public static volatile SingularAttribute<FieldValue, EntityTemplate> entityTemplate;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#externalValues
	 **/
	public static volatile ListAttribute<FieldValue, ExternalDictionaryValue> externalValues;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#entities
	 **/
	public static volatile ListAttribute<FieldValue, Entity> entities;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#datetimeValue
	 **/
	public static volatile SingularAttribute<FieldValue, ZonedDateTime> datetimeValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#arrayText
	 **/
	public static volatile SingularAttribute<FieldValue, List<String>> arrayText;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#booleanValue
	 **/
	public static volatile SingularAttribute<FieldValue, Boolean> booleanValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValue#entity
	 **/
	public static volatile SingularAttribute<FieldValue, Entity> entity;

}

