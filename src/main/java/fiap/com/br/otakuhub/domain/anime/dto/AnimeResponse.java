package fiap.com.br.otakuhub.domain.anime.dto;

import fiap.com.br.otakuhub.domain.anime.Anime;


public record AnimeResponse(
        Long id,
        String title,
        String genre,
        int year,
        String imageUrl
) {
    public AnimeResponse(Anime anime) {
        this(
                anime.getId(),
                anime.getTitle(),
                anime.getGenre(),
                anime.getYear(),
                anime.getImageUrl()
        );
    }
}
