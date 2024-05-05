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
	List<EventEntity> findByDisplay(DisplayEntity display);
	
	
	 @Query("SELECT e FROM EventEntity e WHERE DATE(e.date) >= :startDate AND DATE(e.date) <= :endDate AND e.display.id = :displayID")
	   List<EventEntity> findEventsOnDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("displayID") Long displayID);
}
