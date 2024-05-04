package br.com.mustang.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.UserEntity;

@Repository
public interface DisplayRepository extends JpaRepository<DisplayEntity, Long> {
	
	 List<DisplayEntity> findByUser_Id(Long userId);
	Optional<DisplayEntity> findByToken(String token);

}
