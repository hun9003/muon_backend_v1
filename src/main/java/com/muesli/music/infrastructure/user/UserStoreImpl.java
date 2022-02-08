package com.muesli.music.infrastructure.user;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.user.User;
import com.muesli.music.domain.user.UserStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;

    @Override
    public User store(User user) {
        if (StringUtils.isEmpty(user.getUsername())) throw new InvalidParamException("User.username");
        if (StringUtils.isEmpty(user.getEmail())) throw new InvalidParamException("User.email");
        if (StringUtils.isEmpty(user.getPassword())) throw new InvalidParamException("User.password");
        if (StringUtils.isEmpty(user.getPhone_number())) throw new InvalidParamException("User.phone_number");

        return userRepository.save(user);
    }


}
