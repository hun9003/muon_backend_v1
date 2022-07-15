package com.muesli.music.infrastructure.playlog;

import com.muesli.music.domain.playlog.Playlog;
import com.muesli.music.domain.playlog.PlaylogStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaylogStoreImpl implements PlaylogStore {
    private final PlaylogRepository playlogRepository;

    @Override
    public void store(Playlog initPlaylog) {
        System.out.println("PlaylogStoreImpl :: store");
        playlogRepository.save(initPlaylog);
    }
}
