package br.com.mustang.repositorys;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

	Optional<List<EventEntity>> findByDate(LocalDate date);
	Optional<List<EventEntity>> findByDisplay(DisplayEntity display);
	
	 @Query("SELECT e FROM EventEntity e WHERE e.date >= :startDate AND e.date <= :endDate")
	   Optional<List<EventEntity>> findEventsOnDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
