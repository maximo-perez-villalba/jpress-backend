package net.mpv.jpress.data.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.mpv.jpress.data.mapper.UserMapper;
import net.mpv.jpress.data.model.User;

@Repository
public class UserRepository extends DBRepository<User> 
{

	@Override
	public User getById(long id) 
	{
		User user = null;
		try 
		{
			user = this.jdbcTemplate.queryForObject(
					"SELECT * FROM `users` WHERE id = ?;",
					new UserMapper(),
					new Object[]{id}
				); 
		} 
		catch (Exception e) 
		{
			this.setLastException(e);
		}
		
		return user;
	}

	public User getByEmail(String email) 
	{
		User user = null;
		try 
		{
			user = this.jdbcTemplate.queryForObject(
					"SELECT * FROM `users` WHERE email = ?;",
					new UserMapper(),
					new Object[]{email}
				);
		} 
		catch (Exception e) 
		{
			this.setLastException(e);
		}
		
		return user;
	}

	@Override
	public List<User> getAll() 
	{
		return this.jdbcTemplate.query(
				"SELECT * FROM `users`;", 
				new UserMapper()
			);
	}

	@Override
	protected String queryInsert(User user) 
	{
		return String.format(
				"INSERT INTO `users` (firstname,lastname,email,`password`)"
				+ " VALUES ('%s','%s','%s','%s');",
				this.sanitize(user.getFirstname()),
				this.sanitize(user.getLastname()),
				this.sanitize(user.getEmail()),
				this.sanitize(user.getPassword())
			);
	}

	@Override
	protected String queryUpdate(User user) 
	{
		return String.format(
				"UPDATE `users` SET firstname='%s',lastname='%s',email='%s',"
				+ "`password`='%s' WHERE id='%d';",
				this.sanitize(user.getFirstname()),
				this.sanitize(user.getLastname()),
				this.sanitize(user.getEmail()),
				this.sanitize(user.getPassword()),
				user.getId()
			);
	}

	@Override
	protected String queryDelete(User user) 
	{
		return String.format("DELETE FROM `users` WHERE id = %d;", user.getId());
	}

	@Override
	protected String queryClean() 
	{
		return String.format("DELETE FROM `users`;");
	}

	@Override
	public boolean deleteById(long id) 
	{
		return this.execute(
				String.format(
						"DELETE FROM `users` WHERE id = %d;", 
						id
					)
			);
	}
	
}
