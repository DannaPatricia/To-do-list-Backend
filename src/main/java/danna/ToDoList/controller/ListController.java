package danna.ToDoList.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import danna.ToDoList.dto.ListDto.CreateListDto;
import danna.ToDoList.dto.ListDto.GetListDto;
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

    //luego cambiar con verificacion de session
    @GetMapping("/{userId}")
    public ResponseEntity<List<GetListDto>> getListsByUser(@PathVariable Long userId) {
        return listService.getListsByUser(userId);
    }

    @PostMapping("/create")
    public ResponseEntity<CreateListDto> crearUsuario(@RequestBody CreateListDto newList) {
        return listService.createList(newList);
    }
}