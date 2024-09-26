package fiap.com.br.otakuhub.domain.anime;

import fiap.com.br.otakuhub.domain.avaliacao.Avaliacao;
import fiap.com.br.otakuhub.domain.avaliacao.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@Slf4j
@CacheConfig(cacheNames = "animes")
@RequestMapping("/animes")
@Tag(name = "animes")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping(consumes = {"multipart/form-data"})
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar um novo anime com imagem e avaliação")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Anime criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos")
    })
    public ResponseEntity<Anime> createAnime(
            @RequestPart("anime") Anime anime,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestPart(value = "avaliacao", required = false) Avaliacao avaliacao) {

        try {
            // Se houver uma avaliação, associe-a ao anime
            if (avaliacao != null) {
                avaliacao.setAnime(anime);
                anime.getAvaliacoes().add(avaliacao);
            }

            // Salva o anime primeiro
            Anime createdAnime = animeService.save(anime, imageFile);

            // Salva a avaliação, se houver
            if (avaliacao != null) {
                avaliacaoService.save(avaliacao); // Usando a instância do AvaliacaoService
            }

            return ResponseEntity.status(CREATED).body(createdAnime);
        } catch (IOException e) {
            log.error("Erro ao criar anime: {}", e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método para atualizar um anime e sua imagem
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    @CacheEvict(allEntries = true)
    @Operation(
            summary = "Atualizar um anime com imagem",
            description = "Atualiza o anime e a imagem com o ID especificado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Anime atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anime não encontrado")
    })
    public ResponseEntity<Anime> updateAnime(
            @PathVariable Long id,
            @RequestPart("anime") Anime animeDetails,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        return animeService.findById(id)
                .map(anime -> {
                    anime.setTitle(animeDetails.getTitle());
                    anime.setGenre(animeDetails.getGenre());
                    anime.setYear(animeDetails.getYear());

                    // Tentativa de salvar a imagem
                    if (imageFile != null && !imageFile.isEmpty()) {
                        String imageUrl;
                        try {
                            imageUrl = animeService.saveImage(imageFile); // Chamada ao método saveImage
                            anime.setImageUrl(imageUrl);
                        } catch (IOException e) {
                            log.error("Erro ao salvar imagem: {}", e.getMessage());
                            // Defina um campo imageUrl padrão ou nulo
                            anime.setImageUrl(null); // Ou uma URL padrão
                        }
                    }

                    // Salva o anime, agora com a imagem (ou sem)
                    Anime updatedAnime = null;
                    try {
                        updatedAnime = animeService.save(anime, imageFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return ResponseEntity.ok(updatedAnime);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Método para deletar um anime
    @DeleteMapping("/{id}")
    @CacheEvict(allEntries = true)
    @Operation(
            summary = "Deletar um anime",
            description = "Remove o anime com o ID especificado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Anime deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anime não encontrado")
    })
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        if (animeService.findById(id).isPresent()) {
            animeService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public Page<Anime> searchAnimes(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return animeService.searchAnimes(title, genre, year, pageable);
    }
}
