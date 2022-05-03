package com.muesli.music.domain.channel;

import java.util.List;

public interface ChannelReader {
    // 큐레이팅 리스트 호츨
    List<Channel> getChannelList();

    // 큐레이팅 호출
    Channel getChannelById(Long channelId);


}
