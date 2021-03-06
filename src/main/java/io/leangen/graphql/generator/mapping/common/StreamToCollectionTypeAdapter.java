package io.leangen.graphql.generator.mapping.common;

import java.lang.reflect.AnnotatedType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.geantyref.TypeFactory;
import io.leangen.graphql.generator.mapping.AbstractTypeAdapter;
import io.leangen.graphql.query.ResolutionContext;

/**
 * @author Bojan Tomic (kaqqao)
 */
public class StreamToCollectionTypeAdapter extends AbstractTypeAdapter<Stream<?>, List<?>> {

    @Override
    public List<?> convertOutput(Stream<?> original, AnnotatedType type, ResolutionContext resolutionContext) {
        return original
                .map(item -> resolutionContext.convertOutput(item, getElementType(type)))
                .collect(Collectors.toList());
    }

    @Override
    public Stream<?> convertInput(List<?> substitute, AnnotatedType type, ResolutionContext resolutionContext) {
        return substitute.stream().map(item -> resolutionContext.convertInput(item, getElementType(type), resolutionContext));
    }

    @Override
    public AnnotatedType getSubstituteType(AnnotatedType original) {
        return TypeFactory.parameterizedAnnotatedClass(List.class, original.getAnnotations(), getElementType(original));
    }
    
    private AnnotatedType getElementType(AnnotatedType type) {
        return GenericTypeReflector.getTypeParameter(type, Stream.class.getTypeParameters()[0]);
    }
}
