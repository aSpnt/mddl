package ru.softmachine.odyssey.backend.cms.entity.validation;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.List;
import ru.softmachine.odyssey.backend.cms.dto.validation.ValidationType;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;

@StaticMetamodel(FieldValidation.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class FieldValidation_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

	public static final String TEXT_VALUE = "textValue";
	public static final String INT_VALUE = "intValue";
	public static final String TEXT_ARRAY_VALUE = "textArrayValue";
	public static final String DOUBLE_VALUE = "doubleValue";
	public static final String FIELD_DEF = "fieldDef";
	public static final String TYPE = "type";
	public static final String MESSAGE = "message";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation#textValue
	 **/
	public static volatile SingularAttribute<FieldValidation, String> textValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation#intValue
	 **/
	public static volatile SingularAttribute<FieldValidation, Integer> intValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation#textArrayValue
	 **/
	public static volatile SingularAttribute<FieldValidation, List<String>> textArrayValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation#doubleValue
	 **/
	public static volatile SingularAttribute<FieldValidation, Double> doubleValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation#fieldDef
	 **/
	public static volatile SingularAttribute<FieldValidation, FieldDef> fieldDef;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation#type
	 **/
	public static volatile SingularAttribute<FieldValidation, ValidationType> type;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation#message
	 **/
	public static volatile SingularAttribute<FieldValidation, String> message;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation
	 **/
	public static volatile EntityType<FieldValidation> class_;

}

