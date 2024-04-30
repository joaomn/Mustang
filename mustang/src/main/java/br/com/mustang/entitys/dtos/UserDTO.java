package br.com.mustang.entitys.dtos;

import java.util.List;
import java.util.UUID;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
public class UserDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "The field 'name' is mandatory.")
	private String name;
	@NotBlank(message = "The field 'password' is mandatory.")
	private String password;
	@NotBlank(message = "The field 'email' is mandatory.")
	@Column(unique = true)
	private String email;
	@OneToMany(mappedBy = "user_id") 
	private List<DisplayEntity> displays;
	
	public UserEntity toDto() {
		return new UserEntity(this);
	}
	
	public UserDTO(UserEntity entity) {
		this.email = entity.getEmail();
		this.password = entity.getPassword();
		this.name = entity.getName();
		this.id = entity.getId();
		this.displays = entity.getDisplays();
	}

}
