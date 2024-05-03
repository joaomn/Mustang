package br.com.mustang.entitys.dtos;

import java.util.UUID;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisplayDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "The field 'name' is mandatory.")
	private String name;
	
	@Column(nullable = false)
	@Size(max = 16)
	private String token;
	
	private String message;
	
	@ManyToOne
	private UserEntity user;
	
	
	public DisplayEntity toDto() {
		return new DisplayEntity(this);
	}
	
	public DisplayDTO (DisplayEntity entity) {
	    this.id = entity.getId();
	    this.name = entity.getName();
	    this.user = entity.getUser();
	    this.token = entity.getToken();
	}
}
