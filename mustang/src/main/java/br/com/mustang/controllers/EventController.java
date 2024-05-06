package br.com.mustang.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.EventEntity;
import br.com.mustang.entitys.dtos.EventDTO;
import br.com.mustang.services.implement.DisplayServiceImpl;
import br.com.mustang.services.implement.EventServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/event")
@CrossOrigin("*")
public class EventController {
	
	@Autowired
	private EventServiceImpl eventService;
	
	@Autowired
	private DisplayServiceImpl displayService;
	
	private static final long MINIMUM_REQUEST_INTERVAL = 20000; // 20 segundos
	private static final Map<String, Long> lastRequestTimes = new HashMap<>();
	
	@Operation(description = "Salvar um event")
	@PostMapping
	public ResponseEntity<EventDTO> store(@Valid @RequestBody EventEntity event){
		
		String token = event.getDisplay().getToken();
		 long currentTime = System.currentTimeMillis();
		 if (lastRequestTimes.containsKey(token) && currentTime -  lastRequestTimes.get(token) < MINIMUM_REQUEST_INTERVAL) {
		        EventDTO errorDTO = new EventDTO();
		        errorDTO.setMessage("Você deve aguardar 20 segundos entre cada solicitação");
		        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(errorDTO);
		    }
		try {
			eventService.store(event);
			 lastRequestTimes.put(token, currentTime);
			 System.out.println(lastRequestTimes);
			return ResponseEntity.status(HttpStatus.CREATED).body(event.toDto());

		} catch (RuntimeException e) {
			EventDTO errorDTO = new EventDTO();
	        errorDTO.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
		}
		
	}
	
