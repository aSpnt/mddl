package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.ZonedDateTime;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

@StaticMetamodel(Entity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Entity_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

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
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#authorEmail
	 **/
	public static volatile SingularAttribute<Entity, String> authorEmail;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#entityTemplateName
	 **/
	public static volatile SingularAttribute<Entity, String> entityTemplateName;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#author
	 **/
	public static volatile SingularAttribute<Entity, String> author;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#values
	 **/
	public static volatile ListAttribute<Entity, FieldValue> values;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#slugLock
	 **/
	public static volatile SingularAttribute<Entity, Boolean> slugLock;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#active
	 **/
	public static volatile SingularAttribute<Entity, Boolean> active;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#lastStatusChangeTs
	 **/
	public static volatile SingularAttribute<Entity, ZonedDateTime> lastStatusChangeTs;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#version
	 **/
	public static volatile SingularAttribute<Entity, Long> version;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#entityDef
	 **/
	public static volatile SingularAttribute<Entity, EntityDef> entityDef;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity
	 **/
	public static volatile EntityType<Entity> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#slug
	 **/
	public static volatile SingularAttribute<Entity, String> slug;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#seq
	 **/
	public static volatile SingularAttribute<Entity, Integer> seq;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#deleteLock
	 **/
	public static volatile SingularAttribute<Entity, Boolean> deleteLock;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.Entity#ftsVector
	 **/
	public static volatile SingularAttribute<Entity, String> ftsVector;

}

