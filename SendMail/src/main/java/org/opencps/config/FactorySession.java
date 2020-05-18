package org.opencps.config;

import java.io.FileInputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.opencps.utils.ConstantsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
//@PropertySource("classpath:hibernate.properties")
public class FactorySession
{
	//@Autowired
	//private Environment environment;

	public Properties getInfoConfig() {
		Properties props = new Properties();
		try {
			FileInputStream fis = new FileInputStream(ConstantsUtils.PROPERETIES_CONFIG_PATH);
			props.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
	}

	@Bean
	public DataSource getDataSource()
	{
		Properties props = getInfoConfig();
		String db ="MySQL";
		String driver = props.getProperty(db+".Driver");
		String url = props.getProperty(db+".Url");
		String userName = props.getProperty(db+".Username");
		String password = props.getProperty(db+".Password");

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);

		//.info("Get DataSource successfully");
		return dataSource;
	}

	private Properties getConfigurationHibernate()
	{
		Properties props = getInfoConfig();
		String hibernateDialect = props.getProperty("Hibernate.Dialect");
		String hibernateShowSql = props.getProperty("Hibernate.ShowSql");
		String currentSessionContextClass = props.getProperty("Hibernate.SessionContext");

		Properties properties = new Properties();
		properties.put("hibernate.dialect",hibernateDialect);
		properties.put("hibernate.show_sql",hibernateShowSql);
		properties.put("current_session_context_class",currentSessionContextClass);

		//log.info("Get configuration attribute for Hibernate successfully");
		return properties;
	}

	@Bean
	public SessionFactory getSessionFactory(@Autowired DataSource dataSource)
	{
		Properties props = getInfoConfig();
		Properties configurationProperties = getConfigurationHibernate();
		String scanPackage = props.getProperty("Hibernate.PackagesToScan");

		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setHibernateProperties(configurationProperties);
		factoryBean.setPackagesToScan(scanPackage);

		try
		{
			factoryBean.afterPropertiesSet();
			SessionFactory sessionFactory = factoryBean.getObject();
			//log.info("Get SessionFactory successfully");
			return sessionFactory;
		}
		catch (Exception err)
		{
			//log.error("Get SessionFactory error, detail:");
			err.printStackTrace();
			return null;
		}
	}

}
