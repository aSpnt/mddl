package com.aspnt.mddl.entity;

import com.aspnt.mddl.entity.ExternalDictionaryValue;
import com.aspnt.mddl.entity.FieldValue;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.ZonedDateTime;

@StaticMetamodel(ExternalDictionaryValue.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class ExternalDictionaryValue_ {

	public static final String IMG = "img";
	public static final String REF = "ref";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String UPDATED_TS = "updatedTs";
	public static final String CREATED_TS = "createdTs";
	public static final String FIELD_VALUE = "fieldValue";
	public static final String SEQ = "seq";


	/**
	 * @see ExternalDictionaryValue#img
	 **/
	public static volatile SingularAttribute<ExternalDictionaryValue, String> img;

	/**
	 * @see ExternalDictionaryValue#ref
	 **/
	public static volatile SingularAttribute<ExternalDictionaryValue, String> ref;

	/**
	 * @see ExternalDictionaryValue#name
	 **/
	public static volatile SingularAttribute<ExternalDictionaryValue, String> name;

	/**
	 * @see ExternalDictionaryValue#description
	 **/
	public static volatile SingularAttribute<ExternalDictionaryValue, String> description;

	/**
	 * @see ExternalDictionaryValue#id
	 **/
	public static volatile SingularAttribute<ExternalDictionaryValue, String> id;

	/**
	 * @see ExternalDictionaryValue#updatedTs
	 **/
	public static volatile SingularAttribute<ExternalDictionaryValue, ZonedDateTime> updatedTs;

	/**
	 * @see ExternalDictionaryValue
	 **/
	public static volatile EntityType<ExternalDictionaryValue> class_;

	/**
	 * @see ExternalDictionaryValue#createdTs
	 **/
	public static volatile SingularAttribute<ExternalDictionaryValue, ZonedDateTime> createdTs;

	/**
	 * @see ExternalDictionaryValue#fieldValue
	 **/
	public static volatile SingularAttribute<ExternalDictionaryValue, FieldValue> fieldValue;

	/**
	 * @see ExternalDictionaryValue#seq
	 **/
	public static volatile SingularAttribute<ExternalDictionaryValue, Integer> seq;

}

