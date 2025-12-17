package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import org.locationtech.jts.geom.Point;

@StaticMetamodel(FieldGeometryValue.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class FieldGeometryValue_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

	public static final String TITLE = "title";
	public static final String MESSAGE = "message";
	public static final String GEOM = "geom";
	public static final String FIELD_VALUE = "fieldValue";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldGeometryValue#title
	 **/
	public static volatile SingularAttribute<FieldGeometryValue, String> title;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldGeometryValue#message
	 **/
	public static volatile SingularAttribute<FieldGeometryValue, String> message;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldGeometryValue#geom
	 **/
	public static volatile SingularAttribute<FieldGeometryValue, Point> geom;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldGeometryValue
	 **/
	public static volatile EntityType<FieldGeometryValue> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.FieldGeometryValue#fieldValue
	 **/
	public static volatile SingularAttribute<FieldGeometryValue, FieldValue> fieldValue;

}

