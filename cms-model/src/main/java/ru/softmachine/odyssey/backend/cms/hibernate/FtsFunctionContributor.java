package ru.softmachine.odyssey.backend.cms.hibernate;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.query.sqm.produce.function.FunctionParameterType;
import org.hibernate.type.BasicType;
import org.hibernate.type.StandardBasicTypes;

public class FtsFunctionContributor implements FunctionContributor {

    public static final String FTS = "fts";
    public static final String FTS_RANK = "ts_rank";
    public static final String PLAIN_QUERY = "plainto_tsquery";
    public static final String TO_QUERY = "to_tsquery";
    public static final String TO_TSVECTOR = "to_tsvector";

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        BasicType<Boolean> booleanType = functionContributions.getTypeConfiguration()
                .getBasicTypeRegistry()
                .resolve(StandardBasicTypes.BOOLEAN);
        BasicType<Double> doubleType = functionContributions.getTypeConfiguration()
                .getBasicTypeRegistry()
                .resolve(StandardBasicTypes.DOUBLE);

        functionContributions.getFunctionRegistry().registerPattern(
                FTS,
                "(?1 @@ ?2)", booleanType);

        functionContributions.getFunctionRegistry().registerPattern(
                FTS_RANK,
                "ts_rank(?1, ?2)", doubleType);

        functionContributions.getFunctionRegistry().namedDescriptorBuilder(PLAIN_QUERY)
                .setExactArgumentCount(2)
                .setParameterTypes(FunctionParameterType.STRING, FunctionParameterType.STRING)
                .register();

        functionContributions.getFunctionRegistry().namedDescriptorBuilder(TO_QUERY)
                .setExactArgumentCount(2)
                .setParameterTypes(FunctionParameterType.STRING, FunctionParameterType.STRING)
                .register();

        functionContributions.getFunctionRegistry().namedDescriptorBuilder(TO_TSVECTOR)
                .setExactArgumentCount(2)
                .setParameterTypes(FunctionParameterType.STRING, FunctionParameterType.STRING)
                .register();
    }

    @Override
    public int ordinal() {
        return FunctionContributor.super.ordinal();
    }
}
