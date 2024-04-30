package br.com.mustang.entitys.dtos;

import java.time.LocalDate;
import java.util.UUID;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.EventEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
public class EventDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@NotNull(message = "The field 'date' is mandatory.")
	private LocalDate date;
	
	private Double luminosity;
	
	private Double sound;
	
	private Double temperature;

    @ManyToOne
    @JoinColumn(name = "display_id") 
	private DisplayEntity display;
    
    public EventEntity toDto() {
		return new EventEntity(this);
	}

    public EventDTO(EventEntity entity) {
 	   this.id = entity.getId();
 	   this.date = entity.getDate();
 	   this.display = entity.getDisplay();
 	   this.luminosity = entity.getLuminosity();
 	   this.sound = entity.getSound();
 	   this.temperature = entity.getTemperature();
    }
}
