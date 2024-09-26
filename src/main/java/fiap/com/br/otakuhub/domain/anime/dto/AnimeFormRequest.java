package fiap.com.br.otakuhub.domain.anime.dto;

import fiap.com.br.otakuhub.domain.anime.Anime;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AnimeFormRequest(

        @NotBlank(message = "{anime.title.notBlank}")
        @Size(min = 1, max = 100, message = "{anime.title.size}")
        String title,

        @NotBlank(message = "{anime.genre.notBlank}")
        @Size(min = 1, max = 50, message = "{anime.genre.size}")
        String genre,

        @NotNull(message = "{anime.year.notNull}")
        @Min(value = 1900, message = "{anime.year.min}")
        int year,


        String imageUrl
) {
    public Anime toModel() {
        return Anime.builder()
                .title(title)
                .genre(genre)
                .year(year)
                .imageUrl(imageUrl) // Este campo agora é opcional
                .build();
    }
}
