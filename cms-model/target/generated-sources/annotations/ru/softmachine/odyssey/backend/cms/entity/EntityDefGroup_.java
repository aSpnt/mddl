package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EntityDefGroup.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class EntityDefGroup_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

	public static final String PARENT = "parent";
	public static final String CODE = "code";
	public static final String NAME = "name";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefGroup#parent
	 **/
	public static volatile SingularAttribute<EntityDefGroup, EntityDefGroup> parent;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefGroup#code
	 **/
	public static volatile SingularAttribute<EntityDefGroup, String> code;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefGroup#name
	 **/
	public static volatile SingularAttribute<EntityDefGroup, String> name;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefGroup
	 **/
	public static volatile EntityType<EntityDefGroup> class_;

}

