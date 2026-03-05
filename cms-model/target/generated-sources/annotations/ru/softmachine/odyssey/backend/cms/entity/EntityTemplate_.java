package com.aspnt.mddl.entity;

import com.aspnt.mddl.entity.EntityTemplate;
import com.aspnt.mddl.entity.FieldValue;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import com.aspnt.mddl.dto.EntityTemplateStatus;
import com.aspnt.mddl.entity.entitydef.EntityDef;

@StaticMetamodel(EntityTemplate.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class EntityTemplate_ extends com.aspnt.mddl.entity.base.BaseEntity_ {

	public static final String GROUP_NAME = "groupName";
	public static final String CODE = "code";
	public static final String VALUES = "values";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ENTITY_DEF = "entityDef";
	public static final String SEQ = "seq";
	public static final String STATUS = "status";


	/**
	 * @see EntityTemplate#groupName
	 **/
	public static volatile SingularAttribute<EntityTemplate, String> groupName;

	/**
	 * @see EntityTemplate#code
	 **/
	public static volatile SingularAttribute<EntityTemplate, String> code;

	/**
	 * @see EntityTemplate#values
	 **/
	public static volatile ListAttribute<EntityTemplate, FieldValue> values;

	/**
	 * @see EntityTemplate#name
	 **/
	public static volatile SingularAttribute<EntityTemplate, String> name;

	/**
	 * @see EntityTemplate#description
	 **/
	public static volatile SingularAttribute<EntityTemplate, String> description;

	/**
	 * @see EntityTemplate#entityDef
	 **/
	public static volatile SingularAttribute<EntityTemplate, EntityDef> entityDef;

	/**
	 * @see EntityTemplate
	 **/
	public static volatile EntityType<EntityTemplate> class_;

	/**
	 * @see EntityTemplate#seq
	 **/
	public static volatile SingularAttribute<EntityTemplate, Integer> seq;

	/**
	 * @see EntityTemplate#status
	 **/
	public static volatile SingularAttribute<EntityTemplate, EntityTemplateStatus> status;

}

