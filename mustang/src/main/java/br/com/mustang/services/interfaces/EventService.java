package br.com.mustang.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.EventEntity;
import br.com.mustang.exceptions.GenericMustangException;

public interface EventService {
	
	void store(EventEntity event) throws GenericMustangException;
	List<EventEntity> getAll();
	Optional<EventEntity> getbyId (Long id);
	void update(Long id, EventEntity event) throws GenericMustangException;
	void delete (Long id) throws GenericMustangException;
	
	Optional<DisplayEntity> autorizationByDisplayToken (String token);
	
	Optional<List<EventEntity>> getEventsByDate (LocalDate startDate, LocalDate endDate);
	
	Optional<List<EventEntity>> getByDisplay (DisplayEntity display);
	
	
	
	
}
