package br.com.mustang.entitys;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.mustang.entitys.dtos.EventDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "event")
@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
public class EventEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@JsonIgnore
	private LocalDateTime date;
	
	private Double luminosity;
	
	private Double sound;
	
	private Double temperature;
	
	@JsonIgnore
	@Size(max = 20)
	private String temperatureStatus;
	
	@JsonIgnore
	@Size(max = 20)
	private String soundStatus;
	
	@JsonIgnore
	@Size(max = 20)
	private String luminosityStatus;

    @ManyToOne
    @JoinColumn(name = "display_id") 
	private DisplayEntity display;
    
    
    public EventDTO toDto() {
		return new EventDTO(this);
	}
   public EventEntity(EventDTO dto) {
	   this.id = dto.getId();
	   this.date = dto.getDate();
	   this.display = dto.getDisplay();
	   this.luminosity = dto.getLuminosity();
	   this.sound = dto.getSound();
	   this.temperature = dto.getTemperature();
	   this.luminosityStatus = dto.getLuminosityStatus();
	   this.soundStatus = dto.getSoundStatus();
	   this.temperatureStatus = dto.getTemperatureStatus();
   }
	
	
}
