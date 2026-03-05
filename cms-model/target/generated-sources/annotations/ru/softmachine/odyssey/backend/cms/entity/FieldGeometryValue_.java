package com.aspnt.mddl.entity;

import com.aspnt.mddl.entity.FieldGeometryValue;
import com.aspnt.mddl.entity.FieldValue;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import org.locationtech.jts.geom.Point;

@StaticMetamodel(FieldGeometryValue.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class FieldGeometryValue_ extends com.aspnt.mddl.entity.base.BaseEntity_ {

	public static final String TITLE = "title";
	public static final String MESSAGE = "message";
	public static final String GEOM = "geom";
	public static final String FIELD_VALUE = "fieldValue";


	/**
	 * @see FieldGeometryValue#title
	 **/
	public static volatile SingularAttribute<FieldGeometryValue, String> title;

	/**
	 * @see FieldGeometryValue#message
	 **/
	public static volatile SingularAttribute<FieldGeometryValue, String> message;

	/**
	 * @see FieldGeometryValue#geom
	 **/
	public static volatile SingularAttribute<FieldGeometryValue, Point> geom;

	/**
	 * @see FieldGeometryValue
	 **/
	public static volatile EntityType<FieldGeometryValue> class_;

	/**
	 * @see FieldGeometryValue#fieldValue
	 **/
	public static volatile SingularAttribute<FieldGeometryValue, FieldValue> fieldValue;

}

