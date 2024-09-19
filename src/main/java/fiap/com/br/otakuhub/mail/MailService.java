package fiap.com.br.otakuhub.mail;

import fiap.com.br.otakuhub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private MailSender mailSender;

    public void sendWelcomeEmail(Usuario usuario) {
        var email = new SimpleMailMessage();

        email.setFrom("noreply@otakuhub.com");  // Endereço de e-mail do remetente
        email.setTo(usuario.getEmail());
        email.setSubject("Boas Vindas ao OtakuHub!");
        email.setText("""
                    Olá, %s!
                    
                    Seja bem-vindo ao OtakuHub!
                    
                    Estamos felizes por ter você aqui para gerenciar sua coleção de animes.
                    
                    Aproveite!
                    
                    Atenciosamente,
                    Equipe OtakuHub
                """.formatted(usuario.getUsername()));

        mailSender.send(email);
    }
}
