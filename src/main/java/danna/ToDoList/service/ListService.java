package danna.ToDoList.service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import danna.ToDoList.repository.ListRepository;
import danna.ToDoList.dto.ListDto;
import danna.ToDoList.model.ListEntity;

@Service
public class ListService {
    
    private final ListRepository listRepository;

    public ListService(ListRepository listRepository) {
        this.listRepository = listRepository;
    }
    
    public List<ListDto> getAllLists() {
        //obtener todas las entidades
        List<ListEntity> entities = listRepository.findAll();
        

        //Creo la lista para devolver despues 
        List<ListDto> dtos = new ArrayList<>();
        
        // Recorrer todas las entidades en un for para transformarlas en un dto
        for (ListEntity entity : entities) {
            // Para extraer solo lo que  nesecito del entity
            ListDto dto = convertToDto(entity);
            dtos.add(dto);
        }
        
        return dtos;
    }
    
    // Metodo separado para convertir una entidad a dto
    private ListDto convertToDto(ListEntity entity) {
        // Obtener el nombre del usuario
        String userName = null;
        if (entity.getUser() != null) {
            // se puede devolver el dto de user si lo nesecitamos depues
            userName = entity.getUser().getUsername();
        }
        
        // Crear aqui para el dto de tareas y tener una lista
        /* 
        List<Tasks> tasks
        // no se si usar la propia entidad del la listas o tomar otra
        if (entity.getTasks() != null) {
            tasks = entity.getTasks();
        }
        */

        // Crear y devolver el DTO
        return new ListDto(
            entity.getId(),
            entity.getName(),
            userName
        );
    }
}