package danna.ToDoList.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import danna.ToDoList.dto.Task.TaskRequestDto;
import danna.ToDoList.dto.Task.TaskResponseDetailsDto;
import danna.ToDoList.dto.Task.TaskUpdateDto;
import danna.ToDoList.model.ListEntity;
import danna.ToDoList.model.TagEntity;
import danna.ToDoList.model.TaskEntity;
import danna.ToDoList.model.UserEntity;
import danna.ToDoList.repository.ListRepository;
import danna.ToDoList.repository.TagRepository;
import danna.ToDoList.repository.TaskRespository;
import danna.ToDoList.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class TaskService {
    private static final String USER_NOT_FOUND_MESSAGE = "Usuario no encontrado: ";
    private final TaskRespository taskRespository;
    private final UserRepository userRepository;
    private final ListRepository listRepository;
    private final TagRepository tagRepository;

    public TaskService(TaskRespository taskRespository, UserRepository userRepository, ListRepository listRepository,
            TagRepository tagRepository) {
        this.taskRespository = taskRespository;
        this.userRepository = userRepository;
        this.listRepository = listRepository;
        this.tagRepository = tagRepository;
    }

    // Metodo para obtener los detalles de la tarea seleccionada
    @Transactional
    public ResponseEntity<TaskResponseDetailsDto> getTasksDetailsById(Long taskId) {
        // Obtengo el entity del task con el repository
        return taskRespository.findById(taskId)
                // se devuelve el dto
                .map(task -> ResponseEntity.ok(new TaskResponseDetailsDto(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener las tareas de una lista mediante su id
    @Transactional
    public ResponseEntity<List<TaskResponseDetailsDto>> getTasksByListId(Long listId, Long userId) {
        try {
            // Validar que la lista le pertenece al usuario o le ha sido compartida
            getAndValidateList(listId, userId);
            // Obtener las tareas de la lista
            List<TaskEntity> tasks = taskRespository.findByListId(listId);
            // Convertir las tareas a DTOs
            List<TaskResponseDetailsDto> taskDtos = tasks.stream()
                    .map(TaskResponseDetailsDto::new)
                    .toList();
            return ResponseEntity.ok(taskDtos);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Metodo para crear una tarea, se le pasa por parameto el id de la lista
    @Transactional
    public ResponseEntity<TaskResponseDetailsDto> createTask(TaskRequestDto taskDto, Long listId, Long userId) {
        try {
            // Validacion de datos no nulos (titulo de la task)
            if (taskDto == null || taskDto.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            // Obtencion del entity de la lista para poder almacenar la task en la lista
            // deseada
            ListEntity listEntity = getAndValidateList(listId, userId);

            // Obtencion del entity del user para almacenarlo en la base de datos junto a la
            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE + userId));

            // Se pasa la taskDto a entity para almacenarlo en la lista, y se modifican el
            // id de lista y el user
            TaskEntity taskEntity = new TaskEntity(taskDto);
            taskEntity.setUser(userEntity);
            taskEntity.setList(listEntity);

            // Se retorna el task creado en dto
            return ResponseEntity.ok(new TaskResponseDetailsDto(taskRespository.save(taskEntity)));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Metodo para actualizar la tarea mediante su id
    @Transactional
    public ResponseEntity<TaskResponseDetailsDto> updateTask(TaskUpdateDto taskDto, Long taskId, Long userId) {
        try {
            // Se obtiene la entidad de la tarea para poder modificarlo y obtener el id de
            TaskEntity taskEntity = taskRespository.findById(taskId)
                    .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE + userId));

            // Se valida si la lista (donde esta guardada la tarea) le pertenece al usuario
            // o le ha sido compartida
            getAndValidateList(taskEntity.getList().getId(), userId);

            // Obtencion del entity del user para poder obtener las tags mediante el nombre
            // de las tags y el userID
            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE + userId));

            // Modificacion de los atributos del entity para guardarlo en la base de datos
            if (taskDto.getTitle() != null)
                taskEntity.setTitle(taskDto.getTitle());
            if (taskDto.getDescription() != null)
                taskEntity.setDescription(taskDto.getDescription());
            if (taskDto.getStatus() != null)
                taskEntity.setStatus(taskDto.getStatus());
            if (taskDto.getDueDate() != null)
                taskEntity.setDueDate(taskDto.getDueDate());
            // Para modificar las tags se obtiene la lista de TagEntity mediante el
            // repositorio
            if (taskDto.getTags() != null) {
                // Se obtiene la lista
                List<TagEntity> tagEntityList = new ArrayList<>(
                        taskDto.getTags().stream()
                                .map(tagName -> tagRepository.findByNameAndUser(tagName, userEntity)
                                        .orElseGet(() -> tagRepository.save(new TagEntity(tagName, userEntity))))
                                .toList());

                taskEntity.setTags(tagEntityList);
            }

            // Se retorna el dto de la task y se guarda en la base de datos la entidad
            return ResponseEntity.ok(new TaskResponseDetailsDto(taskRespository.save(taskEntity)));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Transactional
    public ResponseEntity<String> deleteTask(Long taskId, Long userId) {
        try {
            // Se obtiene la entidad de la tarea para poder verificar si tiene relacion con
            // el usuario y luego eliminarlo
            TaskEntity taskEntity = taskRespository.findById(taskId)
                    .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MESSAGE + userId));
            // Se valida si la lista (donde esta guardada la tarea) le pertenece al usuario
            // o le ha sido compartida
            getAndValidateList(taskEntity.getList().getId(), userId);
            taskRespository.delete(taskEntity);
            return ResponseEntity.ok("Tarea eliminada correctamente");
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // Metodo que devuelve la listEntity si la lista le fue compartida al user o es
    // suya
    private ListEntity getAndValidateList(Long listId, Long userId) {
        ListEntity listEntity = listRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Lista no encontrada con id " + listId));
        // Validar que el usuario que accede es el dueño de la lista o le ha sido
        // compartida
        if (!listEntity.getUser().getId().equals(userId) && !listEntity.isSharedWith(userId)) {
            throw new SecurityException("No tienes permiso para esta lista");
        }
        // Enviar la lista validada para su uso
        return listEntity;
    }
}
