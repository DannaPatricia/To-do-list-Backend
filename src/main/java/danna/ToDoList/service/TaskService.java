package danna.ToDoList.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import danna.ToDoList.dto.TaskRequestDto;
import danna.ToDoList.dto.TaskResponseDetailsDto;
import danna.ToDoList.model.ListEntity;
import danna.ToDoList.model.TaskEntity;
import danna.ToDoList.model.UserEntity;
import danna.ToDoList.repository.ListRepository;
import danna.ToDoList.repository.TaskRespository;
import danna.ToDoList.repository.UserRepository;

@Service
public class TaskService {
    private final TaskRespository taskRespository;
    private final UserRepository userRepository;
    private final ListRepository listRepository;

    public TaskService(TaskRespository taskRespository, UserRepository userRepository, ListRepository listRepository){
        this.taskRespository = taskRespository;
        this.userRepository = userRepository;
        this.listRepository = listRepository;
    }

    // Metodo para obtener los detalles de la tarea seleccionada
    public ResponseEntity<TaskResponseDetailsDto> getTasksDetailsById(Long taskId){
        // Obtengo el entity del task con el repository
        return taskRespository.findById(taskId)
        // se devuelve el dto
        .map(task -> ResponseEntity.ok(new TaskResponseDetailsDto(task)))
        .orElse(ResponseEntity.notFound().build());
    }

    // Metodo para crear una tarea, se le pasa por parameto el id de la lista
    public ResponseEntity<TaskResponseDetailsDto> createTask(TaskRequestDto taskDto, Long listId, Long userId){
        try{
            // Validacion de datos no nulos (titulo de la task)
            if (taskDto == null || taskDto.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            // Obtencion del entity de la lista para poder almacenar la task en la lista deseada
            ListEntity listEntity = getAndValidateList(listId, userId);

            // Obtencion del entity del user para almacenarlo en la base de datos junto a la task
            UserEntity userEntity = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + userId));

            // Se pasa la taskDto a entity para almacenarlo en la lista, y se modifican el id de lista y el user
            TaskEntity taskEntity = new TaskEntity(taskDto);
            taskEntity.setUser(userEntity);
            taskEntity.setList(listEntity);

            // Se retorna el task creado en dto
            return ResponseEntity.ok(new TaskResponseDetailsDto(taskRespository.save(taskEntity)));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // public ResponseEntity<TaskResponseDetailsDto> updateTask(TaskRequestDto taskDto, Long listId, Long TaskId, Long userId){

    // }

    // Metodo que devuelve la listEntity  si la lista le fue compartida al user o es suya
    private ListEntity getAndValidateList(Long listId, Long userId) {
        ListEntity listEntity = listRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Lista no encontrada con id " + listId));
        // Validar que el usuario que accede es el dueño de la lista o le ha sido compartida
        if (!listEntity.getUser().getId().equals(userId) && !listEntity.isSharedWith(userId)) {
            throw new SecurityException("No tienes permiso para esta lista");
        }
        // Enviar la lista validada para su uso
        return listEntity;
    }
}
