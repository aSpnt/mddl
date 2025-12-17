package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldDefContainerType;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;

@StaticMetamodel(FieldDefContainer.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class FieldDefContainer_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

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
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer#disableCondition
	 **/
	public static volatile SingularAttribute<FieldDefContainer, String> disableCondition;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer#parent
	 **/
	public static volatile SingularAttribute<FieldDefContainer, FieldDefContainer> parent;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer#visibleCondition
	 **/
	public static volatile SingularAttribute<FieldDefContainer, String> visibleCondition;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer#code
	 **/
	public static volatile SingularAttribute<FieldDefContainer, String> code;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer#name
	 **/
	public static volatile SingularAttribute<FieldDefContainer, String> name;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer#type
	 **/
	public static volatile SingularAttribute<FieldDefContainer, FieldDefContainerType> type;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer#fields
	 **/
	public static volatile ListAttribute<FieldDefContainer, FieldDef> fields;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer
	 **/
	public static volatile EntityType<FieldDefContainer> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer#childContainers
	 **/
	public static volatile ListAttribute<FieldDefContainer, FieldDefContainer> childContainers;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer#seq
	 **/
	public static volatile SingularAttribute<FieldDefContainer, Integer> seq;

}

