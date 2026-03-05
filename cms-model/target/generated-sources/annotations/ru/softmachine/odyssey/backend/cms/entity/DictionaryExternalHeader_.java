package com.aspnt.mddl.entity;

import com.aspnt.mddl.entity.DictionaryExternal;
import com.aspnt.mddl.entity.DictionaryExternalHeader;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DictionaryExternalHeader.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class DictionaryExternalHeader_ extends com.aspnt.mddl.entity.base.BaseEntity_ {

	public static final String DICTIONARY_EXTERNAL = "dictionaryExternal";
	public static final String IS_SPEL = "isSpel";
	public static final String NAME = "name";
	public static final String VALUE = "value";


	/**
	 * @see DictionaryExternalHeader#dictionaryExternal
	 **/
	public static volatile SingularAttribute<DictionaryExternalHeader, DictionaryExternal> dictionaryExternal;

	/**
	 * @see DictionaryExternalHeader#isSpel
	 **/
	public static volatile SingularAttribute<DictionaryExternalHeader, Boolean> isSpel;

	/**
	 * @see DictionaryExternalHeader#name
	 **/
	public static volatile SingularAttribute<DictionaryExternalHeader, String> name;

	/**
	 * @see DictionaryExternalHeader
	 **/
	public static volatile EntityType<DictionaryExternalHeader> class_;

	/**
	 * @see DictionaryExternalHeader#value
	 **/
	public static volatile SingularAttribute<DictionaryExternalHeader, String> value;

}

