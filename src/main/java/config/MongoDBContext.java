package config;

import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import ga.rugal.jpt.core.entity.PackageInfo;
import ga.rugal.jpt.core.repository.RepositoryPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Java based application context configuration class.
 *
 * @author Rugal Bernstein
 * @since 0.2
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = RepositoryPackage.class)
@PropertySource(
    {
        "classpath:jdbc.properties"
    })
public class MongoDBContext extends AbstractMongoConfiguration
{

    private static final Logger LOG = LoggerFactory.getLogger(MongoDBContext.class.getName());

    @Autowired
    private Environment env;

    @Override
    public String getDatabaseName()
    {
        return env.getProperty("mongo.db");
    }

    @Override
    @Bean
    public MongoClient mongo() throws Exception
    {
        //jdbc:postgresql://192.168.0.125:5432/postgres
        String url = env.getProperty("jdbc.url");
        int start = url.indexOf("//");
        int stop = url.indexOf(":", start);
        String host = url.substring(start + 2, stop);
        LOG.info(host);
        return new MongoClient(host, Integer.parseInt(env.getProperty("mongo.port")));
    }

    @Override
    protected String getMappingBasePackage()
    {
        return PackageInfo.class.getName();
    }

    @Bean
    @Autowired
    public GridFS gridFS(MongoClient mongo)
    {
        return new GridFS(mongo.getDB(this.getDatabaseName()), env.getProperty("mongo.bucket"));
    }

}
