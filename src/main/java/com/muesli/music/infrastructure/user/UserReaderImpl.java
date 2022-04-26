package com.muesli.music.infrastructure.user;

import com.muesli.music.domain.user.User;
import com.muesli.music.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {
    private final UserRepository userRepository;
    @Override
    public User getUser(User user) {
        System.out.println("UserReaderImpl :: getUser");
        return userRepository.findById(user.getId())
                .orElse(new User());
    }

    @Override
    public User getUser(String email) {
        System.out.println("UserReaderImpl :: getUser");
        return userRepository.findByEmail(email)
                .orElse(new User());
    }

    @Override
    public User getUser(String email, String password) {
        System.out.println("UserReaderImpl :: getUser");
        return userRepository.findByEmailAndPassword(email, password)
                .orElse(new User());
    }

    @Override
    public User getUser(Long userId) {
        System.out.println("UserReaderImpl :: getUser");
        return userRepository.findById(userId).orElse(new User());
    }

    @Override
    public User getUserByusername(String username) {
        System.out.println("UserReaderImpl :: getUserByusername");
        return userRepository.findFirstByUsername(username).orElse(new User());
    }
}
