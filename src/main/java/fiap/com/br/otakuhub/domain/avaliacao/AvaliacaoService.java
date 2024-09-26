// AvaliacaoService.java
package fiap.com.br.otakuhub.domain.avaliacao;

import fiap.com.br.otakuhub.domain.anime.Anime;
import fiap.com.br.otakuhub.domain.anime.AnimeService;
import fiap.com.br.otakuhub.domain.anime.dto.AnimeResponse;
import fiap.com.br.otakuhub.domain.avaliacao.dto.AnimeWithAvaliacaoFormRequest;
import fiap.com.br.otakuhub.domain.usuario.Usuario;
import fiap.com.br.otakuhub.domain.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private AnimeService animeService;

    @Autowired
    private UsuarioService usuarioService;

    // Método para criar anime com avaliação
    public AnimeResponse createAnimeWithAvaliacao(AnimeWithAvaliacaoFormRequest request, Long usuarioId) throws IOException {
        // Busca o usuário
        Usuario usuario = usuarioService.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Cria o anime a partir da requisição
        Anime anime = request.toModel();
        anime.setUsuario(usuario); // Associar o usuário ao anime

        // Salva o anime no repositório
        Anime createdAnime = animeService.save(anime, request.getImageFile());

        // Cria a avaliação utilizando o método toAvaliacao
        Avaliacao avaliacao = request.toAvaliacao(createdAnime, usuario);

        // Salva a avaliação no repositório
        avaliacaoRepository.save(avaliacao);

        // Retorna a resposta com o anime criado
        return new AnimeResponse(createdAnime);
    }

    // Método para salvar uma avaliação
    public Avaliacao save(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public List<Avaliacao> getAvaliacoesPorUsuario(Long usuarioId) {
        return avaliacaoRepository.findByUsuarioId(usuarioId);
    }

    public List<Avaliacao> getAvaliacoesPorAnime(Long animeId) {
        return avaliacaoRepository.findByAnimeId(animeId);
    }

    public Avaliacao updateAvaliacao(Long avaliacaoId, Avaliacao avaliacaoDetails) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        // Atualiza os campos da avaliação
        avaliacao.setRating(avaliacaoDetails.getRating());
        avaliacao.setComentario(avaliacaoDetails.getComentario());

        return avaliacaoRepository.save(avaliacao);
    }

}
