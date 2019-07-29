package kr.or.ddit.config.spring;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
@PropertySource("classpath:kr/or/ddit/config/mybatis/db.properties")
@Configuration
public class ApplicationDatasource {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource datasource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl(env.getProperty("jdbc.url"));
		ds.setDriverClassName(env.getProperty("jdbc.driver"));
		ds.setUsername(env.getProperty("jdbc.username"));
		ds.setPassword(env.getProperty("jdbc.password"));
		return ds;
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() {
		SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
		sfb.setConfigLocation(new ClassPathResource("kr/or/ddit/config/mybatis/mybatis-config.xml"));
		sfb.setDataSource(datasource());
		return sfb;
	}
	
	@Bean
	public SqlSessionTemplate sqlSession() throws Exception {
		SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory().getObject());
		return sqlSession;
	}
}
