package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

@StaticMetamodel(EntityDefMode.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class EntityDefMode_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String ENTITY_DEF = "entityDef";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefMode#code
	 **/
	public static volatile SingularAttribute<EntityDefMode, String> code;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefMode#name
	 **/
	public static volatile SingularAttribute<EntityDefMode, String> name;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefMode#entityDef
	 **/
	public static volatile SingularAttribute<EntityDefMode, EntityDef> entityDef;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityDefMode
	 **/
	public static volatile EntityType<EntityDefMode> class_;

}

