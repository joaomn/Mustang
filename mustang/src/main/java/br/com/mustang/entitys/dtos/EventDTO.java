package br.com.mustang.entitys.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import jakarta.validation.constraints.Size;
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
	
	
	private LocalDateTime date;
	
	private Double luminosity;
	
	private Double sound;
	
	private Double temperature;
	
	private String message;
	@Size(max = 20)
    private String temperatureStatus;
	@Size(max = 20)
	private String soundStatus;
	@Size(max = 20)
	private String luminosityStatus;

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
 	   this.luminosityStatus = entity.getLuminosityStatus();
 	   this.soundStatus = entity.getSoundStatus();
 	   this.temperatureStatus = entity.getTemperatureStatus();
    }
}
