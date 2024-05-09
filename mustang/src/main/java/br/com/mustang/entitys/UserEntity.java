package br.com.mustang.entitys;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.mustang.entitys.dtos.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "usuario")
@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Long id;
	@NotBlank(message = "The field 'name' is mandatory.")
	private String name;
	@NotBlank(message = "The field 'password' is mandatory.")
	private String password;
	@NotBlank(message = "The field 'email' is mandatory.")
	@Column(unique = true)
	@Email(message = "The email field requires '@' and '.com")
	private String email;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user") 
	private List<DisplayEntity> displays; 

	
	
	public UserDTO toDto() {
		return new UserDTO(this);
	}
	public UserEntity(UserDTO dto) {
		this.email = dto.getEmail();
		this.password = dto.getPassword();
		this.name = dto.getName();
		this.id = dto.getId();
		this.displays = dto.getDisplays();
	}
	

}
