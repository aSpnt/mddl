package com.aspnt.mddl.entity.base;

import com.aspnt.mddl.entity.base.BaseEntity;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.ZonedDateTime;

@StaticMetamodel(BaseEntity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class BaseEntity_ extends com.aspnt.mddl.entity.base.UidIdentEntity_ {

	public static final String UPDATED_TS = "updatedTs";
	public static final String CREATED_TS = "createdTs";


	/**
	 * @see BaseEntity#updatedTs
	 **/
	public static volatile SingularAttribute<BaseEntity, ZonedDateTime> updatedTs;

	/**
	 * @see BaseEntity
	 **/
	public static volatile MappedSuperclassType<BaseEntity> class_;

	/**
	 * @see BaseEntity#createdTs
	 **/
	public static volatile SingularAttribute<BaseEntity, ZonedDateTime> createdTs;

}

