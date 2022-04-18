package com.muesli.music.interfaces;

import com.muesli.music.domain.search.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@EnableAsync
@Service
@RequiredArgsConstructor
public class CronTask {
    private final SearchService searchService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void testSchedule() {
        searchService.saveSearchKeyword();
    }
}
