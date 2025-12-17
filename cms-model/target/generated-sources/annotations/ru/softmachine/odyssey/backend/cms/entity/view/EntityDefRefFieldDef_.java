package ru.softmachine.odyssey.backend.cms.entity.view;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;

@StaticMetamodel(EntityDefRefFieldDef.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class EntityDefRefFieldDef_ {

	public static final String FIELD_DEF_ID = "fieldDefId";
	public static final String ENTITY_DEF_CODE = "entityDefCode";
	public static final String FIELD_DEF_CODE = "fieldDefCode";
	public static final String ENTITY_DEF_ID = "entityDefId";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.view.EntityDefRefFieldDef#fieldDefId
	 **/
	public static volatile SingularAttribute<EntityDefRefFieldDef, UUID> fieldDefId;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.view.EntityDefRefFieldDef#entityDefCode
	 **/
	public static volatile SingularAttribute<EntityDefRefFieldDef, String> entityDefCode;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.view.EntityDefRefFieldDef#fieldDefCode
	 **/
	public static volatile SingularAttribute<EntityDefRefFieldDef, String> fieldDefCode;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.view.EntityDefRefFieldDef#entityDefId
	 **/
	public static volatile SingularAttribute<EntityDefRefFieldDef, UUID> entityDefId;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.view.EntityDefRefFieldDef
	 **/
	public static volatile EntityType<EntityDefRefFieldDef> class_;

}

