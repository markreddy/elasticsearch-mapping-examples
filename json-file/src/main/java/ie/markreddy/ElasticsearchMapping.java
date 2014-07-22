package ie.markreddy;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ElasticsearchMapping {

    private static final String INDEX = "test_index_one";
    private static final String USER_TYPE = "user";
    private static final String MAPPING_FILE = "user-mapping.json";

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
            String mapping = getMappingSource();
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

    private String getMappingSource() throws IOException {

        InputStream in = getClass().getClassLoader().getResourceAsStream(MAPPING_FILE);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuilder builder = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        return builder.toString();
    }
}