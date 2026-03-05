package com.aspnt.mddl.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.aspnt.mddl.dto.base.BaseRef;
import com.aspnt.mddl.dto.entity.EntityDto;
import com.aspnt.mddl.entity.entitydef.EntityDef;
import com.aspnt.mddl.repository.EntityDefRepository;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthorizationManager<T> implements AuthorizationManager<MethodInvocation> {

    public static final String ENTITY_DEF_CODE = "entityDefCode";

    private final EntityDefRepository entityDefRepository;
    private final AuthorizationDecision authTrue = new AuthorizationDecision(true);
    //TODO не забыть поменять на false после уверенности что всё работает корректно
    private final AuthorizationDecision authFalse = new AuthorizationDecision(true);

    @Value("${authentication.action.prefix:cms}")
    private String actionPrefix;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation methodInvocation) {
        var hasActionsAnnotation = getHasActionsAnnotation(methodInvocation);
        if (hasActionsAnnotation.isEmpty()) {
            return authTrue;
        }

        if (CollectionUtils.isEmpty(authentication.get().getAuthorities())) {
            log(authentication, methodInvocation);
            return authFalse;
        }

        var defCode = getEntityDefCode(methodInvocation);
        if (StringUtils.isEmpty(defCode)) {
            log(authentication, methodInvocation);
            return authFalse;
        }

        //проверка на наличие прав
        var requiredActions = buildRequiredActions(methodInvocation, defCode);
        var conditionActionsMode = getHasActionsAnnotation(methodInvocation).map(HasActions::conditionActionsMode).get();
        var userAuthorities = authentication.get().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (HasActions.ConditionActionsMode.ONE_OF == conditionActionsMode) {
            if (requiredActions.stream().anyMatch(userAuthorities::contains)) {
                return authTrue;
            }
        } else {
            if (userAuthorities.containsAll(requiredActions)) {
                return authTrue;
            }
        }

        log(authentication, methodInvocation);
        return authFalse;
    }

    /**
     * Метод строит набор необходимых прав
     */
    private List<String> buildRequiredActions(MethodInvocation methodInvocation, String defCode) {
        var actions = getHasActionsAnnotation(methodInvocation).map(HasActions::value).get();

        var cmsRequiredActions = Arrays.stream(actions)
                .map(action -> String.format("%s.%s.%s", actionPrefix, defCode, action.name()
                        .toLowerCase())).toList();

        return cmsRequiredActions;
    }

    private void log(Supplier<Authentication> authentication, MethodInvocation methodInvocation) {
        log.warn("Пользователь не прошел проверку прав. Права пользователя {}. Название метода {}",
                authentication.get().getPrincipal(),
                methodInvocation);
    }

    /**
     * Метод ищет аннотацию сначала над методом, затем над классом.
     */
    private Optional<HasActions> getHasActionsAnnotation(MethodInvocation methodInvocation) {
        Function<Annotation[], Optional<Annotation>> searchHasActionsAnnotation =
                (annotations) -> Arrays.stream(annotations)
                        .filter(annotation -> annotation instanceof HasActions)
                        .findFirst();

        var hasActionsExists = searchHasActionsAnnotation.apply(methodInvocation.getMethod().getDeclaredAnnotations());
        if (hasActionsExists.isEmpty()) {
            hasActionsExists = searchHasActionsAnnotation.apply(methodInvocation.getMethod().getDeclaringClass().getDeclaredAnnotations());
        }

        return hasActionsExists.map(HasActions.class::cast);
    }

    /**
     * Метод возвращает Map(название параметра, значение параметра)
     */
    private Map<String, Object> getArguments(MethodInvocation methodInvocation) {
        var parameters = Arrays.stream(methodInvocation.getMethod().getParameters()).toList();
        return parameters.stream()
                .collect(Collectors.toMap(Parameter::getName, p -> methodInvocation.getArguments()[parameters.indexOf(p)]));
    }

    /**
     * Метод ищет entityDefCode относительно HasActions::codeDefPath,
     * относительно наличия параметра ENTITY_DEF_CODE, относительно наличия сущности EntityDto
     */
    private String getEntityDefCode(MethodInvocation methodInvocation) {
        var arguments = getArguments(methodInvocation);

        var codeDefPath = getHasActionsAnnotation(methodInvocation).map(HasActions::codeDefPath).orElse(null);
        if (StringUtils.isNotEmpty(codeDefPath)) {

            var context = new StandardEvaluationContext(arguments);
            var parser = new SpelExpressionParser();
            var exp = parser.parseExpression(codeDefPath);
            return exp.getValue(context, String.class);
        }

        var entityDefCode = arguments.get(ENTITY_DEF_CODE);
        if (entityDefCode instanceof String && StringUtils.isNotEmpty((String) entityDefCode)) {
            return (String) entityDefCode;
        }

        var entityDtoParam = arguments.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof EntityDto)
                .findAny();

        if (entityDtoParam.isPresent()) {
            var defId = entityDtoParam
                    .map(Map.Entry::getValue)
                    .map(EntityDto.class::cast)
                    .map(EntityDto::getEntityDef)
                    .map(BaseRef::getId)
                    .orElse(null);
            if (defId != null) {
                return entityDefRepository.findById(defId)
                        .map(EntityDef::getCode)
                        .orElse(null);
            }
        }

        return null;
    }
}
