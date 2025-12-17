package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.List;
import java.util.Map;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryHttpMethod;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

@StaticMetamodel(DictionaryExternal.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class DictionaryExternal_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

	public static final String HEADERS = "headers";
	public static final String CODE = "code";
	public static final String METHOD = "method";
	public static final String IS_SPEL = "isSpel";
	public static final String IMG_EXPRESSION = "imgExpression";
	public static final String NAME_PARAM = "nameParam";
	public static final String URL = "url";
	public static final String DESCRIPTION_PARAM = "descriptionParam";
	public static final String DESCRIPTION_EXPRESSION = "descriptionExpression";
	public static final String DEFAULT_BODY = "defaultBody";
	public static final String IMG_PARAM = "imgParam";
	public static final String REF_EXPRESSION = "refExpression";
	public static final String PARAM = "param";
	public static final String NAME = "name";
	public static final String RESPONSE_PARAM = "responseParam";
	public static final String ENTITY_DEF = "entityDef";
	public static final String ID_PARAM = "idParam";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#headers
	 **/
	public static volatile ListAttribute<DictionaryExternal, DictionaryExternalHeader> headers;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#code
	 **/
	public static volatile SingularAttribute<DictionaryExternal, String> code;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#method
	 **/
	public static volatile SingularAttribute<DictionaryExternal, DictionaryHttpMethod> method;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#isSpel
	 **/
	public static volatile SingularAttribute<DictionaryExternal, Boolean> isSpel;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#imgExpression
	 **/
	public static volatile SingularAttribute<DictionaryExternal, String> imgExpression;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#nameParam
	 **/
	public static volatile SingularAttribute<DictionaryExternal, String> nameParam;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#url
	 **/
	public static volatile SingularAttribute<DictionaryExternal, String> url;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#descriptionParam
	 **/
	public static volatile SingularAttribute<DictionaryExternal, String> descriptionParam;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#descriptionExpression
	 **/
	public static volatile SingularAttribute<DictionaryExternal, String> descriptionExpression;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#defaultBody
	 **/
	public static volatile SingularAttribute<DictionaryExternal, Map<String,Object>> defaultBody;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#imgParam
	 **/
	public static volatile SingularAttribute<DictionaryExternal, String> imgParam;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#refExpression
	 **/
	public static volatile SingularAttribute<DictionaryExternal, String> refExpression;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#param
	 **/
	public static volatile SingularAttribute<DictionaryExternal, List<String>> param;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#name
	 **/
	public static volatile SingularAttribute<DictionaryExternal, String> name;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#responseParam
	 **/
	public static volatile SingularAttribute<DictionaryExternal, String> responseParam;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#entityDef
	 **/
	public static volatile SingularAttribute<DictionaryExternal, EntityDef> entityDef;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal#idParam
	 **/
	public static volatile SingularAttribute<DictionaryExternal, String> idParam;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal
	 **/
	public static volatile EntityType<DictionaryExternal> class_;

}

