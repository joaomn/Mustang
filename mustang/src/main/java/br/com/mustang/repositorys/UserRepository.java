package br.com.mustang.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mustang.entitys.DisplayEntity;
import br.com.mustang.entitys.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	Optional<UserEntity> findById(Long id);
	Optional<List<UserEntity>> findByDisplays(List<DisplayEntity> displays);
	Optional<UserEntity> findByEmail(String email);

}
