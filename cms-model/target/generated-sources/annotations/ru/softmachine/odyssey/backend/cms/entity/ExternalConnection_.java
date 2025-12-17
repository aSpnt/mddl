package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.List;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryHttpMethod;

@StaticMetamodel(ExternalConnection.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class ExternalConnection_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

	public static final String METHOD = "method";
	public static final String PARAM = "param";
	public static final String RESPONSE_PARAM = "responseParam";
	public static final String URL = "url";

	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.ExternalConnection#method
	 **/
	public static volatile SingularAttribute<ExternalConnection, DictionaryHttpMethod> method;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.ExternalConnection#param
	 **/
	public static volatile SingularAttribute<ExternalConnection, List<String>> param;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.ExternalConnection#responseParam
	 **/
	public static volatile SingularAttribute<ExternalConnection, List<String>> responseParam;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.ExternalConnection
	 **/
	public static volatile EntityType<ExternalConnection> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.ExternalConnection#url
	 **/
	public static volatile SingularAttribute<ExternalConnection, String> url;

}

