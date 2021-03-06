package io.leangen.graphql.generator.mapping;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.Set;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLOutputType;
import io.leangen.graphql.generator.BuildContext;
import io.leangen.graphql.generator.QueryGenerator;

/**
 * A {@code TypeMapper} is used to map annotated Java types to a GraphQL input or output types, modeled by
 * {@link GraphQLOutputType} and {@link GraphQLInputType} respectively.
 * Types of method parameters are mapped to GraphQL input types, while the return types are mapped to GraphQL
 * output types.
 */
public interface TypeMapper {

    GraphQLOutputType toGraphQLType(AnnotatedType javaType, Set<Type> abstractTypes, QueryGenerator queryGenerator, BuildContext buildContext);
    GraphQLInputType toGraphQLInputType(AnnotatedType javaType, Set<Type> abstractTypes, QueryGenerator queryGenerator, BuildContext buildContext);

    boolean supports(AnnotatedType type);
}
