package pl.mentoring.m16nosql.repository;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.search.queries.QueryStringQuery;
import com.couchbase.client.java.search.result.SearchResult;
import com.couchbase.client.java.search.result.SearchRow;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserSearchQueryRepository {

    public static final String FULL_TEXT_SEARCH_INDEX_NAME = "idx_full_text";

    private final Cluster couchbaseCluster;

    public UserSearchQueryRepository(Cluster couchbaseCluster) {
        this.couchbaseCluster = couchbaseCluster;
    }

    public List<String> searchByQuery(String query) {
        SearchResult searchResult = couchbaseCluster.searchQuery(FULL_TEXT_SEARCH_INDEX_NAME, new QueryStringQuery(query));

        return searchResult.rows().stream()
            .map(SearchRow::id)
            .collect(Collectors.toList());
    }
}
