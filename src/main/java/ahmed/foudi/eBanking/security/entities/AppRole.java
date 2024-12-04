package ahmed.foudi.eBanking.security.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;

}
