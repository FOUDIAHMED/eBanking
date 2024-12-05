package ahmed.foudi.eBanking.security.service;

import ahmed.foudi.eBanking.security.entities.AppRole;
import ahmed.foudi.eBanking.security.entities.AppUser;
import ahmed.foudi.eBanking.security.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AppUser createUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        AppRole defaultRole = new AppRole();
        defaultRole.setRole("ROLE_USER");
        defaultRole.setAppUser(user);

        user.setRoles(Collections.singletonList(defaultRole));
        
        return userRepository.save(user);
    }
} 