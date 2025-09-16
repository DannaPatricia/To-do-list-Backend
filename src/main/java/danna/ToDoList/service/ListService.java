package danna.ToDoList.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import danna.ToDoList.dto.ListDto;
import danna.ToDoList.model.ListEntity;
import danna.ToDoList.repository.ListRepository;
import danna.ToDoList.security.CustomUserDetails;

@Service
public class ListService {

    private final ListRepository listRepository;

    public ListService(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    // Metodo para obtener todas las listas del usuario, incluidas las compartidas
    public ResponseEntity<List<ListDto>> getListsByUser(CustomUserDetails userDetails) {
        // Obtenemos el id de la sesion del user
        long userId = userDetails.getId();
        // obtener todas las listas del user
        List<ListEntity> entities = listRepository.findByUserId(userId);
        // se añaden las listas compartidas a la List de listas del user
        entities.addAll(listRepository.findBySharedWith_Id(userId));
        
        // Se convierte las entidades a Dto, mediante su constructor, para poder retornarlo
        List<ListDto> lists = entities
             .stream()
             .map(ListDto::new)
             .toList();
        
        // Se retorna el ResponseEntity adecuado en funcion si hay contenido o no
        return lists.isEmpty() 
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(lists);
    }
}