package danna.ToDoList.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import danna.ToDoList.dto.ListDto;
import danna.ToDoList.service.ListService;

@RestController
@RequestMapping("/api/lists")
public class ListController {
    private final ListService listService;

    public ListController(ListService listService) {
        this.listService = listService;
    }

    //luego cambiar con verificacion de session
    @GetMapping("/{userId}")
    public List<ListDto> getListsByUser(@PathVariable Long userId) {
        return listService.getListsByUser(userId);
    }
}