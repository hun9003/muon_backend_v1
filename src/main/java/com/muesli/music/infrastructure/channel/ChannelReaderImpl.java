package com.muesli.music.infrastructure.channel;

import com.google.common.collect.Lists;
import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.domain.channel.Channel;
import com.muesli.music.domain.channel.ChannelReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelReaderImpl implements ChannelReader {
    private final ChannelRepository channelRepository;

    @Override
    public List<Channel> getChannelList() {
        System.out.println("ChannelReaderImpl :: getChannelList");
        return channelRepository.findChannelBy().orElse(Lists.newArrayList());
    }

    @Override
    public Channel getChannelById(Long channelId) {
        System.out.println("ChannelReaderImpl :: getChannelById");
        return channelRepository.findById(channelId).orElseThrow(EntityNotFoundException::new);
    }
}
