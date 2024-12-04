package ahmed.foudi.eBanking.security.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "appUser")
    private List<AppRole> roles;
}
