package io.leangen.graphql.generator.exceptions;

import graphql.schema.GraphQLType;

/**
 * @author Bojan Tomic (kaqqao)
 */
public class UnresolvableTypeException extends IllegalStateException {

    public UnresolvableTypeException(GraphQLType fieldType, Object result) {
        super(String.format(
                "Exact GraphQL type for %s is unresolvable for object of type %s",
                fieldType.getName(), result.getClass().getName()));
    }
}
