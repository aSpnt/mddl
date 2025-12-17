package ru.softmachine.odyssey.backend.cms.entity.field;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.field.FieldDefType;
import ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefMode;
import ru.softmachine.odyssey.backend.cms.entity.ExternalConnection;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldType;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldViewType;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;
import ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer;
import ru.softmachine.odyssey.backend.cms.entity.FieldValue;
import ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition;
import ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "field_def", schema = "meta")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FieldDef extends BaseEntity {

    @Column(name = "field_def_type")
    @Enumerated(EnumType.STRING)
    private FieldDefType fieldDefType = FieldDefType.FLEX;

    /**
     * Наименование поля
     */
    private String name;

    /**
     * Код поля, является ключом для значения
     * при серилизации (например в JSON)
     */
    private String code;

    /**
     * Тип поля (определяет контрол для значения,
     * способ хранения данных и др)
     */
    @Enumerated(EnumType.STRING)
    private FieldType type;

    /**
     * Ссылка на режим дефиниции (предполагается то поле активно только в определенном режиме)
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity_def_mode_id")
    private EntityDefMode mode;

    /**
     * Подсказка для toolbox дял поля
     */
    private String note;

    /**
     * Подсказка
     */
    private String placeholder;

    /**
     * При рендеринге контроля дя введения
     * значения отображается до значения поля
     */
    private String suffix;

    /**
     * При рендеринге контроля дя введения
     * значения отображается после значения поля
     */
    private String prefix;

    /**
     * Условие (js) для видимости поля на
     * форме заведения данных
     */
    @Column(name = "visible_condition")
    private String visibleCondition;

    /**
     * Условие (js) для видимости элемента
     * коллекции
     */
    @Column(name = "visible_item_condition")
    private String visibleItemCondition;

    /**
     * Условие (js) для видимости поля на
     * форме заведения данных
     */
    @Column(name = "disable_condition")
    private String disableCondition;

    /**
     * Поле при изменении которого значение нужно сбрасывать (вероятно нужна коллекция)
     */
    @Column(name = "reset_dependency_field_code")
    private String resetDependencyFieldCode;

    /**
     * Ссылка на код коллекции для фильтрации
     */
    @Column(name = "ref_collection_field_code")
    private String refCollectionFieldCode;

    /**
     * Разрешенные для добавления коды шаблонов
     */
    @Column(name = "allowed_templates")
    private List<String> allowedTemplates;

    /**
     * Порядковый номер поля на форме
     * заведения данных
     */
    private Integer seq;

    /**
     * Порядок колонки
     */
    @Column(name = "table_seq")
    private Integer tableSeq;

    /**
     * Помечает множественность вложенных типов
     */
    @Column(name = "multiple")
    private boolean multiple;

    /**
     * Разрешает добавление дублирующихся значений для внешних справочников
     */
    @Column(name = "allow_duplicates")
    private boolean allowDuplicates;

    /**
     * Для видимых, но не редактируемых полей
     */
    @Column(name = "disabled")
    private boolean disabled;

    /**
     * Включает сортировку в таблице для поля
     */
    @Column(name = "order_in_table")
    private boolean orderInTable;

    /**
     * Признак сортировки по-умолчанию
     */
    @Column(name = "default_order")
    private boolean defaultOrder;

    /**
     * Предписывает применять компактное отображение в таблице
     */
    @Column(name = "compact_table_view")
    private boolean compactTableView;

    /**
     * Параметр для указания ширины колонки
     * (не привязан к реализации, может быть любым)
     */
    @Column(name = "table_width")
    private String tableWidth;

    /**
     * Уточняет предпочтительный тип отображения
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "view_type")
    private FieldViewType viewType;

    /**
     * Помечает поля для использования в
     * кратких проекциях (например для выпадающего списка и др)
     */
    @Column(name = "visible_short")
    private boolean visibleShort;

    /**
     * Флаг отображения колонки с полем при
     * табличном способе отображения
     */
    @Column(name = "visible_table")
    private boolean visibleTable;

    /**
     * Флаг отображения колонки с полем при
     * просмотре формы
     */
    @Column(name = "visible_view")
    private boolean visibleView;

    /**
     * Отображение на форме редактирования и
     * заведения сузности
     */
    @Column(name = "visible_form")
    private boolean visibleForm;

    /**
     * Флаг указывает на необходимость передавать поле при
     * запросе сущности в рамках списка
     */
    @Column(name = "visible_list_view")
    private boolean visibleListView;

    /**
     * Видимость в заголовке
     */
    @Column(name = "visible_header")
    private boolean visibleHeader;

    /**
     * Видимость при создании и редактировании шаблона
     */
    @Column(name = "visible_template")
    private boolean visibleTemplate;

    /**
     * Признак скрытия на форме создания
     */
    @Column(name = "hide_on_create")
    private boolean hideOnCreate;

    /**
     * Предписывает создавать фильтр в табличном
     * представлении для текущего поля
     */
    @Column(name = "use_filter")
    private boolean useFilter;

    /**
     * Наименование фильтра при построении внешнего запроса
     */
    @Column(name = "external_filter_name")
    private String externalFilterName;

    /**
     * Наименование фильтра нижней границы диапазона
     * при построении внешнего запроса
     */
    @Column(name = "external_filter_low_boundary_name")
    private String externalFilterLowBoundaryName;

    /**
     * Наименование фильтра верхней границы диапазона
     * при построении внешнего запроса
     */
    @Column(name = "external_filter_up_boundary_name")
    private String externalFilterUpBoundaryName;

    /**
     * Предписывает использовать значения поля для
     * текстового поиска
     */
    @Column(name = "use_search_filter")
    private boolean useSearchFilter;

    /**
     * Указывает способ отображения подсказки для
     * контрола поля на формем
     */
    @Column(name = "label_inside")
    private boolean labelInside;

    /**
     * Указывает на ширину контрола при отоюражении
     * в рамках формы
     */
    private Integer span;

    /**
     * Флаг указывает на поле, являющееся значением по которому серилизуется
     * (и десерилизуется значение справочника)
     */
    @Column(name = "serialize_enum")
    private Boolean serializeEnum;

    /**
     * Флаг указывает серилизацию полного представления вне зависимости
     * он настроек serializeEnum
     */
    @Column(name = "serialize_full")
    private Boolean serializeFull;

    /**
     * Разрешает устанавливать ограничения на состав коллекции в шаблонах
     */
    @Column(name = "allow_collection_restriction")
    private boolean allowCollectionRestriction;

    /**
     * Разрешает удалять элементы коллекции
     */
    @Column(name = "allow_collection_remove")
    private boolean allowCollectionRemove;

    @Column(name = "default_text_value")
    private String defaultTextValue;

    @Column(name = "example_text")
    private String exampleText;

    @Column(name = "default_boolean_value")
    private Boolean defaultBooleanValue;

    @Column(name = "default_double_value")
    private Double defaultDoubleValue;

    @Column(name = "default_int_value")
    private Long defaultIntValue;

    @Column(name = "set_current_date_as_default")
    private Boolean setCurrentDateAsDefault;

    @Column(name = "default_ref_filter_values")
    private List<String> defaultRefFilterValues;

    @Column(name = "predefined_selections")
    private List<String> predefinedSelections;

    /**
     * Параметры запроса к внешнему значению
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "external_connection_id")
    private ExternalConnection externalConnection;

    /**
     * Параметры пакетного запроса к внешнему значению
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "external_connection_batch_id")
    private ExternalConnection externalConnectionBatch;

    /**
     * Помечает поле для генератора слагов
     */
    @Column(name = "for_slug_generator")
    private boolean forSlugGenerator;

    /**
     * SPEL-Выражение для расчета значения на
     * основании других значений сущности
     */
    @Column(name = "expression")
    private String expression;

    /**
     * Для полей с типом коллекция, содержат ссылку на
     * Entity Definition сложенных сущностей
     */
    @ManyToOne
    @JoinColumn(name = "collection_ref")
    private EntityDef collectionRef;

    /**
     * Для полей с типом внешний справочник, содержат ссылку на
     * External Dictionary
     */
    @ManyToOne
    @JoinColumn(name = "dictionary_external_ref")
    private DictionaryExternal dictionaryExternal;

    /**
     * ДЛя коллекций указывает на наличие контролов для
     * управления порядком (можно по-умолчанию включать всегда)
     */
    @Column(name = "can_change_order")
    private boolean canChangeOrder;

    /**
     * Указывает на обязательность поля (возможно будет
     * перенесено в валидацию, но так удобнее)
     */
    @Column(name = "required")
    private boolean required;

    /**
     * Указывает на необходимость создания
     * элемента в коллекции при необходимости
     */
    @Column(name = "create_default")
    private boolean createDefault;

    /**
     * Приоритет поля при индексации FTS (A, B, C, D) по-умолчанию B
     */
    @Column(name = "fts_priority")
    private Character ftsPriority;

    /**
     * Regclass при FTS (по умолчанию russian)
     */
    @Column(name = "fts_language")
    private String ftsLanguage;

    /**
     * Включено ли сжатие для этого поля
     */
    @Column(name = "compression_enabled")
    private boolean compressionEnabled;

    /**
     * Лимит размера файла (в КБ), после которого применяется сжатие
     * для видео и изображений
     */
    @Column(name = "compression_limit")
    private Integer compressionLimit;

    /**
     * Значение внутреннего справочника по-умолчанию
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_ref_value")
    private ru.softmachine.odyssey.backend.cms.entity.Entity defaultRefValue;

    /**
     * Контейнер частью которого является поле
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_id")
    private FieldDefContainer container;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "fieldDef", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<FieldValue> values = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "fieldDef", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FieldValidation> fieldValidations = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "fieldDef", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FieldValueTransition> fieldTransitions = new ArrayList<>();

    public void setFieldValidations(List<FieldValidation> fieldValidations) {
        this.fieldValidations.clear();
        if (fieldValidations != null) {
            this.fieldValidations.addAll(fieldValidations);
        }
    }

    public void setFieldTransitions(List<FieldValueTransition> fieldTransitions) {
        this.fieldTransitions.clear();
        if (fieldTransitions != null) {
            this.fieldTransitions.addAll(fieldTransitions);
        }
    }
}
