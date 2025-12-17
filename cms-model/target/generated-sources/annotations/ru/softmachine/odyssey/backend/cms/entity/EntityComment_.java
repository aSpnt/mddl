package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EntityComment.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class EntityComment_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

	public static final String AUTHOR_EMAIL = "authorEmail";
	public static final String AUTHOR = "author";
	public static final String TITLE = "title";
	public static final String MESSAGE = "message";
	public static final String ENTITY = "entity";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityComment#authorEmail
	 **/
	public static volatile SingularAttribute<EntityComment, String> authorEmail;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityComment#author
	 **/
	public static volatile SingularAttribute<EntityComment, String> author;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityComment#title
	 **/
	public static volatile SingularAttribute<EntityComment, String> title;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityComment#message
	 **/
	public static volatile SingularAttribute<EntityComment, String> message;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityComment
	 **/
	public static volatile EntityType<EntityComment> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.EntityComment#entity
	 **/
	public static volatile SingularAttribute<EntityComment, Entity> entity;

}

