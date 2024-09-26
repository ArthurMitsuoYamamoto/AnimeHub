package fiap.com.br.otakuhub.domain.avaliacao.dto;

import fiap.com.br.otakuhub.domain.anime.Anime;
import fiap.com.br.otakuhub.domain.avaliacao.Avaliacao;
import fiap.com.br.otakuhub.domain.usuario.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Setter
@Getter
public class AnimeWithAvaliacaoFormRequest {

    @NotNull(message = "Anime não pode ser nulo")
    @Valid
    private Anime anime;

    @NotNull(message = "Avaliação não pode ser nula")
    @Valid
    private AvaliacaoForm avaliacao;

    // Campo para o upload da imagem do anime
    private MultipartFile imageFile;

    // Método para converter AnimeWithAvaliacaoFormRequest em Anime
    public Anime toModel() {
        Anime animeModel = new Anime();
        animeModel.setTitle(anime.getTitle());
        animeModel.setGenre(anime.getGenre());
        animeModel.setYear(anime.getYear());
        animeModel.setImageUrl(anime.getImageUrl());
        return animeModel;
    }


    public Avaliacao toAvaliacao(Anime animeModel, Usuario usuario) {
        Avaliacao avaliacaoModel = new Avaliacao();
        avaliacaoModel.setRating(avaliacao.getNota()); // Usando nota em vez de rating
        avaliacaoModel.setComentario(avaliacao.getComentario()); // Usando comentario em vez de comment
        avaliacaoModel.setAnime(animeModel);
        avaliacaoModel.setUsuario(usuario);
        avaliacaoModel.setCreatedAt(LocalDateTime.now()); // Inicializa createdAt com a data atual
        return avaliacaoModel;
    }


    @Setter
    @Getter
    public static class AvaliacaoForm {
        @NotNull(message = "Nota não pode ser nula")
        @Min(value = 0, message = "Nota mínima é 0")
        @Max(value = 10, message = "Nota máxima é 10")
        private Integer nota;

        private String comentario;
    }

}