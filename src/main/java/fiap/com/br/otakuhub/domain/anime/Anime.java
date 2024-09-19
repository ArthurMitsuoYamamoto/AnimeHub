package fiap.com.br.otakuhub.domain.anime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "anime")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String genre;
    int year;
    String imageUrl;
}
