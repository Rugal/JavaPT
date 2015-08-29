package config;

import com.zaxxer.hikari.HikariDataSource;
import ga.rugal.jpt.common.CommonLogContent;
import ga.rugal.jpt.common.SystemDefaultProperties;
import ga.rugal.jpt.common.tracker.server.TrackedTorrent;
import ga.rugal.jpt.common.tracker.server.Tracker;
import ga.rugal.jpt.core.entity.PackageInfo;
import java.io.File;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Java based application context configuration class.
 * <p>
 * Including data source and transaction manager configuration.
 *
 * @author Rugal Bernstein
 * @since 0.2
 */
@Configuration
@EnableTransactionManagement
@PropertySource(
    {
        "classpath:jdbc.properties",
        "classpath:hibernate.properties"
    })
@ComponentScan(basePackageClasses = ga.PackageInfo.class)
public class ApplicationContext
{

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationContext.class.getName());

    public static final String hibernate_connection_autocommit = "hibernate.connection.autocommit";

    public static final String hibernate_format_sql = "hibernate.format_sql";

    public static final String hibernate_hbm2ddl_auto = "hibernate.hbm2ddl.auto";

    public static final String hibernate_show_sql = "hibernate.show_sql";

    public static final String hibernate_current_session_context_class = "hibernate.current_session_context_class";

    public static final String hibernate_dialect = "hibernate.dialect";

    public static final String package_to_scan = PackageInfo.class.getPackage().getName();

    @Autowired
    private Environment env;

//<editor-fold defaultstate="collapsed" desc="HikariCP Datasoure Configuration" >
    @Bean(destroyMethod = "close")
    @Autowired
    public DataSource dataSource()
    {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setConnectionTestQuery("SELECT 1;");
        dataSource.setMaximumPoolSize(3);
        dataSource.setAutoCommit(false);
        //------------------------------
        return dataSource;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Hibernate Session factory configuration">
    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource datasouce)
    {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(datasouce);
        sessionFactory.setPackagesToScan(package_to_scan);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties()
    {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put(hibernate_dialect, env.getProperty(hibernate_dialect));
        hibernateProperties
            .put(hibernate_current_session_context_class, env.getProperty(hibernate_current_session_context_class));
        hibernateProperties.put(hibernate_connection_autocommit, env.getProperty(hibernate_connection_autocommit));
        hibernateProperties.put(hibernate_format_sql, env.getProperty(hibernate_format_sql));
        hibernateProperties.put(hibernate_hbm2ddl_auto, env.getProperty(hibernate_hbm2ddl_auto));
        hibernateProperties.put(hibernate_show_sql, env.getProperty(hibernate_show_sql));
//        hibernateProperties.put(hibernate_connection_provider_class, env.getProperty(hibernate_connection_provider_class));
        return hibernateProperties;

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Hibernate transaction manager">
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
    {
        HibernateTransactionManager txManager = new HibernateTransactionManager(sessionFactory);
        return txManager;
    }
//</editor-fold>

    /**
     * Create a tracker server in local, with same port to servlet container
     * Spring will start this tracker after creation.
     *
     * @return
     *
     * @throws java.io.IOException
     *
     */
//    @Bean(initMethod = "start", destroyMethod = "stop")
    public Tracker tracker() throws Exception
    {
        try
        {
            Tracker tracker = new Tracker();
            File folder = new File(SystemDefaultProperties.TORRENT_PATH);
            LOG.debug(CommonLogContent.OPEN_TORRENT_FOLDER, folder.getAbsolutePath());
            File[] torrentFiles = folder.listFiles((File dir, String fileName) -> fileName.endsWith(SystemDefaultProperties.TORRENT_SUBFIX));
            if (null != torrentFiles && torrentFiles.length != 0)
            {
                for (File torrentFile : torrentFiles)
                {
                    TrackedTorrent torrent = TrackedTorrent.load(torrentFile);
                    tracker.announce(torrent);
                }
                LOG.info(CommonLogContent.TRACKER_CREATED, torrentFiles.length);
            }
            else
            {
                LOG.info(CommonLogContent.TRACKER_NO_TORRENT);
            }
            return tracker;
        }
        catch (Exception e)
        {
            throw new Exception(CommonLogContent.TRACKER_NOT_CREATED, e);
        }
    }

}
