package danna.ToDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import danna.ToDoList.model.TaskEntity;

@Repository
public interface TaskRespository extends JpaRepository<TaskEntity, Long>{
    // JpaRepository ya te da métodos como findAll(), findById(), save(), deleteById()...
}
