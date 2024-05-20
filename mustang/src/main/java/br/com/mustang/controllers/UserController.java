package br.com.mustang.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import br.com.mustang.entitys.dtos.LoginRequestDTO;
import br.com.mustang.entitys.dtos.NewUserDTO;
import br.com.mustang.entitys.dtos.ResponseDTO;
import br.com.mustang.entitys.dtos.ResponseLightUserDTO;
import br.com.mustang.entitys.dtos.UserDTO;
import br.com.mustang.exceptions.GenericMustangException;
import br.com.mustang.infras.security.TokenService;
import br.com.mustang.services.implement.EmailServiceImpl;
import br.com.mustang.services.implement.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private EmailServiceImpl email;
	
	@Operation(description = "Salvar um usuario")
	@PostMapping
	public ResponseEntity<?> store(@RequestBody @Valid UserEntity user){
		try {
			userService.store(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(new NewUserDTO(user.getName(), user.getEmail(), "Usuario Criado com sucesso"));

		} catch (RuntimeException e) {
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

		} catch (RuntimeException e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@Operation(description = "Obter usuario por id")
	@GetMapping("/{id}")
	public ResponseEntity<?> showUserByID(@PathVariable Long id){
		
			Optional<UserEntity> userObj = userService.getbyId(id);
		
		if(userObj == null) {
			UserDTO errorDTO = new UserDTO();
	        errorDTO.setMessage("Usuario nao encontrado");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
		}
		return ResponseEntity.status(HttpStatus.OK).body(new NewUserDTO(userObj.get().getName(), userObj.get().getEmail(), userObj.get().getId().toString()));
		
		
	}
	
	@Operation(description = "Obter usuario por email")
	@GetMapping("/email/{email}")
	public ResponseEntity<?> showUserByEmail(@PathVariable String email){
			Optional<UserEntity> userObj = userService.getByEmail(email);
		
		if(userObj == null) {
			UserDTO errorDTO = new UserDTO();
	        errorDTO.setMessage("Usuario nao encontrado");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseLightUserDTO(userObj.get().getName(), userObj.get().getEmail(), userObj.get().getId()));
		
		
		
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
			
		} catch (RuntimeException e) {
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
	
	 @Operation(description  = "rota de login")
	 @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body){
	        UserEntity user = this.userService.getByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
	        if(new BCryptPasswordEncoder().matches(body.password(), user.getPassword())) {
	        	 String token = this.tokenService.generateToken(user);
	        	 return ResponseEntity.ok(new ResponseDTO(token));
	        }
	        return ResponseEntity.badRequest().body("Credencias erradas revise e tente novamente");
	    }
	 
	 @Operation(description  = "Gerar e definir uma nova senha para o usuário pelo email")
	 @PostMapping("/{emaill}/password")
	 public ResponseEntity<?> generateAndResetPassword(@PathVariable String emaill) {
	     Optional<UserEntity> userObj = this.userService.getByEmail(emaill);

	         

	     if (userObj.isPresent()) {
	    	 UserEntity user = userObj.get();
	         String newPass = userService.generateNewRandonPassword(8); 
	         user.setPassword(newPass);
	         try {
	        	 userService.store(user);
			} catch (GenericMustangException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	         

	         try {
	             email.sendSimpleEmail(user.getEmail(),"Sua nova senha é: " + newPass);
	         } catch (Exception e) {
	             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno para recuperar senha");
	         }

	         return ResponseEntity.status(HttpStatus.OK).body("Serenha redefinida com sucesso");
	     }

	    

	     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email de usuario errado ou nao cadastrado");
	 }
}
