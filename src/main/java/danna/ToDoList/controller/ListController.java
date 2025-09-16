package danna.ToDoList.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import danna.ToDoList.dto.ListDto;
import danna.ToDoList.security.CustomUserDetails;
import danna.ToDoList.service.ListService;

@RestController
@RequestMapping("/api/lists")
public class ListController {
    private final ListService listService;

    public ListController(ListService listService) {
        this.listService = listService;
    }

    // Metodo para obtenern las listas del usuario
    // @AuthenticationPrincipal -> Inyecta usuario autenticado directamente en el parametro del endpoint
    @GetMapping("/me")
    public ResponseEntity<List<ListDto>> getListsByUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return listService.getListsByUser(customUserDetails);
    }
}