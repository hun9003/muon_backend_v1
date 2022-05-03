package com.muesli.music.domain.channel;

import java.util.List;

public interface ChannelService {
    // 큐레이팅 리스트 호출
    List<ChannelInfo.Main> getChannelList();

}
