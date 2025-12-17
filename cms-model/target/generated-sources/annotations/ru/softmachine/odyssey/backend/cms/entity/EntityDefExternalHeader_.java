package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

@StaticMetamodel(EntityDefExternalHeader.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class EntityDefExternalHeader_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

	public static final String IS_SPEL = "isSpel";
	public static final String NAME = "name";
	public static final String ENTITY_DEF = "entityDef";
	public static final String VALUE = "value";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefExternalHeader#isSpel
	 **/
	public static volatile SingularAttribute<EntityDefExternalHeader, Boolean> isSpel;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefExternalHeader#name
	 **/
	public static volatile SingularAttribute<EntityDefExternalHeader, String> name;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefExternalHeader#entityDef
	 **/
	public static volatile SingularAttribute<EntityDefExternalHeader, EntityDef> entityDef;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefExternalHeader
	 **/
	public static volatile EntityType<EntityDefExternalHeader> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefExternalHeader#value
	 **/
	public static volatile SingularAttribute<EntityDefExternalHeader, String> value;

}

