package br.com.mustang.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.UserEntity;
import br.com.mustang.exceptions.GenericMustangException;
import br.com.mustang.repositorys.DisplayRepository;
import br.com.mustang.services.UtilsService;
import br.com.mustang.services.interfaces.DisplayService;

@Service
public class DisplayServiceImpl implements DisplayService {
	
	@Autowired
	private DisplayRepository displayRepository;
	
	UtilsService utilsMustang = new UtilsService();

	@Override
	public void store(DisplayEntity display) throws GenericMustangException {
		
		try {
			display.setToken(UtilsService.generateRandomToken());
			if(display.getName().isEmpty()) {
				display.setName(UtilsService.generateRandomName());
			}
			displayRepository.save(display);
		} catch (Exception e) {
			throw new GenericMustangException("Erro ao persisitr display");
		}
	}

	@Override
	public List<DisplayEntity> getAll() {
		try {
			return displayRepository.findAll();
		} catch (Exception e) {
			throw new GenericMustangException("Erro ao obter os dispalys do banco de dados");
		}
	}

	@Override
	public Optional<DisplayEntity> getbyId(Long id) {
		Optional<DisplayEntity> display = displayRepository.findById(id);
		
		if(display.isEmpty()) {
			throw new GenericMustangException("Display nao encontrado na base de dados");
		}
		
		return display;
	}

	@Override
	public void update(Long id, DisplayEntity display) throws GenericMustangException {
		
		try {
			Optional<DisplayEntity> displayToUpdate = displayRepository.findById(id);
			
			if(displayToUpdate.isPresent()) {
				DisplayEntity displayObjUPT = displayToUpdate.get();
				 
				 BeanUtils.copyProperties(display, displayObjUPT, "id", "token");
				 
				 this.displayRepository.save(displayObjUPT);
			}
			
		} catch (Exception e) {
			throw new GenericMustangException("erro ao display usuario no banco de dados");
		}
		
	}

	@Override
	public void delete(Long id) throws GenericMustangException {

		Optional<DisplayEntity> display = displayRepository.findById(id);

		if (display.isEmpty()) {
			throw new GenericMustangException("Display nao encontrado na base de dados");
		}

		displayRepository.deleteById(id);
	}

	@Override
	public Optional<List<DisplayEntity>> getByUser(UserEntity user) {
		try {
			Optional<List<DisplayEntity>> findByUser_id = displayRepository.findByUser_id(user);
			
			if(findByUser_id.isEmpty()) {
				return null;
			}
			
			return findByUser_id;
		} catch (Exception e) {
			
			throw new GenericMustangException("erro ao obter os displays pelo user id");
		}
	}

}
