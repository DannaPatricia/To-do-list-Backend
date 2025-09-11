package danna.ToDoList.service;

import java.util.List;
import org.springframework.stereotype.Service;
import danna.ToDoList.repository.ListRepository;
import danna.ToDoList.model.ListEntity;

@Service
public class ListService {

    private ListRepository listRepository;

    // metodo para obtener todas las listas
    public List<ListEntity> getAllLists() {
        return listRepository.findAll();
    }
}