package com.aspnt.mddl.entity;

import com.aspnt.mddl.entity.Entity;
import com.aspnt.mddl.entity.FieldValue;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.ZonedDateTime;
import com.aspnt.mddl.entity.entitydef.EntityDef;

@StaticMetamodel(Entity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Entity_ extends com.aspnt.mddl.entity.base.BaseEntity_ {

	public static final String AUTHOR_EMAIL = "authorEmail";
	public static final String ENTITY_TEMPLATE_NAME = "entityTemplateName";
	public static final String AUTHOR = "author";
	public static final String VALUES = "values";
	public static final String SLUG_LOCK = "slugLock";
	public static final String ACTIVE = "active";
	public static final String LAST_STATUS_CHANGE_TS = "lastStatusChangeTs";
	public static final String VERSION = "version";
	public static final String ENTITY_DEF = "entityDef";
	public static final String SLUG = "slug";
	public static final String SEQ = "seq";
	public static final String DELETE_LOCK = "deleteLock";
	public static final String FTS_VECTOR = "ftsVector";


	/**
	 * @see Entity#authorEmail
	 **/
	public static volatile SingularAttribute<Entity, String> authorEmail;

	/**
	 * @see Entity#entityTemplateName
	 **/
	public static volatile SingularAttribute<Entity, String> entityTemplateName;

	/**
	 * @see Entity#author
	 **/
	public static volatile SingularAttribute<Entity, String> author;

	/**
	 * @see Entity#values
	 **/
	public static volatile ListAttribute<Entity, FieldValue> values;

	/**
	 * @see Entity#slugLock
	 **/
	public static volatile SingularAttribute<Entity, Boolean> slugLock;

	/**
	 * @see Entity#active
	 **/
	public static volatile SingularAttribute<Entity, Boolean> active;

	/**
	 * @see Entity#lastStatusChangeTs
	 **/
	public static volatile SingularAttribute<Entity, ZonedDateTime> lastStatusChangeTs;

	/**
	 * @see Entity#version
	 **/
	public static volatile SingularAttribute<Entity, Long> version;

	/**
	 * @see Entity#entityDef
	 **/
	public static volatile SingularAttribute<Entity, EntityDef> entityDef;

	/**
	 * @see Entity
	 **/
	public static volatile EntityType<Entity> class_;

	/**
	 * @see Entity#slug
	 **/
	public static volatile SingularAttribute<Entity, String> slug;

	/**
	 * @see Entity#seq
	 **/
	public static volatile SingularAttribute<Entity, Integer> seq;

	/**
	 * @see Entity#deleteLock
	 **/
	public static volatile SingularAttribute<Entity, Boolean> deleteLock;

	/**
	 * @see Entity#ftsVector
	 **/
	public static volatile SingularAttribute<Entity, String> ftsVector;

}

