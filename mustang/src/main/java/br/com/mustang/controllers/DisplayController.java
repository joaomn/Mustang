package br.com.mustang.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.UserEntity;
import br.com.mustang.entitys.dtos.DisplayDTO;
import br.com.mustang.services.implement.DisplayServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/display")
@CrossOrigin("*")
public class DisplayController {
	
	@Autowired
	private DisplayServiceImpl displayService;
	
	@Operation(description = "Salvar um display")
	@PostMapping
	public ResponseEntity<DisplayDTO> store(@Valid @RequestBody DisplayEntity display){
		try {
			displayService.store(display);
			return ResponseEntity.status(HttpStatus.CREATED).body(display.toDto());

		} catch (RuntimeException e) {
			DisplayDTO errorDTO = new DisplayDTO();
	        errorDTO.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
		}
		
	}
	
	@Operation(description = "Obter todos os displays")
	@GetMapping
	public ResponseEntity<List<DisplayDTO>> index(){
		try {
			List<DisplayDTO> list = new ArrayList<>();
			List<DisplayEntity> all = displayService.getAll();
			if(all.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			list = all.stream().map(user -> user.toDto()).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(list);

		} catch (RuntimeException e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@Operation(description = "Obter display por id")
	@GetMapping("/{id}")
	public ResponseEntity<DisplayDTO> showUserByID(@PathVariable Long id){
		
			Optional<DisplayEntity> obj = displayService.getbyId(id);
		
		if(obj == null) {
			DisplayDTO errorDTO = new DisplayDTO();
	        errorDTO.setMessage("Usuario nao encontrado");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
		}
		return ResponseEntity.status(HttpStatus.OK).body(obj.get().toDto());
		
		
	}
	
	@Operation(description = "Atualizar display")
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser ( @PathVariable Long id, @RequestBody DisplayEntity display){
		
		try {
			Optional<DisplayEntity> obj = displayService.getbyId(id);
			
			if(obj == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id errado ou não existe");
			}
			
			displayService.update(id, display);
			return ResponseEntity.status(HttpStatus.OK).body("display atualizado com sucesso");
			
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
	}
	
	
	@Operation(description = "Deletar Display pelo id")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> updateUser (@PathVariable Long id){
		
			Optional<DisplayEntity> obj = displayService.getbyId(id);
			
			if(obj == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id errado ou não existe");
			}
			
			displayService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body("Display deletado com sucesso");
			
	}
	
	@Operation(description = "Obter todos os displays de um user pelo id de user")
	@GetMapping("/user/{id}")
	public ResponseEntity<List<DisplayDTO>> showDisplaysByUser(@PathVariable Long id){
		try {
			
			
			List<DisplayDTO> list = new ArrayList<>();
			List<DisplayEntity> all = displayService.getByUser(id);
			if(all.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			list = all.stream().map(user ->  user.toDto()).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(list);

		} catch (RuntimeException e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@Operation(description = "Obter display por token")
	@GetMapping("/get-by-token/{token}")
	public ResponseEntity<DisplayDTO> showUserByToken(@PathVariable String token){
		
			Optional<DisplayEntity> obj = displayService.getbyToken(token.toUpperCase());
		
		if(obj == null) {
			DisplayDTO errorDTO = new DisplayDTO();
	        errorDTO.setMessage("Usuario nao encontrado");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
		}
		return ResponseEntity.status(HttpStatus.OK).body(obj.get().toDto());
		
		
	}

}
