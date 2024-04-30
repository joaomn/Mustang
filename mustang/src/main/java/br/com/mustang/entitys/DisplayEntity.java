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
	
	@NotBlank(message = "The field 'name' is mandatory.")
	private String name;
	
	@ManyToOne
	private UserEntity user_id;
	
	
	public DisplayDTO toDto() {
		return new DisplayDTO(this);
	}
	
	public DisplayEntity (DisplayDTO entity) {
	    this.id = entity.getId();
	    this.name = entity.getName();
	    this.user_id = entity.getUser_id();
	}

}
