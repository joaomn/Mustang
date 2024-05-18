package br.com.mustang.services.implement;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import br.com.mustang.entitys.UserEntity;
import br.com.mustang.exceptions.GenericMustangException;
import br.com.mustang.repositorys.UserRepository;
import br.com.mustang.services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void store(UserEntity user) throws GenericMustangException {
		try {
			String pass = new BCryptPasswordEncoder().encode(user.getPassword());
			user.setPassword(pass);
			userRepository.save(user);
		} catch (Exception e) {
			throw new GenericMustangException("Erro ao salvar usuario");
		}

	}

	@Override
	public List<UserEntity> getAll() {

		try {
			return userRepository.findAll();
		} catch (Exception e) {
			throw new GenericMustangException("Erro ao Obter usuarios do banco de dados");
		}
	}

	@Override
	public Optional<UserEntity> getbyId(Long id) throws GenericMustangException {
		try {
			Optional<UserEntity> user = userRepository.findById(id);
			if (user.isEmpty()) {

				return null;
			}
			return user;
		} catch (Exception e) {
			throw new GenericMustangException("Usuario nao encontrado na base de dados");
		}

	}

	@Override
	public void update(Long id, UserEntity user) throws GenericMustangException {
		try {
			Optional<UserEntity> userToUpdate = userRepository.findById(id);

			if (userToUpdate.isPresent()) {
				UserEntity userobjUpdate = userToUpdate.get();

				ModelMapper modelMapper = new ModelMapper();
				modelMapper.getConfiguration().setSkipNullEnabled(true);

				modelMapper.map(user, userobjUpdate);
				
				 if (user.getPassword() != null) {
		                String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		                userobjUpdate.setPassword(encryptedPassword);
		            }

				this.userRepository.save(userobjUpdate);

			}

		} catch (Exception e) {
			throw new GenericMustangException("erro ao atualizar usuario no banco de dados");
		}

	}

	@Override
	public void delete(Long id) throws GenericMustangException {
		Optional<UserEntity> user = userRepository.findById(id);

		if (user.isEmpty()) {
			throw new GenericMustangException("Erro ao deletar usuario no banco de dados");
		}

		userRepository.delete(user.get());

	}

	@Override
	public Optional<UserEntity> getByEmail(String email) throws GenericMustangException {

		Optional<UserEntity> user = userRepository.findByEmail(email);
		if (user.isEmpty()) {
			throw new GenericMustangException("Usuario nao encontrado na base de dados");
		}
		return user;
	}

	@Override
	public void updatePassword(Long id, String password) throws GenericMustangException {
		// TODO Auto-generated method stub

	}

	@Override
	public String generateNewRandonPassword(int lenght) {
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#.*";
	    StringBuilder senha = new StringBuilder();

	    for (int i = 0; i < lenght; i++) {
	        int index = (int) (Math.random() * caracteres.length());
	        senha.append(caracteres.charAt(index));
	    }

	    return senha.toString();
	}

}
