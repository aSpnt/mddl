package com.aspnt.mddl.entity;

import com.aspnt.mddl.entity.EntityDefExternalHeader;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import com.aspnt.mddl.entity.entitydef.EntityDef;

@StaticMetamodel(EntityDefExternalHeader.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class EntityDefExternalHeader_ extends com.aspnt.mddl.entity.base.BaseEntity_ {

	public static final String IS_SPEL = "isSpel";
	public static final String NAME = "name";
	public static final String ENTITY_DEF = "entityDef";
	public static final String VALUE = "value";


	/**
	 * @see EntityDefExternalHeader#isSpel
	 **/
	public static volatile SingularAttribute<EntityDefExternalHeader, Boolean> isSpel;

	/**
	 * @see EntityDefExternalHeader#name
	 **/
	public static volatile SingularAttribute<EntityDefExternalHeader, String> name;

	/**
	 * @see EntityDefExternalHeader#entityDef
	 **/
	public static volatile SingularAttribute<EntityDefExternalHeader, EntityDef> entityDef;

	/**
	 * @see EntityDefExternalHeader
	 **/
	public static volatile EntityType<EntityDefExternalHeader> class_;

	/**
	 * @see EntityDefExternalHeader#value
	 **/
	public static volatile SingularAttribute<EntityDefExternalHeader, String> value;

}

