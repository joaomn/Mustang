package br.com.mustang.services.implement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.EventEntity;
import br.com.mustang.exceptions.GenericMustangException;
import br.com.mustang.exceptions.TokenException;
import br.com.mustang.repositorys.DisplayRepository;
import br.com.mustang.repositorys.EventRepository;
import br.com.mustang.services.interfaces.EventService;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private DisplayRepository displayReposotiry;
	
	@Autowired
	private EventRepository eventRepository;

	@Override
	public void store(EventEntity event) throws GenericMustangException {
		try {
			event.setDate(LocalDateTime.now());
			if(this.autorizationByDisplayToken(event.getDisplay().getToken()).isEmpty()) {
				throw new TokenException("Token invalido ou nao encontrado");
			}
			eventRepository.save(event);
		} catch (RuntimeException e) {
			throw new GenericMustangException("Falha ao persisitr evento");
		}
		
	}

	@Override
	public List<EventEntity> getAll() {
		try {
			return eventRepository.findAll();
		} catch (RuntimeException e) {
			return null;
		}
	}

	@Override
	public Optional<EventEntity> getbyId(Long id) {
		Optional<EventEntity> event = eventRepository.findById(id);

		if (event.isEmpty()) {
			return null;
		}

		return event;
	}

	@Override
	public void update(Long id, EventEntity event) throws GenericMustangException {
		try {
			Optional<EventEntity> eventToUpdate = eventRepository.findById(id);
			
			if(eventToUpdate.isPresent()) {
				EventEntity eventObjUPT = eventToUpdate.get();
				
				ModelMapper modelMapper = new ModelMapper();
				modelMapper.getConfiguration().setSkipNullEnabled(true);

				modelMapper.map(event, eventObjUPT);
				 
				 this.eventRepository.save(eventObjUPT);
			}
			
		} catch (RuntimeException e) {
			throw new GenericMustangException("erro ao atualizar evento no banco de dados");
		}
		
	}

	@Override
	public void delete(Long id) throws GenericMustangException {
		Optional<EventEntity> event = eventRepository.findById(id);

		if (event.isEmpty()) {
			event = null;
		}

		eventRepository.deleteById(id);
		
	}

	@Override
	public Optional<DisplayEntity> autorizationByDisplayToken(String token) {
		Optional<DisplayEntity> tokenObj = displayReposotiry.findByToken(token);
		if(token.isEmpty()) {
			return null;
		}
		
		return tokenObj;
	}

	@Override
	public List<EventEntity> getEventsByDate(LocalDate startDate, LocalDate endDate, Long displayId) {
		try {
			
			return eventRepository.findEventsOnDates(startDate, endDate, displayId);
		} catch (RuntimeException e) {
			throw new GenericMustangException("erro ao obter eventos pro datas");
		}
	}

	@Override
	public List<EventEntity> getByDisplay(DisplayEntity display) {
		List<EventEntity> event = eventRepository.findByDisplay(display);

		if (event.isEmpty()) {
			event = null;
		}

		return event;
	}

}
