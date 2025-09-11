package danna.ToDoList.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import danna.ToDoList.model.ListEntity;
import danna.ToDoList.service.ListService;

@RestController
@RequestMapping("api/lists")
public class ListController {
    private final ListService listService;

    public ListController (ListService listService) {
        this.listService = listService;
    }

    // Post de todas las listas 
    @GetMapping()
    public List<ListEntity> getAllLists() {
        return listService.getAllLists();
    }
}
