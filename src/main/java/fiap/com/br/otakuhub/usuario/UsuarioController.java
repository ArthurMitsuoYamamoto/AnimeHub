package fiap.com.br.otakuhub.usuario;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

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

@RestController
@RequestMapping("/usuarios")
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
            summary = "Cadastrar ou atualizar um usuário",
            description = "Cria ou atualiza um usuário com os dados enviados no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado ou atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos")
    })
    public Usuario createOrUpdate(@RequestBody @Valid Usuario usuario) {
        log.info("Cadastrando ou atualizando usuário {}", usuario);
        return usuarioService.saveUsuario(usuario);
    }

    @GetMapping("/{id}")
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

    @DeleteMapping("/{id}")
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

    @PutMapping("/{id}")
    @CacheEvict(allEntries = true)
    @Operation(
            summary = "Atualizar um usuário",
            description = "Atualiza o usuário com o ID especificado com os dados enviados no corpo da requisição."
    )
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
        log.info("Atualizando usuário com ID {} para {}", id, usuario);
        return usuarioService.findById(id)
                .map(existingUsuario -> {
                    usuario.setId(id); // Atualiza o ID para garantir que estamos atualizando o registro correto
                    Usuario updatedUsuario = usuarioService.saveUsuario(usuario); // Salva o usuário atualizado
                    return ResponseEntity.ok(updatedUsuario); // Retorna o usuário atualizado
                })
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se o usuário não for encontrado
    }
}
