package telekom.com.userservice.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.SocketSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.concurrent.TimeUnit;

import static com.mongodb.MongoClientSettings.builder;

@Configuration
@RequiredArgsConstructor
public class MongoDBConfiguration {

    @Value("${mongodb.connectionUri}")
    private String connectionUri;

    @Value("${mongodb.database}")
    private String database;

    @Value("${mongodb.collection}")
    private String collection;

    @Bean
    public MongoClient mongoClient() {
        Builder mongoClientSettings = builder()
                .applyConnectionString(new ConnectionString(connectionUri))
                .applyToSocketSettings(builder -> builder
                        .applySettings(SocketSettings.builder().connectTimeout(10000, TimeUnit.MILLISECONDS).build()))
                .applyToConnectionPoolSettings(builder -> builder
                        .maxWaitTime(1000, TimeUnit.MILLISECONDS));

        return MongoClients.create(mongoClientSettings.build());
    }

    @Bean
    public MongoTemplate mongoTemplate(@Autowired MongoClient mongoClient) {
        MongoTemplate template = new MongoTemplate(mongoClient, database);

        if (!template.collectionExists(collection)) {
            template.createCollection(collection);
        }

        return template;
    }
}