	@Operation(description = "Obter todos os events")
	@GetMapping
	public ResponseEntity<List<EventDTO>> index(){
		try {
			List<EventDTO> list = new ArrayList<>();
			List<EventEntity> all = eventService.getAll();
			if(all.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			list = all.stream().map(user -> user.toDto()).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(list);

		} catch (RuntimeException e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@Operation(description = "Obter event por id")
	@GetMapping("/{id}")
	public ResponseEntity<EventDTO> showUserByID(@PathVariable Long id){
		
			Optional<EventEntity> obj = eventService.getbyId(id);
		
		if(obj == null) {
			EventDTO errorDTO = new EventDTO();
	        errorDTO.setMessage("Event nao encontrado");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
		}
		return ResponseEntity.status(HttpStatus.OK).body(obj.get().toDto());
		
		
	}
	
	@Operation(description = "Atualizar event")
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser ( @PathVariable Long id, @RequestBody EventEntity event){
		
		try {
			Optional<EventEntity> obj = eventService.getbyId(id);
			
			if(obj == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id errado ou não existe");
			}
			
			eventService.update(id, event);
			return ResponseEntity.status(HttpStatus.OK).body("event atualizado com sucesso");
			
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
	}
	
	
	@Operation(description = "Deletar event pelo id")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> updateUser (@PathVariable Long id){
		
			Optional<EventEntity> obj = eventService.getbyId(id);
			
			if(obj == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id errado ou não existe");
			}
			
			eventService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body("event deletado com sucesso");
			
	}
	
	
	@Operation(description = "Obter todos os events de um user pelo id de display")
	@GetMapping("/display/{id}")
	public ResponseEntity<List<EventDTO>> showDisplaysByUser(@PathVariable Long id){
		try {
			
			Optional<DisplayEntity> obj = displayService.getbyId(id);
			if(obj.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			DisplayEntity displayObj = obj.get();
			
			List<EventDTO> list = new ArrayList<>();
			List<EventEntity> all = eventService.getByDisplay(displayObj);
			if(all.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			list = all.stream().map(user ->  user.toDto()).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(list);

		} catch (RuntimeException e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	
	@Operation(description = "Obter eventos por intervalo de datas e ID do display")
	@GetMapping("/events")
	public ResponseEntity<List<EventDTO>> getEventsByDateAndDisplayId(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	                                                                  @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
	                                                                  @RequestParam("displayId") Long displayId) {
	    try {
	        List<EventEntity> events = eventService.getEventsByDate(startDate, endDate, displayId);
	        if(events.isEmpty()) {
	        	EventDTO errorDTO = new EventDTO();
		        errorDTO.setMessage("nao há registros para os parametros solicitados");
		        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonList(errorDTO));
	        }
	        
	        List<EventDTO> eventDTOs = events.stream()
	                                         .map(event -> event.toDto())
	                                         .collect(Collectors.toList());
	        return ResponseEntity.status(HttpStatus.OK).body(eventDTOs);
	    } catch (RuntimeException e) {
	        EventDTO errorDTO = new EventDTO();
	        errorDTO.setMessage(e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(errorDTO));
	    }
	}
	
	
	@Operation(description = "Obter eventos por intervalo de datas, ID do display e status de temperatura")
	@GetMapping("/events/temperature-status")
	public ResponseEntity<List<EventDTO>> getEventsByDateAndDisplayIdAndTemperatureStatus(
	        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
	        @RequestParam("displayId") Long displayId,
	        @RequestParam("temperatureStatus") String temperatureStatus) {
	    try {
	        List<EventEntity> events = eventService.getEventsByDateAndTemperatureStatus(startDate, endDate, displayId, temperatureStatus);
	        if (events.isEmpty()) {
	            EventDTO errorDTO = new EventDTO();
	            errorDTO.setMessage("Não há registros para os parâmetros solicitados");
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonList(errorDTO));
	        }

	        List<EventDTO> eventDTOs = events.stream()
	                .map(event -> event.toDto())
	                .collect(Collectors.toList());
	        return ResponseEntity.status(HttpStatus.OK).body(eventDTOs);
	    } catch (RuntimeException e) {
	        EventDTO errorDTO = new EventDTO();
	        errorDTO.setMessage(e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(errorDTO));
	    }
	}

	@Operation(description = "Obter eventos por intervalo de datas, ID do display e status de som")
	@GetMapping("/events/sound-status")
	public ResponseEntity<List<EventDTO>> getEventsByDateAndDisplayIdAndSoundStatus(
	        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
	        @RequestParam("displayId") Long displayId,
	        @RequestParam("soundStatus") String soundStatus) {
	    try {
	        List<EventEntity> events = eventService.getEventsByDateAndSoundStatus(startDate, endDate, displayId, soundStatus);
	        if (events.isEmpty()) {
	            EventDTO errorDTO = new EventDTO();
	            errorDTO.setMessage("Não há registros para os parâmetros solicitados");
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonList(errorDTO));
	        }

	        List<EventDTO> eventDTOs = events.stream()
	                .map(event -> event.toDto())
	                .collect(Collectors.toList());
	        return ResponseEntity.status(HttpStatus.OK).body(eventDTOs);
	    } catch (RuntimeException e) {
	        EventDTO errorDTO = new EventDTO();
	        errorDTO.setMessage(e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(errorDTO));
	    }
	}

	@Operation(description = "Obter eventos por intervalo de datas, ID do display e status de luminosidade")
	@GetMapping("/events/luminosity-status")
	public ResponseEntity<List<EventDTO>> getEventsByDateAndDisplayIdAndLuminosityStatus(
	        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
	        @RequestParam("displayId") Long displayId,
	        @RequestParam("luminosityStatus") String luminosityStatus) {
	    try {
	        List<EventEntity> events = eventService.getEventsByDateAndLuminosityStatus(startDate, endDate, displayId, luminosityStatus);
	        if (events.isEmpty()) {
	            EventDTO errorDTO = new EventDTO();
	            errorDTO.setMessage("Não há registros para os parâmetros solicitados");
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonList(errorDTO));
	        }

	        List<EventDTO> eventDTOs = events.stream()
	                .map(event -> event.toDto())
	                .collect(Collectors.toList());
	        return ResponseEntity.status(HttpStatus.OK).body(eventDTOs);
	    } catch (RuntimeException e) {
	        EventDTO errorDTO = new EventDTO();
	        errorDTO.setMessage(e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(errorDTO));
	    }
	}

}
