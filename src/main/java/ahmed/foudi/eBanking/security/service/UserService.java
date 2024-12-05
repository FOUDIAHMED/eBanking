package ahmed.foudi.eBanking.security.service;

import ahmed.foudi.eBanking.security.entities.AppRole;
import ahmed.foudi.eBanking.security.entities.AppUser;
import ahmed.foudi.eBanking.security.repository.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser createUser(AppUser user) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }

        // Encoder le mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Créer un rôle par défaut (ROLE_USER)
        AppRole defaultRole = new AppRole();
        defaultRole.setRole("ROLE_USER");
        defaultRole.setAppUser(user);
        
        // Associer le rôle à l'utilisateur
        user.setRoles(Collections.singletonList(defaultRole));
        
        return userRepository.save(user);
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    public void deleteUser(String username) {
        AppUser user = getUserByUsername(username);
        userRepository.delete(user);
    }

    public AppUser updateUserRole(String username, String newRole) {
        AppUser user = getUserByUsername(username);
        
        // Vérifier si le nouveau rôle commence par ROLE_
        String roleToSet = newRole.startsWith("ROLE_") ? newRole : "ROLE_" + newRole;
        
        // Mettre à jour le rôle
        user.getRoles().clear();
        AppRole role = new AppRole();
        role.setRole(roleToSet);
        role.setAppUser(user);
        user.setRoles(Collections.singletonList(role));
        
        return userRepository.save(user);
    }

    public AppUser updateUser(String username, AppUser updatedUser) {
        AppUser existingUser = getUserByUsername(username);
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public long getUserCount() {
        return userRepository.count();
    }

    @Transactional(readOnly = true)
    public String getUserRole(String username) {
        AppUser user = getUserByUsername(username);
        return user.getRoles().isEmpty() ? "ROLE_USER" : user.getRoles().get(0).getRole();
    }
} 