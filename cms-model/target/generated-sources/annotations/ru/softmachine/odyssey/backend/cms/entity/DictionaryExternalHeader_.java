package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DictionaryExternalHeader.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class DictionaryExternalHeader_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

	public static final String DICTIONARY_EXTERNAL = "dictionaryExternal";
	public static final String IS_SPEL = "isSpel";
	public static final String NAME = "name";
	public static final String VALUE = "value";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternalHeader#dictionaryExternal
	 **/
	public static volatile SingularAttribute<DictionaryExternalHeader, DictionaryExternal> dictionaryExternal;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternalHeader#isSpel
	 **/
	public static volatile SingularAttribute<DictionaryExternalHeader, Boolean> isSpel;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternalHeader#name
	 **/
	public static volatile SingularAttribute<DictionaryExternalHeader, String> name;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternalHeader
	 **/
	public static volatile EntityType<DictionaryExternalHeader> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternalHeader#value
	 **/
	public static volatile SingularAttribute<DictionaryExternalHeader, String> value;

}

