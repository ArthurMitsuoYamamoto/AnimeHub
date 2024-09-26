package fiap.com.br.otakuhub.domain.avaliacao;

import fiap.com.br.otakuhub.domain.anime.AnimeService;
import fiap.com.br.otakuhub.domain.avaliacao.dto.AnimeWithAvaliacaoFormRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import fiap.com.br.otakuhub.domain.anime.dto.AnimeResponse;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping("/anime/{usuarioId}")
    @Operation(summary = "Criar um novo anime e uma avaliação")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Anime e avaliação criados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<AnimeResponse> createAnimeWithAvaliacao(
            @PathVariable Long usuarioId,
            @Valid @RequestBody AnimeWithAvaliacaoFormRequest request) throws IOException {
        AnimeResponse createdAnimeResponse = avaliacaoService.createAnimeWithAvaliacao(request, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnimeResponse);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obter avaliações por usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliações encontradas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma avaliação encontrada para este usuário")
    })
    public ResponseEntity<List<Avaliacao>> getAvaliacoesPorUsuario(@PathVariable Long usuarioId) {
        List<Avaliacao> avaliacoes = avaliacaoService.getAvaliacoesPorUsuario(usuarioId);
        return avaliacoes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/anime/{animeId}")
    @Operation(summary = "Obter avaliações por anime")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliações encontradas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma avaliação encontrada para este anime")
    })
    public ResponseEntity<List<Avaliacao>> getAvaliacoesPorAnime(@PathVariable Long animeId) {
        List<Avaliacao> avaliacoes = avaliacaoService.getAvaliacoesPorAnime(animeId);
        return avaliacoes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(avaliacoes);
    }

    @PutMapping("/{avaliacaoId}")
    @Operation(summary = "Atualizar uma avaliação existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public ResponseEntity<Avaliacao> updateAvaliacao(@PathVariable Long avaliacaoId, @Valid @RequestBody Avaliacao avaliacao) {
        Avaliacao updatedAvaliacao = avaliacaoService.updateAvaliacao(avaliacaoId, avaliacao);
        return ResponseEntity.ok(updatedAvaliacao);
    }
}
