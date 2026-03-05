package com.aspnt.mddl.entity.base;

import com.aspnt.mddl.entity.base.UidIdentEntity;
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
	 * @see UidIdentEntity#id
	 **/
	public static volatile SingularAttribute<UidIdentEntity, UUID> id;

	/**
	 * @see UidIdentEntity
	 **/
	public static volatile MappedSuperclassType<UidIdentEntity> class_;

}

