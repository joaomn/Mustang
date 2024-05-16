package br.com.mustang.entitys;

import java.util.UUID;

import br.com.mustang.entitys.dtos.DisplayDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "display")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisplayEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private String name;
	
	@Column(nullable = false, unique = true)
	@Size(max = 16)
	private String token;
	
	@NotBlank(message = "The field 'description' is mandatory.")
	private String description;
	
	@ManyToOne
	private UserEntity user;
	
	
	public DisplayDTO toDto() {
		return new DisplayDTO(this);
	}
	
	public DisplayEntity (DisplayDTO entity) {
	    this.id = entity.getId();
	    this.name = entity.getName();
	    this.user = entity.getUser();
	    this.token = entity.getToken();
	    this.description = entity.getDescription();
	}

}
