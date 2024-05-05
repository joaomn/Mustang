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

import br.com.mustang.entitys.UserEntity;
import br.com.mustang.entitys.dtos.UserDTO;
import br.com.mustang.exceptions.GenericMustangException;
import br.com.mustang.services.implement.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	@Operation(description = "Salvar um usuario")
	@PostMapping
	public ResponseEntity<UserDTO> store(@Valid @RequestBody UserEntity user){
		try {
			userService.store(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(user.toDto());

		} catch (Exception e) {
			UserDTO errorDTO = new UserDTO();
	        errorDTO.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
		}
		
	}
	
	@Operation(description = "Obter todos os usuarios")
	@GetMapping
	public ResponseEntity<List<UserDTO>> index(){
		try {
			List<UserDTO> list = new ArrayList<>();
			List<UserEntity> all = userService.getAll();
			if(all.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			list = all.stream().map(user -> user.toDto()).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(list);

		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@Operation(description = "Obter usuario por id")
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> showUserByID(@PathVariable Long id){
		
			Optional<UserEntity> userObj = userService.getbyId(id);
		
		if(userObj == null) {
			UserDTO errorDTO = new UserDTO();
	        errorDTO.setMessage("Usuario nao encontrado");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
		}
		return ResponseEntity.status(HttpStatus.OK).body(userObj.get().toDto());
		
		
	}
	
	@Operation(description = "Obter usuario por email")
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDTO> showUserByEmail(@PathVariable String email){
			Optional<UserEntity> userObj = userService.getByEmail(email);
		
		if(userObj == null) {
			UserDTO errorDTO = new UserDTO();
	        errorDTO.setMessage("Usuario nao encontrado");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
		}
		return ResponseEntity.status(HttpStatus.OK).body(userObj.get().toDto());
		
		
		
	}
	
	@Operation(description = "Atualizar Usuario")
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser ( @PathVariable Long id, @RequestBody UserEntity user){
		
		try {
			Optional<UserEntity> userObj = userService.getbyId(id);
			
			if(userObj == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id errado ou não existe");
			}
			
			userService.update(id, user);
			return ResponseEntity.status(HttpStatus.OK).body("Usuario atualizado com sucesso");
			
		} catch (GenericMustangException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
	}
	
	
	@Operation(description = "Deletar Usuario pelo id")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> updateUser (@PathVariable Long id){
		
			Optional<UserEntity> userObj = userService.getbyId(id);
			
			if(userObj == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id errado ou não existe");
			}
			
			userService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com sucesso");
			
	}
}