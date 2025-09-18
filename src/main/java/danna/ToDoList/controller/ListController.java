package danna.ToDoList.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// DTOs
import danna.ToDoList.dto.ListDto.GetListDto;
import danna.ToDoList.dto.ListDto.ShareListDto;
import danna.ToDoList.dto.ListDto.CreateListDto;

// Serviucios y seguridad
import danna.ToDoList.security.CustomUserDetails;
import danna.ToDoList.service.ListService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/lists")
public class ListController {
    private final ListService listService;

    public ListController(ListService listService) {
        this.listService = listService;
    }

    // Metodo para obtenern las listas del usuario
    // @AuthenticationPrincipal -> Inyecta usuario autenticado directamente en el
    // parametro del endpoint
    @GetMapping("/me")
    public ResponseEntity<List<GetListDto>> getListsByUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return listService.getListsByUser(customUserDetails);
    }

    @PostMapping("/create")
    public ResponseEntity<CreateListDto> createList(@RequestBody CreateListDto newList,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (newList == null || newList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return listService.createList(newList, customUserDetails.getId());
    }

    // Metodo para compartir una lista ya creada busncando el usuario y la lista por el id
    // recordatorio meter algo para no insertar varias veces el mismo usuario en la misma lista
    @PostMapping("/share")
    public ResponseEntity<Void> createShareList(@RequestBody ShareListDto newShareList,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (newShareList == null || newShareList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return listService.createShareList(newShareList, customUserDetails.getId());
    }

    // Eliminar el compartir una lista
    @DeleteMapping("/share")
    public ResponseEntity<Void> unshareList(@RequestBody ShareListDto shareListDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (shareListDto == null || shareListDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return listService.unshareList(shareListDto, customUserDetails.getId());
    }
}