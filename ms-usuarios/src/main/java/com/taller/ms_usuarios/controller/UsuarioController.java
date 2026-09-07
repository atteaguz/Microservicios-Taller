package com.taller.ms_usuarios.controller;

import com.taller.ms_usuarios.dto.UsuarioRequestDTO;
import com.taller.ms_usuarios.dto.UsuarioResponseDTO;
import com.taller.ms_usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {


    //request
    //por el body = @RequestBody
    //por el url = @PathVariable

    //inyeccion de dependencias
    private final UsuarioService usuarioService;

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.ListarTodosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.obtenerUsuarioPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioDTO){

        //Mapper para convertir de DTO a entidad


        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(usuarioDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO usuarioDTO){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.actualizarUsuario(id, usuarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> eliminarUsuario(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.eliminarUsuario(id));
    }

}
