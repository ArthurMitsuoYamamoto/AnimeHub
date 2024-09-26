package fiap.com.br.otakuhub.domain.avaliacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByUsuarioId(Long usuarioId);

    List<Avaliacao> findByAnimeId(Long animeId);



}