package danna.ToDoList.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import danna.ToDoList.dto.UserRegisterDto;
import danna.ToDoList.dto.ListDto.CreateListDto;
import danna.ToDoList.dto.ListDto.GetListDto;
import danna.ToDoList.dto.ListDto.ShareListDto;
import danna.ToDoList.model.ListEntity;
import danna.ToDoList.model.UserEntity;
import danna.ToDoList.repository.ListRepository;
import danna.ToDoList.repository.UserRepository;
import danna.ToDoList.security.CustomUserDetails;

@Service
public class ListService {

    private final ListRepository listRepository;
    private final UserRepository userRepository;

    public ListService(ListRepository listRepository, UserRepository userRepository) {
        this.listRepository = listRepository;
        this.userRepository = userRepository;
    }

    // Metodo para obtener todas las listas del usuario, incluidas las compartidas
    public ResponseEntity<List<GetListDto>> getListsByUser(CustomUserDetails userDetails) {
        // Obtenemos el id de la sesion del user
        long userId = userDetails.getId();
        // obtener todas las listas del user
        List<ListEntity> entities = listRepository.findByUserId(userId);
        // se añaden las listas compartidas a la List de listas del user
        entities.addAll(listRepository.findBySharedWith_Id(userId));
        
        // Se convierte las entidades a Dto, mediante su constructor, para poder retornarlo
        List<GetListDto> lists = entities
             .stream()
             .map(GetListDto::new)
             .toList();
        
        // Se retorna el ResponseEntity adecuado en funcion si hay contenido o no
        return lists.isEmpty() 
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(lists);
    }

    // Metodo para obtener la entidad de usuario mediante el id del post (luego cambiar por la session)
    public ResponseEntity<CreateListDto> createList(CreateListDto dto, Long userId) {
        // Validacion de datos no nulos
        if (dto == null || dto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Buscar el usuario por el id y obtener la entidad
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + userId));

        // setear la entidad lista
        ListEntity list = new ListEntity(dto.getName(), user);

        // guardarla en la base de datos
        list = listRepository.save(list);

        return ResponseEntity.ok(new CreateListDto(list));
    }

    // Metodo para compartir una lista ya creada busncando el usuario y la lista por el id
    public ResponseEntity<Void> createShareList(ShareListDto dto, Long userId) {
        // Para realizar un insert en la tabla intermedia, se debe obtener las dos entidades para este caso Usuario y Lista

        // Buscar el usuario por el id y obtener la entidad
        UserEntity userToShare = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + dto.getUserId()));
        // Buscar la lista por el id y obtener la entidad
        ListEntity listToShare = listRepository.findById(dto.getListId())
                .orElseThrow(() -> new RuntimeException("Lista no encontrada con id " + dto.getListId()));
        // Validar que el usuario que comparte es el dueño de la lista
        if (!listToShare.getUser().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // Agregar el usuario a la lista de compartidos
        listToShare.getSharedWith().add(userToShare);
        // spring automaticamente hace el insert en la tabla intermedia ya que detecta que solo se modifico esa relacion y no las demas tablas
        listRepository.save(listToShare);
        return ResponseEntity.ok().build();
    }

    // metodo para descompartir una lista ( no se si eso es un verbo )
    public ResponseEntity<Void> unshareList(ShareListDto dto, Long userId) {
        // Buscar el usuario por el id y obtener la entidad
        UserEntity userToUnshare = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + dto.getUserId()));
        // Buscar la lista por el id y obtener la entidad
        ListEntity listToUnshare = listRepository.findById(dto.getListId())
                .orElseThrow(() -> new RuntimeException("Lista no encontrada con id " + dto.getListId()));
        // Validar que el usuario que descomparte es el dueño de la lista
        if (!listToUnshare.getUser().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // Remover el usuario de la lista de compartidos
        listToUnshare.getSharedWith().remove(userToUnshare);
        // spring automaticamente hace el delete en la tabla intermedia ya que detecta que solo se modifico esa relacion y no las demas tablas
        listRepository.save(listToUnshare);
        return ResponseEntity.ok().build();
    }
}