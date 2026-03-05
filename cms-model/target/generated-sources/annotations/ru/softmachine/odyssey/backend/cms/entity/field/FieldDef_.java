package com.aspnt.mddl.entity.field;

import com.aspnt.mddl.entity.field.FieldDef;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.List;
import com.aspnt.mddl.dto.base.FieldType;
import com.aspnt.mddl.dto.base.FieldViewType;
import com.aspnt.mddl.dto.field.FieldDefType;
import com.aspnt.mddl.entity.DictionaryExternal;
import com.aspnt.mddl.entity.Entity;
import com.aspnt.mddl.entity.EntityDefMode;
import com.aspnt.mddl.entity.ExternalConnection;
import com.aspnt.mddl.entity.FieldDefContainer;
import com.aspnt.mddl.entity.FieldValue;
import com.aspnt.mddl.entity.FieldValueTransition;
import com.aspnt.mddl.entity.entitydef.EntityDef;
import com.aspnt.mddl.entity.validation.FieldValidation;

@StaticMetamodel(FieldDef.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class FieldDef_ extends com.aspnt.mddl.entity.base.BaseEntity_ {

	public static final String CONTAINER = "container";
	public static final String FIELD_TRANSITIONS = "fieldTransitions";
	public static final String ALLOW_COLLECTION_RESTRICTION = "allowCollectionRestriction";
	public static final String COMPRESSION_ENABLED = "compressionEnabled";
	public static final String VISIBLE_SHORT = "visibleShort";
	public static final String EXAMPLE_TEXT = "exampleText";
	public static final String PREFIX = "prefix";
	public static final String ORDER_IN_TABLE = "orderInTable";
	public static final String TYPE = "type";
	public static final String SUFFIX = "suffix";
	public static final String FTS_LANGUAGE = "ftsLanguage";
	public static final String FOR_SLUG_GENERATOR = "forSlugGenerator";
	public static final String REQUIRED = "required";
	public static final String MODE = "mode";
	public static final String TABLE_SEQ = "tableSeq";
	public static final String EXTERNAL_FILTER_LOW_BOUNDARY_NAME = "externalFilterLowBoundaryName";
	public static final String DEFAULT_TEXT_VALUE = "defaultTextValue";
	public static final String VISIBLE_FORM = "visibleForm";
	public static final String EXTERNAL_FILTER_UP_BOUNDARY_NAME = "externalFilterUpBoundaryName";
	public static final String COLLECTION_REF = "collectionRef";
	public static final String VISIBLE_LIST_VIEW = "visibleListView";
	public static final String DEFAULT_INT_VALUE = "defaultIntValue";
	public static final String VISIBLE_HEADER = "visibleHeader";
	public static final String USE_SEARCH_FILTER = "useSearchFilter";
	public static final String DICTIONARY_EXTERNAL = "dictionaryExternal";
	public static final String SET_CURRENT_DATE_AS_DEFAULT = "setCurrentDateAsDefault";
	public static final String SERIALIZE_ENUM = "serializeEnum";
	public static final String CAN_CHANGE_ORDER = "canChangeOrder";
	public static final String SERIALIZE_FULL = "serializeFull";
	public static final String FIELD_DEF_TYPE = "fieldDefType";
	public static final String DEFAULT_BOOLEAN_VALUE = "defaultBooleanValue";
	public static final String FTS_PRIORITY = "ftsPriority";
	public static final String ALLOW_COLLECTION_REMOVE = "allowCollectionRemove";
	public static final String RESET_DEPENDENCY_FIELD_CODE = "resetDependencyFieldCode";
	public static final String ALLOWED_TEMPLATES = "allowedTemplates";
	public static final String NAME = "name";
	public static final String VIEW_TYPE = "viewType";
	public static final String EXTERNAL_FILTER_NAME = "externalFilterName";
	public static final String HIDE_ON_CREATE = "hideOnCreate";
	public static final String EXTERNAL_CONNECTION = "externalConnection";
	public static final String NOTE = "note";
	public static final String DISABLE_CONDITION = "disableCondition";
	public static final String CODE = "code";
	public static final String VISIBLE_TABLE = "visibleTable";
	public static final String DEFAULT_DOUBLE_VALUE = "defaultDoubleValue";
	public static final String TABLE_WIDTH = "tableWidth";
	public static final String VALUES = "values";
	public static final String DEFAULT_REF_VALUE = "defaultRefValue";
	public static final String DEFAULT_ORDER = "defaultOrder";
	public static final String COMPRESSION_LIMIT = "compressionLimit";
	public static final String DISABLED = "disabled";
	public static final String PLACEHOLDER = "placeholder";
	public static final String SEQ = "seq";
	public static final String EXTERNAL_CONNECTION_BATCH = "externalConnectionBatch";
	public static final String LABEL_INSIDE = "labelInside";
	public static final String CREATE_DEFAULT = "createDefault";
	public static final String VISIBLE_CONDITION = "visibleCondition";
	public static final String EXPRESSION = "expression";
	public static final String MULTIPLE = "multiple";
	public static final String PREDEFINED_SELECTIONS = "predefinedSelections";
	public static final String USE_FILTER = "useFilter";
	public static final String COMPACT_TABLE_VIEW = "compactTableView";
	public static final String FIELD_VALIDATIONS = "fieldValidations";
	public static final String VISIBLE_ITEM_CONDITION = "visibleItemCondition";
	public static final String REF_COLLECTION_FIELD_CODE = "refCollectionFieldCode";
	public static final String VISIBLE_TEMPLATE = "visibleTemplate";
	public static final String ALLOW_DUPLICATES = "allowDuplicates";
	public static final String VISIBLE_VIEW = "visibleView";
	public static final String DEFAULT_REF_FILTER_VALUES = "defaultRefFilterValues";
	public static final String SPAN = "span";


	/**
	 * @see FieldDef#container
	 **/
	public static volatile SingularAttribute<FieldDef, FieldDefContainer> container;

	/**
	 * @see FieldDef#fieldTransitions
	 **/
	public static volatile ListAttribute<FieldDef, FieldValueTransition> fieldTransitions;

	/**
	 * @see FieldDef#allowCollectionRestriction
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> allowCollectionRestriction;

	/**
	 * @see FieldDef#compressionEnabled
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> compressionEnabled;

	/**
	 * @see FieldDef#visibleShort
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleShort;

	/**
	 * @see FieldDef#exampleText
	 **/
	public static volatile SingularAttribute<FieldDef, String> exampleText;

	/**
	 * @see FieldDef#prefix
	 **/
	public static volatile SingularAttribute<FieldDef, String> prefix;

	/**
	 * @see FieldDef#orderInTable
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> orderInTable;

	/**
	 * @see FieldDef#type
	 **/
	public static volatile SingularAttribute<FieldDef, FieldType> type;

	/**
	 * @see FieldDef#suffix
	 **/
	public static volatile SingularAttribute<FieldDef, String> suffix;

	/**
	 * @see FieldDef#ftsLanguage
	 **/
	public static volatile SingularAttribute<FieldDef, String> ftsLanguage;

	/**
	 * @see FieldDef#forSlugGenerator
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> forSlugGenerator;

	/**
	 * @see FieldDef#required
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> required;

	/**
	 * @see FieldDef#mode
	 **/
	public static volatile SingularAttribute<FieldDef, EntityDefMode> mode;

	/**
	 * @see FieldDef#tableSeq
	 **/
	public static volatile SingularAttribute<FieldDef, Integer> tableSeq;

	/**
	 * @see FieldDef#externalFilterLowBoundaryName
	 **/
	public static volatile SingularAttribute<FieldDef, String> externalFilterLowBoundaryName;

	/**
	 * @see FieldDef#defaultTextValue
	 **/
	public static volatile SingularAttribute<FieldDef, String> defaultTextValue;

	/**
	 * @see FieldDef#visibleForm
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleForm;

	/**
	 * @see FieldDef#externalFilterUpBoundaryName
	 **/
	public static volatile SingularAttribute<FieldDef, String> externalFilterUpBoundaryName;

	/**
	 * @see FieldDef#collectionRef
	 **/
	public static volatile SingularAttribute<FieldDef, EntityDef> collectionRef;

	/**
	 * @see FieldDef#visibleListView
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleListView;

	/**
	 * @see FieldDef#defaultIntValue
	 **/
	public static volatile SingularAttribute<FieldDef, Long> defaultIntValue;

	/**
	 * @see FieldDef#visibleHeader
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleHeader;

	/**
	 * @see FieldDef#useSearchFilter
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> useSearchFilter;

	/**
	 * @see FieldDef#dictionaryExternal
	 **/
	public static volatile SingularAttribute<FieldDef, DictionaryExternal> dictionaryExternal;

	/**
	 * @see FieldDef#setCurrentDateAsDefault
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> setCurrentDateAsDefault;

	/**
	 * @see FieldDef#serializeEnum
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> serializeEnum;

	/**
	 * @see FieldDef#canChangeOrder
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> canChangeOrder;

	/**
	 * @see FieldDef#serializeFull
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> serializeFull;

	/**
	 * @see FieldDef#fieldDefType
	 **/
	public static volatile SingularAttribute<FieldDef, FieldDefType> fieldDefType;

	/**
	 * @see FieldDef#defaultBooleanValue
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> defaultBooleanValue;

	/**
	 * @see FieldDef#ftsPriority
	 **/
	public static volatile SingularAttribute<FieldDef, Character> ftsPriority;

	/**
	 * @see FieldDef#allowCollectionRemove
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> allowCollectionRemove;

	/**
	 * @see FieldDef#resetDependencyFieldCode
	 **/
	public static volatile SingularAttribute<FieldDef, String> resetDependencyFieldCode;

	/**
	 * @see FieldDef#allowedTemplates
	 **/
	public static volatile SingularAttribute<FieldDef, List<String>> allowedTemplates;

	/**
	 * @see FieldDef#name
	 **/
	public static volatile SingularAttribute<FieldDef, String> name;

	/**
	 * @see FieldDef#viewType
	 **/
	public static volatile SingularAttribute<FieldDef, FieldViewType> viewType;

	/**
	 * @see FieldDef#externalFilterName
	 **/
	public static volatile SingularAttribute<FieldDef, String> externalFilterName;

	/**
	 * @see FieldDef#hideOnCreate
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> hideOnCreate;

	/**
	 * @see FieldDef#externalConnection
	 **/
	public static volatile SingularAttribute<FieldDef, ExternalConnection> externalConnection;

	/**
	 * @see FieldDef#note
	 **/
	public static volatile SingularAttribute<FieldDef, String> note;

	/**
	 * @see FieldDef#disableCondition
	 **/
	public static volatile SingularAttribute<FieldDef, String> disableCondition;

	/**
	 * @see FieldDef#code
	 **/
	public static volatile SingularAttribute<FieldDef, String> code;

	/**
	 * @see FieldDef#visibleTable
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleTable;

	/**
	 * @see FieldDef#defaultDoubleValue
	 **/
	public static volatile SingularAttribute<FieldDef, Double> defaultDoubleValue;

	/**
	 * @see FieldDef#tableWidth
	 **/
	public static volatile SingularAttribute<FieldDef, String> tableWidth;

	/**
	 * @see FieldDef#values
	 **/
	public static volatile ListAttribute<FieldDef, FieldValue> values;

	/**
	 * @see FieldDef#defaultRefValue
	 **/
	public static volatile SingularAttribute<FieldDef, Entity> defaultRefValue;

	/**
	 * @see FieldDef#defaultOrder
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> defaultOrder;

	/**
	 * @see FieldDef#compressionLimit
	 **/
	public static volatile SingularAttribute<FieldDef, Integer> compressionLimit;

	/**
	 * @see FieldDef#disabled
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> disabled;

	/**
	 * @see FieldDef#placeholder
	 **/
	public static volatile SingularAttribute<FieldDef, String> placeholder;

	/**
	 * @see FieldDef
	 **/
	public static volatile EntityType<FieldDef> class_;

	/**
	 * @see FieldDef#seq
	 **/
	public static volatile SingularAttribute<FieldDef, Integer> seq;

	/**
	 * @see FieldDef#externalConnectionBatch
	 **/
	public static volatile SingularAttribute<FieldDef, ExternalConnection> externalConnectionBatch;

	/**
	 * @see FieldDef#labelInside
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> labelInside;

	/**
	 * @see FieldDef#createDefault
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> createDefault;

	/**
	 * @see FieldDef#visibleCondition
	 **/
	public static volatile SingularAttribute<FieldDef, String> visibleCondition;

	/**
	 * @see FieldDef#expression
	 **/
	public static volatile SingularAttribute<FieldDef, String> expression;

	/**
	 * @see FieldDef#multiple
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> multiple;

	/**
	 * @see FieldDef#predefinedSelections
	 **/
	public static volatile SingularAttribute<FieldDef, List<String>> predefinedSelections;

	/**
	 * @see FieldDef#useFilter
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> useFilter;

	/**
	 * @see FieldDef#compactTableView
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> compactTableView;

	/**
	 * @see FieldDef#fieldValidations
	 **/
	public static volatile ListAttribute<FieldDef, FieldValidation> fieldValidations;

	/**
	 * @see FieldDef#visibleItemCondition
	 **/
	public static volatile SingularAttribute<FieldDef, String> visibleItemCondition;

	/**
	 * @see FieldDef#refCollectionFieldCode
	 **/
	public static volatile SingularAttribute<FieldDef, String> refCollectionFieldCode;

	/**
	 * @see FieldDef#visibleTemplate
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleTemplate;

	/**
	 * @see FieldDef#allowDuplicates
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> allowDuplicates;

	/**
	 * @see FieldDef#visibleView
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleView;

	/**
	 * @see FieldDef#defaultRefFilterValues
	 **/
	public static volatile SingularAttribute<FieldDef, List<String>> defaultRefFilterValues;

	/**
	 * @see FieldDef#span
	 **/
	public static volatile SingularAttribute<FieldDef, Integer> span;

}

