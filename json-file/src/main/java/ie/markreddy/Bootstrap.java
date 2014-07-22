package ie.markreddy;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class Bootstrap {
    public static void main(String[] args) {

        Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        new ElasticsearchMapping(client).addMapping();
    }

}
