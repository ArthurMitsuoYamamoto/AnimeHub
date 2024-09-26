package fiap.com.br.otakuhub.domain.avaliacao;

import fiap.com.br.otakuhub.domain.anime.Anime;
import fiap.com.br.otakuhub.domain.usuario.Usuario;
import jakarta.persistence.*;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Data
@Table(name = "avaliacao")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rating;

    @Column(length = 1000)
    private String comentario;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;
    }



