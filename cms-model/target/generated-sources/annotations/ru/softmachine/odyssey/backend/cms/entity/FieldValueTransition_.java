package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.List;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;

@StaticMetamodel(FieldValueTransition.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class FieldValueTransition_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

	public static final String ENTITY_TO = "entityTo";
	public static final String FIELD_DEF = "fieldDef";
	public static final String MESSAGE = "message";
	public static final String COMMENT_NOTE = "commentNote";
	public static final String UPDATE_LAST_STATUS_TS = "updateLastStatusTs";
	public static final String COMMENT_TITLE = "commentTitle";
	public static final String SHOW_BUTTON = "showButton";
	public static final String COMMENT_REQUIRED = "commentRequired";
	public static final String ENTITY_FROM = "entityFrom";
	public static final String FIELD_CODES = "fieldCodes";
	public static final String NEED_COMMENT = "needComment";
	public static final String ENTITY_ACTIVE_STATUS = "entityActiveStatus";
	public static final String SEQ = "seq";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#entityTo
	 **/
	public static volatile SingularAttribute<FieldValueTransition, Entity> entityTo;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#fieldDef
	 **/
	public static volatile SingularAttribute<FieldValueTransition, FieldDef> fieldDef;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#message
	 **/
	public static volatile SingularAttribute<FieldValueTransition, String> message;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#commentNote
	 **/
	public static volatile SingularAttribute<FieldValueTransition, String> commentNote;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#updateLastStatusTs
	 **/
	public static volatile SingularAttribute<FieldValueTransition, Boolean> updateLastStatusTs;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#commentTitle
	 **/
	public static volatile SingularAttribute<FieldValueTransition, String> commentTitle;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#showButton
	 **/
	public static volatile SingularAttribute<FieldValueTransition, Boolean> showButton;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#commentRequired
	 **/
	public static volatile SingularAttribute<FieldValueTransition, Boolean> commentRequired;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#entityFrom
	 **/
	public static volatile SingularAttribute<FieldValueTransition, Entity> entityFrom;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#fieldCodes
	 **/
	public static volatile SingularAttribute<FieldValueTransition, List<String>> fieldCodes;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#needComment
	 **/
	public static volatile SingularAttribute<FieldValueTransition, Boolean> needComment;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#entityActiveStatus
	 **/
	public static volatile SingularAttribute<FieldValueTransition, Boolean> entityActiveStatus;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition
	 **/
	public static volatile EntityType<FieldValueTransition> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition#seq
	 **/
	public static volatile SingularAttribute<FieldValueTransition, Integer> seq;

}

