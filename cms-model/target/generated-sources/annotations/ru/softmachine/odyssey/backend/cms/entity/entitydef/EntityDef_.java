package com.aspnt.mddl.entity.entitydef;

import com.aspnt.mddl.entity.entitydef.EntityDef;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Map;
import com.aspnt.mddl.dto.DictionaryHttpMethod;
import com.aspnt.mddl.dto.EntityDefStatus;
import com.aspnt.mddl.dto.GlobalSearchType;
import com.aspnt.mddl.dto.provider.ProviderType;
import com.aspnt.mddl.entity.Entity;
import com.aspnt.mddl.entity.EntityDefExternalHeader;
import com.aspnt.mddl.entity.EntityDefGroup;
import com.aspnt.mddl.entity.EntityDefMode;
import com.aspnt.mddl.entity.EntityTemplate;
import com.aspnt.mddl.entity.FieldDefContainer;

@StaticMetamodel(EntityDef.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class EntityDef_ extends com.aspnt.mddl.entity.base.BaseEntity_ {

	public static final String ALLOW_DND = "allowDnd";
	public static final String CONTAINER = "container";
	public static final String CODE = "code";
	public static final String MODES = "modes";
	public static final String RESPONSE_TOTAL_NAME = "responseTotalName";
	public static final String PAGE_FILTER_NAME = "pageFilterName";
	public static final String PROVIDER_TYPE = "providerType";
	public static final String SINGLETON_ENTITY = "singletonEntity";
	public static final String ROOT = "root";
	public static final String SUCCESS_DELETE_MESSAGE = "successDeleteMessage";
	public static final String RESPONSE_PARAM = "responseParam";
	public static final String ON_DELETE_CONFLICT_MESSAGE = "onDeleteConflictMessage";
	public static final String GENITIVE = "genitive";
	public static final String TEMPLATE_NOTE = "templateNote";
	public static final String GLOBAL_SEARCH_TYPE = "globalSearchType";
	public static final String SHOW_COMMENTS = "showComments";
	public static final String PAGE_FILTER_SIZE_NAME = "pageFilterSizeName";
	public static final String SINGLETON = "singleton";
	public static final String HEADERS = "headers";
	public static final String METHOD = "method";
	public static final String TEMPLATES = "templates";
	public static final String VERSION = "version";
	public static final String URL = "url";
	public static final String DEFAULT_BODY = "defaultBody";
	public static final String SUCCESS_CREATE_MESSAGE = "successCreateMessage";
	public static final String ALLOW_INLINE_CREATION = "allowInlineCreation";
	public static final String ENTITY_DEF_GROUP = "entityDefGroup";
	public static final String NAME = "name";
	public static final String URL_LIST = "urlList";
	public static final String PAGE_FILTER_NUMBER_NAME = "pageFilterNumberName";
	public static final String LOCK_CREATING = "lockCreating";
	public static final String NOMINATIVE = "nominative";
	public static final String STATUS = "status";


	/**
	 * @see EntityDef#allowDnd
	 **/
	public static volatile SingularAttribute<EntityDef, Boolean> allowDnd;

	/**
	 * @see EntityDef#container
	 **/
	public static volatile SingularAttribute<EntityDef, FieldDefContainer> container;

	/**
	 * @see EntityDef#code
	 **/
	public static volatile SingularAttribute<EntityDef, String> code;

	/**
	 * @see EntityDef#modes
	 **/
	public static volatile ListAttribute<EntityDef, EntityDefMode> modes;

	/**
	 * @see EntityDef#responseTotalName
	 **/
	public static volatile SingularAttribute<EntityDef, String> responseTotalName;

	/**
	 * @see EntityDef#pageFilterName
	 **/
	public static volatile SingularAttribute<EntityDef, String> pageFilterName;

	/**
	 * @see EntityDef#providerType
	 **/
	public static volatile SingularAttribute<EntityDef, ProviderType> providerType;

	/**
	 * @see EntityDef#singletonEntity
	 **/
	public static volatile SingularAttribute<EntityDef, Entity> singletonEntity;

	/**
	 * @see EntityDef#root
	 **/
	public static volatile SingularAttribute<EntityDef, Boolean> root;

	/**
	 * @see EntityDef#successDeleteMessage
	 **/
	public static volatile SingularAttribute<EntityDef, String> successDeleteMessage;

	/**
	 * @see EntityDef#responseParam
	 **/
	public static volatile SingularAttribute<EntityDef, String> responseParam;

	/**
	 * @see EntityDef#onDeleteConflictMessage
	 **/
	public static volatile SingularAttribute<EntityDef, String> onDeleteConflictMessage;

	/**
	 * @see EntityDef#genitive
	 **/
	public static volatile SingularAttribute<EntityDef, String> genitive;

	/**
	 * @see EntityDef
	 **/
	public static volatile EntityType<EntityDef> class_;

	/**
	 * @see EntityDef#templateNote
	 **/
	public static volatile SingularAttribute<EntityDef, String> templateNote;

	/**
	 * @see EntityDef#globalSearchType
	 **/
	public static volatile SingularAttribute<EntityDef, GlobalSearchType> globalSearchType;

	/**
	 * @see EntityDef#showComments
	 **/
	public static volatile SingularAttribute<EntityDef, Boolean> showComments;

	/**
	 * @see EntityDef#pageFilterSizeName
	 **/
	public static volatile SingularAttribute<EntityDef, String> pageFilterSizeName;

	/**
	 * @see EntityDef#singleton
	 **/
	public static volatile SingularAttribute<EntityDef, Boolean> singleton;

	/**
	 * @see EntityDef#headers
	 **/
	public static volatile ListAttribute<EntityDef, EntityDefExternalHeader> headers;

	/**
	 * @see EntityDef#method
	 **/
	public static volatile SingularAttribute<EntityDef, DictionaryHttpMethod> method;

	/**
	 * @see EntityDef#templates
	 **/
	public static volatile ListAttribute<EntityDef, EntityTemplate> templates;

	/**
	 * @see EntityDef#version
	 **/
	public static volatile SingularAttribute<EntityDef, Long> version;

	/**
	 * @see EntityDef#url
	 **/
	public static volatile SingularAttribute<EntityDef, String> url;

	/**
	 * @see EntityDef#defaultBody
	 **/
	public static volatile SingularAttribute<EntityDef, Map<String,Object>> defaultBody;

	/**
	 * @see EntityDef#successCreateMessage
	 **/
	public static volatile SingularAttribute<EntityDef, String> successCreateMessage;

	/**
	 * @see EntityDef#allowInlineCreation
	 **/
	public static volatile SingularAttribute<EntityDef, Boolean> allowInlineCreation;

	/**
	 * @see EntityDef#entityDefGroup
	 **/
	public static volatile SingularAttribute<EntityDef, EntityDefGroup> entityDefGroup;

	/**
	 * @see EntityDef#name
	 **/
	public static volatile SingularAttribute<EntityDef, String> name;

	/**
	 * @see EntityDef#urlList
	 **/
	public static volatile SingularAttribute<EntityDef, String> urlList;

	/**
	 * @see EntityDef#pageFilterNumberName
	 **/
	public static volatile SingularAttribute<EntityDef, String> pageFilterNumberName;

	/**
	 * @see EntityDef#lockCreating
	 **/
	public static volatile SingularAttribute<EntityDef, Boolean> lockCreating;

	/**
	 * @see EntityDef#nominative
	 **/
	public static volatile SingularAttribute<EntityDef, String> nominative;

	/**
	 * @see EntityDef#status
	 **/
	public static volatile SingularAttribute<EntityDef, EntityDefStatus> status;

}

