package com.aspnt.mddl.entity;

import com.aspnt.mddl.entity.Entity;
import com.aspnt.mddl.entity.EntityComment;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EntityComment.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class EntityComment_ extends com.aspnt.mddl.entity.base.BaseEntity_ {

	public static final String AUTHOR_EMAIL = "authorEmail";
	public static final String AUTHOR = "author";
	public static final String TITLE = "title";
	public static final String MESSAGE = "message";
	public static final String ENTITY = "entity";


	/**
	 * @see EntityComment#authorEmail
	 **/
	public static volatile SingularAttribute<EntityComment, String> authorEmail;

	/**
	 * @see EntityComment#author
	 **/
	public static volatile SingularAttribute<EntityComment, String> author;

	/**
	 * @see EntityComment#title
	 **/
	public static volatile SingularAttribute<EntityComment, String> title;

	/**
	 * @see EntityComment#message
	 **/
	public static volatile SingularAttribute<EntityComment, String> message;

	/**
	 * @see EntityComment
	 **/
	public static volatile EntityType<EntityComment> class_;

	/**
	 * @see EntityComment#entity
	 **/
	public static volatile SingularAttribute<EntityComment, Entity> entity;

}

