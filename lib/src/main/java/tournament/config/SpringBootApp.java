package tournament.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import tournament.compiler.DynamicClassLoader;
import tournament.compiler.ExtendedStandardJavaFileManager;

import java.lang.instrument.Instrumentation;
import java.util.Properties;

import javax.sql.DataSource;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.springframework.boot.SpringApplication;

@SpringBootApplication
@ComponentScan("tournament")
@EnableJpaRepositories("tournament")
public class SpringBootApp {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp.class, args);
	}
	
	@Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/tournament");
        dataSource.setUsername( "postgres" );
        dataSource.setPassword( "mypostgres" );
        return dataSource;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
       LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
       em.setDataSource(dataSource());
       em.setPackagesToScan(new String[] { "tournament" });

       em.setJpaVendorAdapter(new HibernateJpaVendorAdapter() );
       em.setJpaProperties(additionalProperties());

       return em;
    }
    
    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
           
        return properties;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    public JavaCompiler javaCompiler() {
    	return ToolProvider.getSystemJavaCompiler();
    }
    
    @Bean
    public DynamicClassLoader dynamicClassLoader() {
    	return new DynamicClassLoader(ClassLoader.getSystemClassLoader() );
    }
    
    @Bean
    public Instrumentation instrumentation() {
    	return tournament.config.Agent.instrument;
    }
    
    @Bean
    public ExtendedStandardJavaFileManager extendedStandardJavaFileManager() {
    	return new ExtendedStandardJavaFileManager(javaCompiler().getStandardFileManager(null, null, null), dynamicClassLoader() );
    }
}
