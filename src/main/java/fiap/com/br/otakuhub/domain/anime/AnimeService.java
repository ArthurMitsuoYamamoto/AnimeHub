package fiap.com.br.otakuhub.domain.anime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import fiap.com.br.otakuhub.domain.avaliacao.Avaliacao;
import fiap.com.br.otakuhub.domain.avaliacao.AvaliacaoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository; // Injete o repositório de avaliação

    // Diretório onde as imagens dos animes serão armazenadas
    private final String UPLOAD_DIR = "uploads/anime-images/";

    // Método para listar todos os animes
    public List<Anime> findAll() {
        return animeRepository.findAll();
    }

    // Método para buscar um anime por ID
    public Optional<Anime> findById(Long id) {
        return animeRepository.findById(id);
    }

    // Método para salvar ou criar um novo anime com ou sem imagem
    public Anime save(Anime anime, MultipartFile imageFile) throws IOException {
        // Se a imagem não for nula e não estiver vazia, salva a imagem
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile); // Chama o método para salvar a imagem
            anime.setImageUrl(imageUrl); // Atualiza o campo imageUrl com o caminho da imagem salva
        }
        return animeRepository.save(anime); // Salva o anime no banco de dados
    }

    // Método para salvar uma avaliação
    public Avaliacao saveAvaliacao(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao); // Use a instância injetada
    }

    // Método para deletar um anime
    public void delete(Long id) {
        animeRepository.deleteById(id);
    }

    // Método para salvar a imagem do anime
    public String saveImage(MultipartFile imageFile) throws IOException {
        // Verifica se o arquivo não está vazio
        if (imageFile.isEmpty()) {
            throw new IOException("O arquivo de imagem está vazio");
        }

        // Verifica se o diretório de upload existe, se não, cria
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Gera um nome único para o arquivo (baseado no timestamp atual)
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        // Escreve o arquivo no disco
        Files.write(filePath, imageFile.getBytes());

        // Retorna o caminho relativo da imagem que será salvo no banco de dados
        return "/uploads/anime-images/" + fileName;
    }

    // Método para buscar animes com paginação
    public Page<Anime> findAllPaged(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    // Método para buscar animes com critérios de busca e paginação
    public Page<Anime> searchAnimes(String title, String genre, Integer year, Pageable pageable) {
        return animeRepository.searchAnimes(title, genre, year, pageable);
    }
}
