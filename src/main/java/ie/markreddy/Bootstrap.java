package ie.markreddy;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.util.concurrent.ExecutionException;

public class Bootstrap {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "mark")
                .build();

        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        new JSONFileMapping(client).addMapping();
        new XContent(client).addMapping();
    }
}
