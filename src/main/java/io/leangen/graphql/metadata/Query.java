package io.leangen.graphql.metadata;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.leangen.geantyref.GenericTypeReflector;

import static java.util.Arrays.stream;

public class Query {

    private final String name;
    private final String description;
    private final AnnotatedType javaType;
    private final List<Type> sourceTypes;
    private final Map<String, QueryResolver> resolversByFingerprint;
    private final List<QueryArgument> arguments;
    private final List<QueryArgument> sortableArguments;

    private boolean hasPrimaryResolver;

    public Query(String name, AnnotatedType javaType, List<Type> sourceTypes, List<QueryArgument> arguments, 
                 List<QueryArgument> sortableArguments, List<QueryResolver> resolvers) {
        
        this.name = name;
        this.description = resolvers.stream().map(QueryResolver::getQueryDescription).filter(desc -> !desc.isEmpty()).findFirst().orElse("");
        this.hasPrimaryResolver = resolvers.stream().anyMatch(QueryResolver::isPrimaryResolver);
        this.javaType = javaType;
        this.sourceTypes = sourceTypes;
        this.resolversByFingerprint = collectResolversByFingerprint(resolvers);
        this.arguments = arguments;
        this.sortableArguments = sortableArguments;
    }

    private Map<String, QueryResolver> collectResolversByFingerprint(List<QueryResolver> resolvers) {
        Map<String, QueryResolver> resolversByFingerprint = new HashMap<>();
        resolvers.forEach(resolver -> resolver.getFingerprints().forEach(fingerprint -> resolversByFingerprint.put(fingerprint, resolver)));
        return resolversByFingerprint;
    }

    public QueryResolver getResolver(Set<String> argumentNames) {
        return resolversByFingerprint.get(getFingerprint(argumentNames));
    }

    public boolean isEmbeddableForType(Type type) {
        return this.sourceTypes.stream().anyMatch(sourceType -> GenericTypeReflector.isSuperType(sourceType, type));
    }

    private String getFingerprint(Set<String> argumentNames) {
        StringBuilder fingerPrint = new StringBuilder();
        argumentNames.stream().sorted().forEach(fingerPrint::append);
        return fingerPrint.toString();
    }

    public boolean hasPrimaryResolver() {
        return hasPrimaryResolver;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AnnotatedType getJavaType() {
        return javaType;
    }

    public List<QueryArgument> getArguments() {
        return arguments;
    }

    public Collection<QueryResolver> getResolvers() {
        return resolversByFingerprint.values();
    }

    @Override
    public int hashCode() {
        int typeHash = stream(javaType.getAnnotations())
                .mapToInt(annotation -> annotation.getClass().getName().hashCode())
                .reduce((x, y) -> x ^ y).orElse(0);
        return name.hashCode() ^ typeHash;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Query)) return false;
        Query that = (Query) other;

        if ((that.javaType == null && this.javaType != null) || (that.javaType != null && this.javaType == null)) {
            return false;
        }

        return that.name.equals(this.name)
                && (that.javaType == null || that.javaType.equals(this.javaType));
    }

    @Override
    public String toString() {
        return name + "(" + String.join(",", arguments.stream().map(QueryArgument::getName).collect(Collectors.toList())) + ")";
    }
}
