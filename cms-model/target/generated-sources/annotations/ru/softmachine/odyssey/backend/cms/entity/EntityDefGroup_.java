package com.aspnt.mddl.entity;

import com.aspnt.mddl.entity.EntityDefGroup;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EntityDefGroup.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class EntityDefGroup_ extends com.aspnt.mddl.entity.base.BaseEntity_ {

	public static final String PARENT = "parent";
	public static final String CODE = "code";
	public static final String NAME = "name";


	/**
	 * @see EntityDefGroup#parent
	 **/
	public static volatile SingularAttribute<EntityDefGroup, EntityDefGroup> parent;

	/**
	 * @see EntityDefGroup#code
	 **/
	public static volatile SingularAttribute<EntityDefGroup, String> code;

	/**
	 * @see EntityDefGroup#name
	 **/
	public static volatile SingularAttribute<EntityDefGroup, String> name;

	/**
	 * @see EntityDefGroup
	 **/
	public static volatile EntityType<EntityDefGroup> class_;

}

