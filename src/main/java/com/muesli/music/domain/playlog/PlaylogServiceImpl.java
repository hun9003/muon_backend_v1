package com.muesli.music.domain.playlog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylogServiceImpl implements PlaylogService{
    private final PlaylogStore playlogStore;

    @Override
    @Transactional
    public void creagePlaylog(PlaylogCommand.RegisterPlayLog command) {
        System.out.println("PlaylogServiceImpl :: creagePlaylog");
        playlogStore.store(command.toEntity());
    }
}
