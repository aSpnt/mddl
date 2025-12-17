package ru.softmachine.odyssey.backend.cms.entity.field;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.List;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldType;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldViewType;
import ru.softmachine.odyssey.backend.cms.dto.field.FieldDefType;
import ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal;
import ru.softmachine.odyssey.backend.cms.entity.Entity;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefMode;
import ru.softmachine.odyssey.backend.cms.entity.ExternalConnection;
import ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer;
import ru.softmachine.odyssey.backend.cms.entity.FieldValue;
import ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;
import ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation;

@StaticMetamodel(FieldDef.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class FieldDef_ extends ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_ {

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
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#container
	 **/
	public static volatile SingularAttribute<FieldDef, FieldDefContainer> container;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#fieldTransitions
	 **/
	public static volatile ListAttribute<FieldDef, FieldValueTransition> fieldTransitions;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#allowCollectionRestriction
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> allowCollectionRestriction;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#compressionEnabled
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> compressionEnabled;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#visibleShort
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleShort;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#exampleText
	 **/
	public static volatile SingularAttribute<FieldDef, String> exampleText;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#prefix
	 **/
	public static volatile SingularAttribute<FieldDef, String> prefix;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#orderInTable
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> orderInTable;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#type
	 **/
	public static volatile SingularAttribute<FieldDef, FieldType> type;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#suffix
	 **/
	public static volatile SingularAttribute<FieldDef, String> suffix;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#ftsLanguage
	 **/
	public static volatile SingularAttribute<FieldDef, String> ftsLanguage;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#forSlugGenerator
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> forSlugGenerator;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#required
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> required;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#mode
	 **/
	public static volatile SingularAttribute<FieldDef, EntityDefMode> mode;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#tableSeq
	 **/
	public static volatile SingularAttribute<FieldDef, Integer> tableSeq;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#externalFilterLowBoundaryName
	 **/
	public static volatile SingularAttribute<FieldDef, String> externalFilterLowBoundaryName;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#defaultTextValue
	 **/
	public static volatile SingularAttribute<FieldDef, String> defaultTextValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#visibleForm
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleForm;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#externalFilterUpBoundaryName
	 **/
	public static volatile SingularAttribute<FieldDef, String> externalFilterUpBoundaryName;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#collectionRef
	 **/
	public static volatile SingularAttribute<FieldDef, EntityDef> collectionRef;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#visibleListView
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleListView;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#defaultIntValue
	 **/
	public static volatile SingularAttribute<FieldDef, Long> defaultIntValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#visibleHeader
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleHeader;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#useSearchFilter
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> useSearchFilter;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#dictionaryExternal
	 **/
	public static volatile SingularAttribute<FieldDef, DictionaryExternal> dictionaryExternal;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#setCurrentDateAsDefault
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> setCurrentDateAsDefault;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#serializeEnum
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> serializeEnum;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#canChangeOrder
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> canChangeOrder;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#serializeFull
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> serializeFull;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#fieldDefType
	 **/
	public static volatile SingularAttribute<FieldDef, FieldDefType> fieldDefType;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#defaultBooleanValue
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> defaultBooleanValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#ftsPriority
	 **/
	public static volatile SingularAttribute<FieldDef, Character> ftsPriority;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#allowCollectionRemove
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> allowCollectionRemove;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#resetDependencyFieldCode
	 **/
	public static volatile SingularAttribute<FieldDef, String> resetDependencyFieldCode;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#allowedTemplates
	 **/
	public static volatile SingularAttribute<FieldDef, List<String>> allowedTemplates;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#name
	 **/
	public static volatile SingularAttribute<FieldDef, String> name;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#viewType
	 **/
	public static volatile SingularAttribute<FieldDef, FieldViewType> viewType;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#externalFilterName
	 **/
	public static volatile SingularAttribute<FieldDef, String> externalFilterName;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#hideOnCreate
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> hideOnCreate;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#externalConnection
	 **/
	public static volatile SingularAttribute<FieldDef, ExternalConnection> externalConnection;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#note
	 **/
	public static volatile SingularAttribute<FieldDef, String> note;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#disableCondition
	 **/
	public static volatile SingularAttribute<FieldDef, String> disableCondition;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#code
	 **/
	public static volatile SingularAttribute<FieldDef, String> code;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#visibleTable
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleTable;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#defaultDoubleValue
	 **/
	public static volatile SingularAttribute<FieldDef, Double> defaultDoubleValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#tableWidth
	 **/
	public static volatile SingularAttribute<FieldDef, String> tableWidth;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#values
	 **/
	public static volatile ListAttribute<FieldDef, FieldValue> values;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#defaultRefValue
	 **/
	public static volatile SingularAttribute<FieldDef, Entity> defaultRefValue;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#defaultOrder
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> defaultOrder;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#compressionLimit
	 **/
	public static volatile SingularAttribute<FieldDef, Integer> compressionLimit;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#disabled
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> disabled;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#placeholder
	 **/
	public static volatile SingularAttribute<FieldDef, String> placeholder;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef
	 **/
	public static volatile EntityType<FieldDef> class_;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#seq
	 **/
	public static volatile SingularAttribute<FieldDef, Integer> seq;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#externalConnectionBatch
	 **/
	public static volatile SingularAttribute<FieldDef, ExternalConnection> externalConnectionBatch;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#labelInside
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> labelInside;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#createDefault
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> createDefault;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#visibleCondition
	 **/
	public static volatile SingularAttribute<FieldDef, String> visibleCondition;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#expression
	 **/
	public static volatile SingularAttribute<FieldDef, String> expression;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#multiple
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> multiple;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#predefinedSelections
	 **/
	public static volatile SingularAttribute<FieldDef, List<String>> predefinedSelections;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#useFilter
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> useFilter;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#compactTableView
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> compactTableView;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#fieldValidations
	 **/
	public static volatile ListAttribute<FieldDef, FieldValidation> fieldValidations;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#visibleItemCondition
	 **/
	public static volatile SingularAttribute<FieldDef, String> visibleItemCondition;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#refCollectionFieldCode
	 **/
	public static volatile SingularAttribute<FieldDef, String> refCollectionFieldCode;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#visibleTemplate
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleTemplate;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#allowDuplicates
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> allowDuplicates;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#visibleView
	 **/
	public static volatile SingularAttribute<FieldDef, Boolean> visibleView;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#defaultRefFilterValues
	 **/
	public static volatile SingularAttribute<FieldDef, List<String>> defaultRefFilterValues;
	
	/**
	 * @see ru.softmachine.odyssey.backend.cms.entity.field.FieldDef#span
	 **/
	public static volatile SingularAttribute<FieldDef, Integer> span;

}

