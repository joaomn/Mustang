package br.com.mustang.repositorys;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

	Optional<List<EventEntity>> findByDate(LocalDate date);
	Optional<List<EventEntity>> findByDisplay(DisplayEntity display);
}
