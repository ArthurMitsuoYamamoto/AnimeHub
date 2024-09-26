package fiap.com.br.otakuhub.domain.anime;

import fiap.com.br.otakuhub.domain.avaliacao.Avaliacao;
import fiap.com.br.otakuhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "anime")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 255)
    private String genre;

    @Column(name = "anime_year", nullable = false)
    private int year;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}


