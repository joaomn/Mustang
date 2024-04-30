package br.com.mustang.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mustang.entitys.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

}
