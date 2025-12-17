package ru.softmachine.odyssey.backend.cms.hibernate;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.BasicType;
import org.hibernate.type.StandardBasicTypes;

public class ArrayFunctionContributor implements FunctionContributor {

    public static final String ARRAY_ANY = "array_any";
    public static final String ARRAY_OVERLAP = "array_overlap";
    public static final String ARRAY_OVERLAP_LENGTH = "overlap_length";

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        BasicType<Boolean> booleanType = functionContributions.getTypeConfiguration()
                .getBasicTypeRegistry()
                .resolve(StandardBasicTypes.BOOLEAN);
        BasicType<Integer> intType = functionContributions.getTypeConfiguration()
                .getBasicTypeRegistry()
                .resolve(StandardBasicTypes.INTEGER);

        functionContributions.getFunctionRegistry().registerPattern(
                ARRAY_ANY,
                "(?1 = ANY(?2))", booleanType);

        functionContributions.getFunctionRegistry().registerPattern(
                ARRAY_OVERLAP,
                "(cast(?1 as text array) && ?2)", booleanType);

        functionContributions.getFunctionRegistry().registerPattern(
                ARRAY_OVERLAP_LENGTH,
                "overlap_length(?1, ?2)", intType);
    }

    @Override
    public int ordinal() {
        return FunctionContributor.super.ordinal();
    }
}
