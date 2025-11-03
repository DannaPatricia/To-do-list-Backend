package danna.ToDoList.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import danna.ToDoList.model.TagEntity;
import danna.ToDoList.model.UserEntity;


@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    // JpaRepository ya te da métodos como findAll(), findById(), save(), deleteById()...
    List<TagEntity> findByUserId(Long userId);

    Optional<TagEntity> findByNameAndUser(String tagName, UserEntity userEntity);

    Optional<TagEntity> findByIdAndUserId(Long id, Long userId);
}