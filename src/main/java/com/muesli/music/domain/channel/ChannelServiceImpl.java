package com.muesli.music.domain.channel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ChannelReader channelReader;

    /**
     * 큐레이팅 리스트
     * @return
     */
    @Override
    public List<ChannelInfo.Main> getChannelList() {
        System.out.println("ChannelServiceImpl :: getChannelList");
        var channelList = channelReader.getChannelList();
        return channelList.stream().map(ChannelInfo.Main::new).collect(Collectors.toList());
    }
}
