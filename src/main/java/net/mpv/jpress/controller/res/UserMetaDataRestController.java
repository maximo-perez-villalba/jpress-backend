package net.mpv.jpress.controller.res;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mpv.jpress.data.model.UserMetaData;
import net.mpv.jpress.data.model.controller.res.response.ResponseBody;
import net.mpv.jpress.data.model.controller.res.response.SaveResponseBody;
import net.mpv.jpress.data.repository.UserMetaDataRepository;

@RestController
@RequestMapping("/api/v1/user-metadata")
public class UserMetaDataRestController extends GenericResController
{

	@Autowired
	private UserMetaDataRepository repository;
	
	
	@PutMapping
	public ResponseEntity<SaveResponseBody> save(@RequestBody UserMetaData metadata)
	{
		this.repository.setVerbose(true);
		boolean response = this.repository.save(metadata);
		if(response) 
		{
			metadata = this.repository.getByKey(metadata.getUsers_id(), metadata.getKey());
			if(Objects.nonNull(metadata)) 
			{
				return this.saveOk(response, metadata.getId());
			}
		}
		return this.saveOk(false, 0);
	}
	
	@PostMapping
	public ResponseEntity<ResponseBody> update(@RequestBody UserMetaData metadata)
	{
		return this.ok(this.repository.update(metadata));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseBody> delete(@PathVariable long id)
	{
		return this.ok(this.repository.deleteById(id));
	}

	@GetMapping
	public ResponseEntity<List<UserMetaData>> findAll()
	{
		return ResponseEntity.ok(this.repository.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserMetaData> findById(@PathVariable long id)
	{
		return ResponseEntity.ok(this.repository.getById(id));
	}
	
	
}
