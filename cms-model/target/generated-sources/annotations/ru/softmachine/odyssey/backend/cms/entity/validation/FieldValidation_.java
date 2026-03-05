package com.aspnt.mddl.entity.validation;

import com.aspnt.mddl.entity.validation.FieldValidation;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.List;
import com.aspnt.mddl.dto.validation.ValidationType;
import com.aspnt.mddl.entity.field.FieldDef;

@StaticMetamodel(FieldValidation.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class FieldValidation_ extends com.aspnt.mddl.entity.base.BaseEntity_ {

	public static final String TEXT_VALUE = "textValue";
	public static final String INT_VALUE = "intValue";
	public static final String TEXT_ARRAY_VALUE = "textArrayValue";
	public static final String DOUBLE_VALUE = "doubleValue";
	public static final String FIELD_DEF = "fieldDef";
	public static final String TYPE = "type";
	public static final String MESSAGE = "message";


	/**
	 * @see FieldValidation#textValue
	 **/
	public static volatile SingularAttribute<FieldValidation, String> textValue;

	/**
	 * @see FieldValidation#intValue
	 **/
	public static volatile SingularAttribute<FieldValidation, Integer> intValue;

	/**
	 * @see FieldValidation#textArrayValue
	 **/
	public static volatile SingularAttribute<FieldValidation, List<String>> textArrayValue;

	/**
	 * @see FieldValidation#doubleValue
	 **/
	public static volatile SingularAttribute<FieldValidation, Double> doubleValue;

	/**
	 * @see FieldValidation#fieldDef
	 **/
	public static volatile SingularAttribute<FieldValidation, FieldDef> fieldDef;

	/**
	 * @see FieldValidation#type
	 **/
	public static volatile SingularAttribute<FieldValidation, ValidationType> type;

	/**
	 * @see FieldValidation#message
	 **/
	public static volatile SingularAttribute<FieldValidation, String> message;

	/**
	 * @see FieldValidation
	 **/
	public static volatile EntityType<FieldValidation> class_;

}

