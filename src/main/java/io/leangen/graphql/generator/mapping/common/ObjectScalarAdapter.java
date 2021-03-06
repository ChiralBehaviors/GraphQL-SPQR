package io.leangen.graphql.generator.mapping.common;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import graphql.schema.GraphQLScalarType;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.annotations.GraphQLScalar;
import io.leangen.graphql.generator.BuildContext;
import io.leangen.graphql.generator.QueryGenerator;
import io.leangen.graphql.generator.mapping.OutputConverter;
import io.leangen.graphql.query.ResolutionContext;
import io.leangen.graphql.util.Scalars;

/**
 * @author Bojan Tomic (kaqqao)
 */
public class ObjectScalarAdapter extends CachingMapper<GraphQLScalarType, GraphQLScalarType> implements OutputConverter<Object, Map<String, ?>> {

    private final AnnotatedType MAP = GenericTypeReflector.annotate(LinkedHashMap.class);

    @Override
    public GraphQLScalarType toGraphQLType(String typeName, AnnotatedType javaType, Set<Type> abstractTypes, QueryGenerator queryGenerator, BuildContext buildContext) {
        return Scalars.graphQLObjectScalar(typeName);
    }
    
    @Override
    public GraphQLScalarType toGraphQLInputType(String typeName, AnnotatedType javaType, Set<Type> abstractTypes, QueryGenerator queryGenerator, BuildContext buildContext) {
        return toGraphQLType(typeName, javaType, abstractTypes, queryGenerator, buildContext);
    }

    @Override
    protected void registerAbstract(AnnotatedType type, Set<Type> abstractTypes, BuildContext buildContext) {
        abstractTypes.addAll(collectAbstract(type, new HashSet<>(), buildContext));
    }

    @Override
    public Map<String, ?> convertOutput(Object original, AnnotatedType type, ResolutionContext resolutionContext) {
        return resolutionContext.valueMapper.fromInput(original, type.getType(), MAP);
    }

    @Override
    public boolean supports(AnnotatedType type) {
        return type.isAnnotationPresent(GraphQLScalar.class);
    }
}
