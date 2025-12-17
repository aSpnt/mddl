package ru.softmachine.odyssey.backend.cms.entity.base;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;

@StaticMetamodel(UidIdentEntity.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class UidIdentEntity_ {

	public static final String ID = "id";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.base.UidIdentEntity#id
	 **/
	public static volatile SingularAttribute<UidIdentEntity, UUID> id;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.base.UidIdentEntity
	 **/
	public static volatile MappedSuperclassType<UidIdentEntity> class_;

}

