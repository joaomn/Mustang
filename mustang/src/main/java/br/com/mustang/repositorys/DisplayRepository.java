package br.com.mustang.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mustang.entitys.DisplayEntity;

@Repository
public interface DisplayRepository extends JpaRepository<DisplayEntity, Long> {

}
