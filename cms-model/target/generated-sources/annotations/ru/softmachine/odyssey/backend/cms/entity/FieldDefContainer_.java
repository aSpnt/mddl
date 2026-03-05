package com.aspnt.mddl.entity;

import com.aspnt.mddl.entity.FieldDefContainer;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import com.aspnt.mddl.dto.base.FieldDefContainerType;
import com.aspnt.mddl.entity.field.FieldDef;

@StaticMetamodel(FieldDefContainer.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class FieldDefContainer_ extends com.aspnt.mddl.entity.base.BaseEntity_ {

	public static final String DISABLE_CONDITION = "disableCondition";
	public static final String PARENT = "parent";
	public static final String VISIBLE_CONDITION = "visibleCondition";
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String FIELDS = "fields";
	public static final String CHILD_CONTAINERS = "childContainers";
	public static final String SEQ = "seq";


	/**
	 * @see FieldDefContainer#disableCondition
	 **/
	public static volatile SingularAttribute<FieldDefContainer, String> disableCondition;

	/**
	 * @see FieldDefContainer#parent
	 **/
	public static volatile SingularAttribute<FieldDefContainer, FieldDefContainer> parent;

	/**
	 * @see FieldDefContainer#visibleCondition
	 **/
	public static volatile SingularAttribute<FieldDefContainer, String> visibleCondition;

	/**
	 * @see FieldDefContainer#code
	 **/
	public static volatile SingularAttribute<FieldDefContainer, String> code;

	/**
	 * @see FieldDefContainer#name
	 **/
	public static volatile SingularAttribute<FieldDefContainer, String> name;

	/**
	 * @see FieldDefContainer#type
	 **/
	public static volatile SingularAttribute<FieldDefContainer, FieldDefContainerType> type;

	/**
	 * @see FieldDefContainer#fields
	 **/
	public static volatile ListAttribute<FieldDefContainer, FieldDef> fields;

	/**
	 * @see FieldDefContainer
	 **/
	public static volatile EntityType<FieldDefContainer> class_;

	/**
	 * @see FieldDefContainer#childContainers
	 **/
	public static volatile ListAttribute<FieldDefContainer, FieldDefContainer> childContainers;

	/**
	 * @see FieldDefContainer#seq
	 **/
	public static volatile SingularAttribute<FieldDefContainer, Integer> seq;

}

