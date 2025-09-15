package danna.ToDoList.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import danna.ToDoList.model.ListEntity;

@Repository
public interface ListRepository extends JpaRepository<ListEntity, Long> {
    // JpaRepository ya te da métodos como findAll(), findById(), save(), deleteById()...
    List<ListEntity> findByUserId(Long userId);

    List<ListEntity> findBySharedWith_Id(Long userId);
}