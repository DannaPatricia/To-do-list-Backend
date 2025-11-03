package danna.ToDoList.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// DTOs
import danna.ToDoList.dto.TagDto.TagByUserDto;
import danna.ToDoList.dto.TagDto.UpdateTagDto;
import danna.ToDoList.dto.TagDto.CreateTagDto;
// Modelos y repositorios
import danna.ToDoList.model.TagEntity;
import danna.ToDoList.model.TaskEntity;
import danna.ToDoList.model.UserEntity;

// repositorios
import danna.ToDoList.repository.TagRepository;
import danna.ToDoList.repository.UserRepository;
import jakarta.transaction.Transactional;
import danna.ToDoList.repository.TaskRespository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final TaskRespository taskRepository;

    private static final String RESPONSE_KEY = "message";

    public TagService(TagRepository tagRepository, UserRepository userRepository, TaskRespository taskRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    // Mostrar etiquetas del usuario
    public ResponseEntity<List<TagByUserDto>> tagsByUser(Long userId) {
        List<TagEntity> tagEntity = tagRepository.findByUserId(userId);
        if (tagEntity.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // pasar de entity a dto
        List<TagByUserDto> tagsDto = tagEntity
                .stream()
                .map(TagByUserDto::new)
                .toList();

        return ResponseEntity.ok(tagsDto);
    }

    // Crear una nueva etiqueta
    public ResponseEntity<Map<String, String>> createTag(CreateTagDto createTagDto, Long userId) {
        Map<String, String> response = new HashMap<>();

        // Obtener el entity usuario
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            response.put(RESPONSE_KEY, "Usuario no encontrado");
            return ResponseEntity.status(404).body(response);
        }

        // Obtener la entidad tarea
        TaskEntity task = taskRepository.findById(createTagDto.getTaskId()).orElse(null);
        if (task == null) {
            response.put(RESPONSE_KEY, "Tarea no localizada");
            return ResponseEntity.status(404).body(response);
        }

        // Crear una nueva etiqueta con valores por defecto
        TagEntity newTag = new TagEntity(createTagDto, user, task);

        // Guardar la nueva etiqueta en la base de datos
        tagRepository.save(newTag);

        response.put(RESPONSE_KEY, "Etiqueta creada con éxito");
        return ResponseEntity.status(201).body(response);
    }

    // Cambiar el nombre de una etiqueta
    public ResponseEntity<Map<String, String>> updateTag(Long userId, UpdateTagDto updateTagDto) {
        Map<String, String> response = new HashMap<>();

        // Buscar la etiqueta por id
        TagEntity tag = tagRepository.findById(updateTagDto.getId()).orElse(null);
        if (tag == null) {
            response.put(RESPONSE_KEY, "Etiqueta no encontrada");
            return ResponseEntity.status(404).body(response);
        }

        // Verificar que la etiqueta pertenezca al usuario
        if (!tag.getUser().getId().equals(userId)) {
            response.put(RESPONSE_KEY, "No tienes permiso para modificar esta etiqueta");
            return ResponseEntity.status(403).body(response);
        }

        // Actualizar el nombre de la etiqueta
        tag.setName(updateTagDto.getName());
        tagRepository.save(tag);

        response.put(RESPONSE_KEY, "Etiqueta actualizada con éxito");
        return ResponseEntity.ok(response);
    }

    // Asignar una tag existente a una tarea
    @Transactional
    public ResponseEntity<Map<String, String>> assignTagToTask(Long userId, Long taskId, Long tagId) {
        Map<String, String> response = new HashMap<>();

        // Obtener la tarea
        TaskEntity task = taskRepository.findById(taskId)
                .orElse(null);

        if (task == null) {
            response.put(RESPONSE_KEY, "Tarea no encontrada");
            return ResponseEntity.status(404).body(response);
        }

        // Obtener la etiqueta
        TagEntity tag = tagRepository.findByIdAndUserId(tagId, userId)
                .orElse(null);

        if (tag == null) {
            response.put(RESPONSE_KEY, "Etiqueta no encontrada o no pertenece al usuario");
            return ResponseEntity.status(404).body(response);
        }

        // Evitar duplicados
        if (task.getTags().contains(tag)) {
            response.put(RESPONSE_KEY, "La etiqueta ya está asignada a la tarea");
            return ResponseEntity.status(409).body(response);
        }

        // Agregar la relación
        task.getTags().add(tag);
        taskRepository.save(task);

        response.put(RESPONSE_KEY, "Etiqueta asignada correctamente");
        return ResponseEntity.ok(response);
    }

    // Desasignar la tag
    @Transactional
    public ResponseEntity<Map<String, String>> unassignTagFromTask(Long userId, Long taskId, Long tagId) {
        // Obtener la tarea
        Map<String, String> response = new HashMap<>();

        TaskEntity task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            response.put(RESPONSE_KEY, "Tarea no encontrada");
            return ResponseEntity.status(404).body(response);
        }

        TagEntity tag = tagRepository.findByIdAndUserId(tagId, userId).orElse(null);
        if (tag == null) {
            response.put(RESPONSE_KEY, "Etiqueta no encontrada o no pertenece al usuario");
            return ResponseEntity.status(404).body(response);
        }

        if (!task.getTags().contains(tag)) {
            response.put(RESPONSE_KEY, "La etiqueta no está asignada a la tarea");
            return ResponseEntity.status(409).body(response);
        }

        task.getTags().remove(tag);
        taskRepository.save(task);

        response.put(RESPONSE_KEY, "Etiqueta desasignada correctamente");
        return ResponseEntity.ok(response);
    }

    // Eliminar una etiqueta
    public ResponseEntity<Map<String, String>> deleteTag(Long userId, Long tagId) {
        Map<String, String> response = new HashMap<>();

        // Buscar la etiqueta por id
        TagEntity tag = tagRepository.findById(tagId).orElse(null);
        if (tag == null) {
            response.put(RESPONSE_KEY, "Etiqueta no encontrada");
            return ResponseEntity.status(404).body(response);
        }

        // Verificar que la etiqueta pertenezca al usuario
        if (!tag.getUser().getId().equals(userId)) {
            response.put(RESPONSE_KEY, "No tienes permiso para eliminar esta etiqueta");
            return ResponseEntity.status(403).body(response);
        }

        // Eliminar la etiqueta
        tagRepository.deleteById(tagId);
        response.put(RESPONSE_KEY, "Etiqueta eliminada con éxito");
        return ResponseEntity.ok(response);
    }
}
