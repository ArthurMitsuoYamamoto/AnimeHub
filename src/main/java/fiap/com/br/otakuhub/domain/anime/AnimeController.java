package fiap.com.br.otakuhub.domain.anime;

import fiap.com.br.otakuhub.domain.anime.Anime;
import fiap.com.br.otakuhub.domain.anime.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/animes")
@Tag(name = "animes")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    @GetMapping
    @Operation(
            summary = "Listar todos os animes",
            description = "Retorna uma lista com todos os animes disponíveis."
    )
    public List<Anime> getAllAnimes() {
        return animeService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar um anime por ID",
            description = "Retorna o anime com o ID especificado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Anime encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anime não encontrado")
    })
    public ResponseEntity<Anime> getAnimeById(@PathVariable Long id) {
        return animeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar um novo anime",
            description = "Cria um novo anime com os dados enviados no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Anime criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos")
    })
    public ResponseEntity<Anime> createAnime(@RequestBody Anime anime) {
        Anime createdAnime = animeService.save(anime);
        return ResponseEntity.status(201).body(createdAnime); // Retorna 201 Created
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar um anime",
            description = "Atualiza o anime com o ID especificado com os dados enviados no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Anime atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anime não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos")
    })
    public ResponseEntity<Anime> updateAnime(@PathVariable Long id, @RequestBody Anime animeDetails) {
        return animeService.findById(id)
                .map(anime -> {
                    anime.setTitle(animeDetails.getTitle());
                    anime.setGenre(animeDetails.getGenre());
                    anime.setYear(animeDetails.getYear());
                    anime.setImageUrl(animeDetails.getImageUrl());
                    Anime updatedAnime = animeService.save(anime);
                    return ResponseEntity.ok(updatedAnime);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar um anime por ID",
            description = "Remove o anime com o ID especificado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Anime deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anime não encontrado")
    })
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        if (animeService.findById(id).isPresent()) {
            animeService.delete(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        }
        return ResponseEntity.notFound().build(); // Retorna 404 Not Found
    }
}
