package ahmed.foudi.eBanking.security.config;

import ahmed.foudi.eBanking.security.entities.AppUser;
import ahmed.foudi.eBanking.security.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));

        String userRole = !appUser.getRoles().isEmpty() ? 
            appUser.getRoles().get(0).getRole() : 
            "ROLE_USER";

        if (!userRole.startsWith("ROLE_")) {
            userRole = "ROLE_" + userRole;
        }

        return new User(
                appUser.getUsername(),
                appUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userRole))
        );
    }
} 