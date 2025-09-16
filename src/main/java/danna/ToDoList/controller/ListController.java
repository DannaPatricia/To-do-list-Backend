package danna.ToDoList.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import danna.ToDoList.dto.ListDto.GetListDto;
import danna.ToDoList.security.CustomUserDetails;
import danna.ToDoList.service.ListService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import danna.ToDoList.dto.ListDto.CreateListDto;

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
    public ResponseEntity<CreateListDto> crearUsuario(@RequestBody CreateListDto newList,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (newList == null || newList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return listService.createList(newList, customUserDetails.getId());
    }
}