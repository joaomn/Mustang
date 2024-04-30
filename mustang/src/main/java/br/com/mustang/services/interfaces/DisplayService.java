package br.com.mustang.services.interfaces;

import java.util.List;
import java.util.Optional;


import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.UserEntity;
import br.com.mustang.exceptions.GenericMustangException;

public interface DisplayService {
	void store(DisplayEntity display) throws GenericMustangException;
	List<DisplayEntity> getAll();
	Optional<DisplayEntity> getbyId (Long id);
	void update(Long id, DisplayEntity display) throws GenericMustangException;
	void delete (Long id) throws GenericMustangException;
	Optional<List<DisplayEntity>> getByUser(UserEntity user);
	
	


}
