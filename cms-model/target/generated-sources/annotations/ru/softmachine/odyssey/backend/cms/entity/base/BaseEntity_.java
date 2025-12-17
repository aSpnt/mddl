package ru.softmachine.odyssey.backend.cms.entity.base;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.ZonedDateTime;

@StaticMetamodel(BaseEntity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class BaseEntity_ extends ru.softmachine.odyssey.backend.cms.entity.base.UidIdentEntity_ {

	public static final String UPDATED_TS = "updatedTs";
	public static final String CREATED_TS = "createdTs";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity#updatedTs
	 **/
	public static volatile SingularAttribute<BaseEntity, ZonedDateTime> updatedTs;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity
	 **/
	public static volatile MappedSuperclassType<BaseEntity> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity#createdTs
	 **/
	public static volatile SingularAttribute<BaseEntity, ZonedDateTime> createdTs;

}

