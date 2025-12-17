package ru.softmachine.odyssey.backend.cms.entity.entitydef;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Map;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryHttpMethod;
import ru.softmachine.odyssey.backend.cms.dto.EntityDefStatus;
import ru.softmachine.odyssey.backend.cms.dto.GlobalSearchType;
import ru.softmachine.odyssey.backend.cms.dto.provider.ProviderType;
import ru.softmachine.odyssey.backend.cms.entity.Entity;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefExternalHeader;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefGroup;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefMode;
import ru.softmachine.odyssey.backend.cms.entity.EntityTemplate;
import ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer;

@StaticMetamodel(EntityDef.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class EntityDef_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

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
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#allowDnd
	 **/
	public static volatile SingularAttribute<EntityDef, Boolean> allowDnd;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#container
	 **/
	public static volatile SingularAttribute<EntityDef, FieldDefContainer> container;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#code
	 **/
	public static volatile SingularAttribute<EntityDef, String> code;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#modes
	 **/
	public static volatile ListAttribute<EntityDef, EntityDefMode> modes;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#responseTotalName
	 **/
	public static volatile SingularAttribute<EntityDef, String> responseTotalName;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#pageFilterName
	 **/
	public static volatile SingularAttribute<EntityDef, String> pageFilterName;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#providerType
	 **/
	public static volatile SingularAttribute<EntityDef, ProviderType> providerType;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#singletonEntity
	 **/
	public static volatile SingularAttribute<EntityDef, Entity> singletonEntity;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#root
	 **/
	public static volatile SingularAttribute<EntityDef, Boolean> root;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#successDeleteMessage
	 **/
	public static volatile SingularAttribute<EntityDef, String> successDeleteMessage;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#responseParam
	 **/
	public static volatile SingularAttribute<EntityDef, String> responseParam;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#onDeleteConflictMessage
	 **/
	public static volatile SingularAttribute<EntityDef, String> onDeleteConflictMessage;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#genitive
	 **/
	public static volatile SingularAttribute<EntityDef, String> genitive;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef
	 **/
	public static volatile EntityType<EntityDef> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#templateNote
	 **/
	public static volatile SingularAttribute<EntityDef, String> templateNote;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#globalSearchType
	 **/
	public static volatile SingularAttribute<EntityDef, GlobalSearchType> globalSearchType;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#showComments
	 **/
	public static volatile SingularAttribute<EntityDef, Boolean> showComments;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#pageFilterSizeName
	 **/
	public static volatile SingularAttribute<EntityDef, String> pageFilterSizeName;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#singleton
	 **/
	public static volatile SingularAttribute<EntityDef, Boolean> singleton;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#headers
	 **/
	public static volatile ListAttribute<EntityDef, EntityDefExternalHeader> headers;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#method
	 **/
	public static volatile SingularAttribute<EntityDef, DictionaryHttpMethod> method;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#templates
	 **/
	public static volatile ListAttribute<EntityDef, EntityTemplate> templates;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#version
	 **/
	public static volatile SingularAttribute<EntityDef, Long> version;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#url
	 **/
	public static volatile SingularAttribute<EntityDef, String> url;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#defaultBody
	 **/
	public static volatile SingularAttribute<EntityDef, Map<String,Object>> defaultBody;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#successCreateMessage
	 **/
	public static volatile SingularAttribute<EntityDef, String> successCreateMessage;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#allowInlineCreation
	 **/
	public static volatile SingularAttribute<EntityDef, Boolean> allowInlineCreation;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#entityDefGroup
	 **/
	public static volatile SingularAttribute<EntityDef, EntityDefGroup> entityDefGroup;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#name
	 **/
	public static volatile SingularAttribute<EntityDef, String> name;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#urlList
	 **/
	public static volatile SingularAttribute<EntityDef, String> urlList;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#pageFilterNumberName
	 **/
	public static volatile SingularAttribute<EntityDef, String> pageFilterNumberName;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#lockCreating
	 **/
	public static volatile SingularAttribute<EntityDef, Boolean> lockCreating;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#nominative
	 **/
	public static volatile SingularAttribute<EntityDef, String> nominative;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef#status
	 **/
	public static volatile SingularAttribute<EntityDef, EntityDefStatus> status;

}

