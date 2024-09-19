package fiap.com.br.otakuhub.domain.anime;

import fiap.com.br.otakuhub.domain.anime.Anime;
import fiap.com.br.otakuhub.domain.anime.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    public List<Anime> findAll() {
        return animeRepository.findAll();
    }

    public Optional<Anime> findById(Long id) {
        return animeRepository.findById(id);
    }

    public Anime save(Anime anime) {
        return animeRepository.save(anime);
    }

    public void delete(Long id) {
        animeRepository.deleteById(id);
    }
}
