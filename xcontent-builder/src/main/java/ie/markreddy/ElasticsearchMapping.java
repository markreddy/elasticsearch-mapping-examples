package ie.markreddy;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


public class ElasticsearchMapping {

    private static final String INDEX = "test_index_two";
    private static final String USER_TYPE = "user";

    private Client client;

    public ElasticsearchMapping(Client client) {
        this.client = client;
    }

    public void addMapping() {

        IndicesExistsResponse existsResponse = client.admin().indices().prepareExists(INDEX).execute().actionGet();
        if (!existsResponse.isExists()) {
            createIndex(client);
        }

        try {
            XContentBuilder mapping = getMappingSource();
            client.admin().indices()
                    .preparePutMapping()
                    .setIndices(INDEX)
                    .setType(USER_TYPE)
                    .setSource(mapping)
                    .execute().actionGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createIndex(Client client) {
        client.admin().indices().prepareCreate(INDEX)
                .setSettings(ImmutableSettings.settingsBuilder()
                        .put("number_of_shards", 1)
                        .put("number_of_replicas", 1)).execute().actionGet();

    }

    private XContentBuilder getMappingSource() throws IOException {

        XContentBuilder xMapping = jsonBuilder()
                .startObject()
                .startObject("user")
                .startObject("properties")
                .startObject("name")
                .field("type", "string")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("age")
                .field("type", "long")
                .endObject()
                .startObject("address")
                .field("type", "string")
                .field("index", "not_analyzed")
                .endObject()
                .endObject()
                .endObject()
                .endObject();

        System.out.println("xMapping.string() = " + xMapping.string());
        return xMapping;
    }
}
