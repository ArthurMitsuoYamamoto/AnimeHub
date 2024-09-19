package fiap.com.br.otakuhub.domain.usuario;

import java.util.List;
import fiap.com.br.otakuhub.domain.usuario.dto.UsuarioFormRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("usuarios")
@Slf4j
@CacheConfig(cacheNames = "usuarios")
@Tag(name = "usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Cacheable
    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista com todos os usuários."
    )
    public List<Usuario> index() {
        return usuarioService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    @Operation(
            summary = "Cadastrar um usuário",
            description = "Cria um novo usuário com os dados enviados no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos")
    })
    public ResponseEntity<Usuario> create(@RequestBody @Valid UsuarioFormRequest userFormRequest) {
        log.info("Cadastrando usuário {}", userFormRequest);
        Usuario usuario = userFormRequest.toModel();
        Usuario createdUsuario = usuarioService.createUsuario(usuario);
        return ResponseEntity.status(CREATED).body(createdUsuario); // Retorna o usuário criado
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(
            summary = "Atualizar um usuário",
            description = "Atualiza o usuário com o ID especificado com os dados enviados no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody @Valid UsuarioFormRequest usuarioFormRequest) {
        log.info("Atualizando usuário com ID {} para {}", id, usuarioFormRequest);
        return usuarioService.findById(id)
                .map(existingUsuario -> {
                    Usuario usuario = usuarioFormRequest.toModel();
                    usuario.setId(id); // Define o ID para garantir a atualização correta
                    Usuario updatedUsuario = usuarioService.updateUsuario(id, usuario); // Atualiza o usuário
                    return ResponseEntity.ok(updatedUsuario); // Retorna o usuário atualizado
                })
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se o usuário não for encontrado
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Buscar um usuário por ID",
            description = "Retorna o usuário com o ID especificado."
    )
    public ResponseEntity<Usuario> show(@PathVariable Long id) {
        log.info("Buscando usuário com ID {}", id);
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(
            summary = "Deletar um usuário por ID",
            description = "Remove o usuário com o ID especificado."
    )
    public void destroy(@PathVariable Long id) {
        log.info("Deletando usuário com ID {}", id);
        usuarioService.deleteUsuario(id);
    }
}
