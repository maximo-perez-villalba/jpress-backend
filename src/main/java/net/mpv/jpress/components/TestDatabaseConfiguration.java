package net.mpv.jpress.components;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import net.mpv.jpress.repository.CategoryRepository;
import net.mpv.jpress.repository.GroupRepository;
import net.mpv.jpress.repository.UserMetaDataRepository;
import net.mpv.jpress.repository.UserRepository;

@Configuration
public class TestDatabaseConfiguration 
{
	@Bean
	public DataSource getDataSource() 
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/tests_jpress");
		dataSource.setUsername("root");
		dataSource.setPassword("admin");
		return dataSource;
	}
	
	@Bean
	public CategoryRepository getCategoryRepository() 
	{
		return new CategoryRepository(); 
	}
	
	@Bean
	public GroupRepository getGroupRepository() 
	{
		return new GroupRepository();
	} 
	
	@Bean
	public UserRepository getUserRepository() 
	{
		return new UserRepository();
	} 
	
	@Bean
	public UserMetaDataRepository getUserMetaDataRepository() 
	{
		return new UserMetaDataRepository();
	} 
}
