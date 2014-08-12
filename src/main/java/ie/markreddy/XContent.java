package ie.markreddy;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class XContent extends ESManager {
    private static final String TYPE = "user";
    private static final String INDEX = "xcontent";


    public XContent(Client client) {
        super(client);
    }

    public void addMapping() {

        if (!doesIndexExist(INDEX)) {
            createIndex(INDEX);
        }

        try {
            System.out.println(buildMapping().toString());
            putMapping(INDEX, TYPE, buildMapping());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private XContentBuilder buildMapping() throws IOException {

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
