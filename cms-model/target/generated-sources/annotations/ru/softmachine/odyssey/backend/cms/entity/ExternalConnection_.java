package com.aspnt.mddl.entity;

import com.aspnt.mddl.entity.ExternalConnection;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.List;
import com.aspnt.mddl.dto.DictionaryHttpMethod;

@StaticMetamodel(ExternalConnection.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class ExternalConnection_ extends com.aspnt.mddl.entity.base.BaseEntity_ {

	public static final String METHOD = "method";
	public static final String PARAM = "param";
	public static final String RESPONSE_PARAM = "responseParam";
	public static final String URL = "url";


	/**
	 * @see ExternalConnection#method
	 **/
	public static volatile SingularAttribute<ExternalConnection, DictionaryHttpMethod> method;

	/**
	 * @see ExternalConnection#param
	 **/
	public static volatile SingularAttribute<ExternalConnection, List<String>> param;

	/**
	 * @see ExternalConnection#responseParam
	 **/
	public static volatile SingularAttribute<ExternalConnection, List<String>> responseParam;

	/**
	 * @see ExternalConnection
	 **/
	public static volatile EntityType<ExternalConnection> class_;

	/**
	 * @see ExternalConnection#url
	 **/
	public static volatile SingularAttribute<ExternalConnection, String> url;

}

