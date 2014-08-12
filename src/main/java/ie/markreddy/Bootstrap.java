package ie.markreddy;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.util.concurrent.ExecutionException;

public class Bootstrap {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        new JSONFileMapping(client).addMapping();
        new XContent(client).addMapping();
    }
}
