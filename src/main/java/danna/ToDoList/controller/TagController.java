package danna.ToDoList.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// DTOs
import danna.ToDoList.dto.TagDto.CreateTagDto;
import danna.ToDoList.dto.TagDto.TagByUserDto;
import danna.ToDoList.dto.TagDto.UpdateTagDto;
// Servicios y seguridad
import danna.ToDoList.security.CustomUserDetails;
import danna.ToDoList.service.TagService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Obtener la etiquetas del usaurio
    @GetMapping("/me")
    public ResponseEntity<List<TagByUserDto>> tagsByUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return tagService.tagsByUser(customUserDetails.getId());
    }

    // Crear una nueva etiqueta
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> postMethodName(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody CreateTagDto createTagDto) {

        if (!createTagDto.isValid()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Datos inválidos");
            return ResponseEntity.status(409).body(response);
        }

        return tagService.createTag(createTagDto, customUserDetails.getId());
    }

    // Cambiar el nombre de una etiqueta
    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> putMethodName(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody UpdateTagDto updateTagDto) {

        if (!updateTagDto.isValid()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Datos inválidos");
            return ResponseEntity.status(409).body(response);
        }

        return tagService.updateTag(customUserDetails.getId(), updateTagDto);
    }

    // Vicular una tag a una nueva lista
    @PostMapping("/assign/{taskId}/{tagId}")
    public ResponseEntity<Map<String, String>> assignTagToTask(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long taskId,
            @PathVariable Long tagId) {
        return tagService.assignTagToTask(customUserDetails.getId(), taskId, tagId);
    }

    @PostMapping("/unassign/{taskId}/{tagId}")
    public ResponseEntity<Map<String, String>> unassignTagFromTask(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long taskId,
            @PathVariable Long tagId) {
        return tagService.unassignTagFromTask(customUserDetails.getId(), taskId, tagId);
    }

    // Eliminar una etiqueta
    @DeleteMapping("/delete/{tagId}")
    public ResponseEntity<Map<String, String>> deleteTag(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long tagId) {
        return tagService.deleteTag(customUserDetails.getId(), tagId);
    }
}