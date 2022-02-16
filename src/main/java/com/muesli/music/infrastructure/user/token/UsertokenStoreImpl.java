package com.muesli.music.infrastructure.user.token;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.user.token.Usertoken;
import com.muesli.music.domain.user.token.UsertokenStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsertokenStoreImpl implements UsertokenStore {
    private final UsertokenRepository usertokenRepository;

    @Override
    @Transactional
    public Usertoken store(Usertoken usertoken) {
        System.out.println("UsertokenStoreImpl :: store");
        if (usertoken.getUser() == null) throw new InvalidParamException("Usertoken.User");
        return usertokenRepository.save(usertoken);
    }
}
