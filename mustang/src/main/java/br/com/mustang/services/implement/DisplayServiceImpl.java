package br.com.mustang.services.implement;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
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
			if (display.getName().isEmpty() || display.getName() == null) {
				display.setName(UtilsService.generateRandomName());
			}
			displayRepository.save(display);
		} catch (RuntimeException e) {
			throw new GenericMustangException("Erro ao persisitr display");
		}
	}

	@Override
	public List<DisplayEntity> getAll() {
		try {
			return displayRepository.findAll();
		} catch (RuntimeException e) {
			return null;
		}
	}

	@Override
	public Optional<DisplayEntity> getbyId(Long id) {
		Optional<DisplayEntity> display = displayRepository.findById(id);

		if (display.isEmpty()) {
			return null;
		}

		return display;
	}

	@Override
	public void update(Long id, DisplayEntity display) throws GenericMustangException {

		try {
			Optional<DisplayEntity> displayToUpdate = displayRepository.findById(id);

			if (displayToUpdate.isPresent()) {
				DisplayEntity displayObjUPT = displayToUpdate.get();

				ModelMapper modelMapper = new ModelMapper();
				modelMapper.getConfiguration().setSkipNullEnabled(true);

				modelMapper.map(display, displayObjUPT);

				this.displayRepository.save(displayObjUPT);
			}

		} catch (RuntimeException e) {
			throw new GenericMustangException("erro ao atualizar o display no banco de dados");
		}

	}

	@Override
	public void delete(Long id) throws GenericMustangException {

		Optional<DisplayEntity> display = displayRepository.findById(id);

		if (display.isEmpty()) {
			display = null;
		}

		displayRepository.deleteById(id);
	}

	@Override
	public List<DisplayEntity> getByUser(Long id) {
		try {
			List<DisplayEntity> findByUser_id = displayRepository.findByUser_Id(id);

		
			if(findByUser_id.isEmpty()) {
				return null;
			}

			return findByUser_id;
		} catch (RuntimeException e) {

			throw new GenericMustangException("erro ao obter os displays pelo user id");
		}
	}

}
