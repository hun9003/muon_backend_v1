package com.muesli.music.infrastructure.user.token;

import com.muesli.music.domain.user.token.Usertoken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsertokenRepository extends JpaRepository<Usertoken, Long> {
    Optional<Usertoken> findByToken(String token);
}
