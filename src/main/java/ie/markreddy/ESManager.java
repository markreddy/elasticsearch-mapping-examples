package ie.markreddy;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.xcontent.XContentBuilder;

public class ESManager {

    Client client;

    public ESManager(Client client) {
        this.client = client;
    }

    public ESManager() {
    }

    public void putMapping(String index, String type, String mapping) {
         client.admin().indices()
                .preparePutMapping()
                .setIndices(index)
                .setType(type)
                .setSource(mapping)
                .execute().actionGet();
    }

    public void putMapping(String index, String type, XContentBuilder mapping) {
         client.admin().indices()
                .preparePutMapping()
                .setIndices(index)
                .setType(type)
                .setSource(mapping)
                .execute().actionGet();
    }

    public void createIndex(String indexName) {
        client.admin().indices().prepareCreate(indexName)
                .setSettings(ImmutableSettings.settingsBuilder()
                        .put("number_of_shards", 1)
                        .put("number_of_replicas", 1)).execute().actionGet();
    }

    public boolean doesIndexExist(String indexName) {
        IndicesExistsResponse existsResponse = client.admin().indices().prepareExists(indexName).execute().actionGet();
        if (!existsResponse.isExists()) {
            return false;
        } else {
            return true;
        }
    }
}

