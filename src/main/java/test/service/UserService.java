package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import test.Repository.UserRepository;
import test.entity.AppUser;
import test.entity.PasswordChangeDTO;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public boolean changePassword(Long userId, PasswordChangeDTO passwordChangeDTO) {
        AppUser user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (passwordEncoder.matches(passwordChangeDTO.getOldPassword(), user.getEncrytedPassword())) {
            user.setEncrytedPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
