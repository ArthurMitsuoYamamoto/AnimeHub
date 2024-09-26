package fiap.com.br.otakuhub.domain.anime;

import fiap.com.br.otakuhub.domain.anime.Anime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    @Query("SELECT a FROM Anime a WHERE "
            + "(:title IS NULL OR a.title LIKE %:title%) AND "
            + "(:genre IS NULL OR a.genre LIKE %:genre%) AND "
            + "(:year IS NULL OR a.year = :year)")
    Page<Anime> searchAnimes(@Param("title") String title,
                             @Param("genre") String genre,
                             @Param("year") Integer year,
                             Pageable pageable);
}
