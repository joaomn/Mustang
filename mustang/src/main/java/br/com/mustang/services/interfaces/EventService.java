package br.com.mustang.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.EventEntity;
import br.com.mustang.enums.StatusTypeEnum;
import br.com.mustang.exceptions.GenericMustangException;

public interface EventService {
	
	void store(EventEntity event) throws GenericMustangException;
	List<EventEntity> getAll();
	Optional<EventEntity> getbyId (Long id);
	void update(Long id, EventEntity event) throws GenericMustangException;
	void delete (Long id) throws GenericMustangException;
	
	Optional<DisplayEntity> autorizationByDisplayToken (String token);
	
	List<EventEntity> getEventsByDate (LocalDate startDate, LocalDate endDate, Long displayId);
	
	List<EventEntity> getByDisplay (DisplayEntity display);
	
	StatusTypeEnum getLumonosityStatus(Double lumonsity);
	
	StatusTypeEnum getSoundStatus(Double sound);
	
	StatusTypeEnum getTemperatureStatus(Double temperature);
	
	List<EventEntity> getEventsByDateAndLuminosityStatus (LocalDate startDate, LocalDate endDate, Long displayId, String status);
	
	List<EventEntity> getEventsByDateAndSoundStatus (LocalDate startDate, LocalDate endDate, Long displayId, String status);
	
	List<EventEntity> getEventsByDateAndTemperatureStatus (LocalDate startDate, LocalDate endDate, Long displayId, String status);
	
	
	
	
}
