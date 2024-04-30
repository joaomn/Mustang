package br.com.mustang.services.interfaces;

import java.util.List;
import java.util.Optional;

import br.com.mustang.entitys.UserEntity;
import br.com.mustang.exceptions.GenericMustangException;

public interface UserService {
	
	void store(UserEntity user) throws GenericMustangException;
	List<UserEntity> getAll();
	Optional<UserEntity> getbyId (Long id);
	void update(Long id, UserEntity user) throws GenericMustangException;
	void delete (Long id) throws GenericMustangException;
	
	Optional<UserEntity> getByEmail(String email) throws GenericMustangException;

}
