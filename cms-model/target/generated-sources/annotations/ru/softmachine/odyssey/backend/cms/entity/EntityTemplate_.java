package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.softmachine.odyssey.backend.cms.dto.EntityTemplateStatus;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

@StaticMetamodel(EntityTemplate.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class EntityTemplate_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

	public static final String GROUP_NAME = "groupName";
	public static final String CODE = "code";
	public static final String VALUES = "values";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ENTITY_DEF = "entityDef";
	public static final String SEQ = "seq";
	public static final String STATUS = "status";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityTemplate#groupName
	 **/
	public static volatile SingularAttribute<EntityTemplate, String> groupName;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityTemplate#code
	 **/
	public static volatile SingularAttribute<EntityTemplate, String> code;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityTemplate#values
	 **/
	public static volatile ListAttribute<EntityTemplate, FieldValue> values;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityTemplate#name
	 **/
	public static volatile SingularAttribute<EntityTemplate, String> name;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityTemplate#description
	 **/
	public static volatile SingularAttribute<EntityTemplate, String> description;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityTemplate#entityDef
	 **/
	public static volatile SingularAttribute<EntityTemplate, EntityDef> entityDef;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityTemplate
	 **/
	public static volatile EntityType<EntityTemplate> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityTemplate#seq
	 **/
	public static volatile SingularAttribute<EntityTemplate, Integer> seq;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityTemplate#status
	 **/
	public static volatile SingularAttribute<EntityTemplate, EntityTemplateStatus> status;

}

