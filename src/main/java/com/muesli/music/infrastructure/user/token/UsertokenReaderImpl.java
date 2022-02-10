package com.muesli.music.infrastructure.user.token;

import com.muesli.music.domain.user.token.Usertoken;
import com.muesli.music.domain.user.token.UsertokenReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsertokenReaderImpl implements UsertokenReader {
    private final UsertokenRepository usertokenRepository;

    @Override
    public Usertoken getUsertoken(String token) {
        System.out.println("UsertokenReaderImpl :: getUsertoken");
        return usertokenRepository.findByToken(token)
                .orElse(new Usertoken());
    }
}
