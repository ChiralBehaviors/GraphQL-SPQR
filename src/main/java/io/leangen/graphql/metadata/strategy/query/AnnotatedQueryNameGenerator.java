package io.leangen.graphql.metadata.strategy.query;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.util.ClassUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by bojan.tomic on 7/3/16.
 */
public class AnnotatedQueryNameGenerator implements QueryNameGenerator {

    public String generateQueryName(Method queryMethod, AnnotatedType declaringType) {
        return queryName(queryMethod, declaringType);
    }

    @Override
    public String generateQueryName(Field domainField, AnnotatedType declaringType) {
        return queryName(domainField, declaringType);
    }

    private String queryName(AnnotatedElement query, AnnotatedType declaringType) {
        if (query.isAnnotationPresent(GraphQLQuery.class)) {
            return query.getAnnotation(GraphQLQuery.class).name();
        }
        Class<?> declaringClass = ClassUtils.getRawType(declaringType.getType());
        if (declaringClass.isAnnotationPresent(GraphQLQuery.class)) {
            return declaringClass.getDeclaringClass().getAnnotation(GraphQLQuery.class).name();
        }
        throw new IllegalArgumentException("Neither the method/field " + query.toString() +
                " nor the declaring class are annotated with GraphQLQuery");
    }

    @Override
    public String generateMutationName(Method mutationMethod, AnnotatedType declaringType) {
        if (mutationMethod.isAnnotationPresent(GraphQLMutation.class)) {
            return mutationMethod.getAnnotation(GraphQLMutation.class).name();
        }
        Class<?> declaringClass = ClassUtils.getRawType(declaringType.getType());
        if (declaringClass.isAnnotationPresent(GraphQLMutation.class)) {
            return declaringClass.getAnnotation(GraphQLMutation.class).name();
        }
        throw new IllegalStateException("Neither the method " + mutationMethod.toString() +
                " nor the declaring class are annotated with GraphQLQuery");
    }
}
