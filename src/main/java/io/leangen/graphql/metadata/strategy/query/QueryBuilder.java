package io.leangen.graphql.metadata.strategy.query;

import java.util.List;

import io.leangen.graphql.metadata.Query;
import io.leangen.graphql.metadata.QueryResolver;

/**
 * @author Bojan Tomic (kaqqao)
 */
public interface QueryBuilder {

    Query buildQuery(List<QueryResolver> resolvers);
    Query buildMutation(List<QueryResolver> resolvers);
}
