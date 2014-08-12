package ie.markreddy;

import org.elasticsearch.client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark on 24/07/2014.
 */
public class JSONFileMapping extends ESManager {
    private static final String MAPPING_FILE = "user-mapping.json";
    private static final String TYPE = "user";
    private static final String INDEX = "json";

    private Client client;

    public JSONFileMapping(Client client) {
        super(client);
    }

    public void addMapping() {

        if (!doesIndexExist(INDEX)) {
            createIndex(INDEX);
        }

        putMapping(INDEX, TYPE, buildMapping());
    }


    public String buildMapping() {

        List<List> mutations = new ArrayList<>();
        InputStream in = getClass().getClassLoader().getResourceAsStream(MAPPING_FILE);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuilder builder = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();

    }
}
