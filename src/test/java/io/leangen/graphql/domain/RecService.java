package io.leangen.graphql.domain;

import java.util.ArrayList;
import java.util.List;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLResolverSource;
import io.leangen.graphql.annotations.NonNull;
import io.leangen.graphql.annotations.RelayId;

/**
 * Created by bojan.tomic on 6/24/16.
 */
public class RecService {

    @GraphQLQuery(name = "user")
    public SimpleUser getByName(@GraphQLArgument(name = "name") @RelayId String name) {
        return new SimpleUser(name);
    }

    @GraphQLQuery(name = "user")
    public SimpleUser getByFriends(@GraphQLArgument(name = "friends") @NonNull List<@NonNull SimpleUser> friends) {
        return new SimpleUser("Friendly McFriend", friends.get(0));
    }

    @GraphQLQuery(name = "getEducation")
    public Education getName(@GraphQLArgument(name = "user") SimpleUser user) {
        return new Education("aaa", 12, 12);
    }

    @GraphQLQuery(name = "educations")
    public @NonNull List<@NonNull Education> getEdus(@GraphQLArgument(name = "user") @NonNull SimpleUser user) {
        List<Education> edus = new ArrayList<>();
        edus.add(new Education("aaa", 12, 12));
        return edus;
    }

    @GraphQLQuery(name = "extra")
    public String extra(@GraphQLResolverSource @GraphQLArgument(name = "education") Education source) {
        return "zmajs";
    }

//	@GraphQLQuery(name = "phreak", parentQueries = "user.education")
//	public String freak(@GraphQLResolverSource @GraphQLArgument(name = "education") Education source) {
//		return "phreak";
//	}
}
