package com.muesli.music.infrastructure.user.token;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.user.token.Usertoken;
import com.muesli.music.domain.user.token.UsertokenStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsertokenStoreImpl implements UsertokenStore {
    private final UsertokenRepository usertokenRepository;

    @Override
    public Usertoken store(Usertoken initUsertoken) {
        System.out.println("UsertokenStoreImpl :: store");
        if (StringUtils.isEmpty(initUsertoken.getToken())) throw new InvalidParamException("Usertoken.token");
        if (initUsertoken.getUser().getId() == null) throw new InvalidParamException("User.id");
        return usertokenRepository.save(initUsertoken);
    }
}
