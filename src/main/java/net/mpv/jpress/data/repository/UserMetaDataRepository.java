package net.mpv.jpress.data.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.mpv.jpress.data.mapper.UserMedataMapper;
import net.mpv.jpress.data.model.UserMetaData;

@Repository
public class UserMetaDataRepository extends DBRepository<UserMetaData> 
{

	@Override
	public UserMetaData getById(long id) 
	{
		UserMetaData metadata = null;
		try 
		{
			metadata = this.jdbcTemplate.queryForObject(
					"SELECT * FROM `users_metadata` WHERE id = ?;",
					new UserMedataMapper(),
					new Object[]{id}
				); 
		} 
		catch (Exception e) 
		{
			this.setLastException(e);
		}
		return metadata;
	}
 
	public UserMetaData getByKey(long userId, String key) 
	{
		UserMetaData metadata = null;
		try 
		{
			metadata = this.jdbcTemplate.queryForObject(
					"SELECT * FROM `users_metadata` WHERE users_id = ? AND `key` = ?;",
					new UserMedataMapper(),
					new Object[]{userId, key}
				);
		} 
		catch (Exception e) 
		{
			this.setLastException(e);
		}
		return metadata;
	}

	@Override
	public List<UserMetaData> getAll() 
	{
		return this.jdbcTemplate.query(
				"SELECT * FROM `users_metadata`;", 
				new UserMedataMapper()
			);
	}

	@Override
	protected String queryInsert(UserMetaData metadata) 
	{
		return String.format(
				"INSERT INTO `users_metadata` (`key`,`value`,users_id) VALUES ('%s','%s',%d);",
				metadata.getKey(),
				this.sanitize(metadata.getValue()),
				metadata.getUsers_id()
			);
	}

	@Override
	protected String queryUpdate(UserMetaData metadata) 
	{
		return String.format(
				"UPDATE `users_metadata` SET value='%s' WHERE id=%d;",
				this.sanitize(metadata.getValue()),
				metadata.getId()
			);
	}

	@Override
	protected String queryDelete(UserMetaData metadata) 
	{
		return String.format("DELETE FROM `users_metadata` WHERE id=%d;",metadata.getId());
	}

	@Override
	protected String queryClean() 
	{
		return String.format("DELETE FROM `users_metadata`;");
	}

	@Override
	public boolean deleteById(long id) 
	{
		return this.execute(
				String.format(
						"DELETE FROM `users_metadata` WHERE id = %d;",
						id
					)
			);
	}
	
}
