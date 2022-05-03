package com.muesli.music.application.channel;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumService;
import com.muesli.music.domain.channel.ChannelInfo;
import com.muesli.music.domain.channel.ChannelService;
import com.muesli.music.domain.playlist.PlaylistService;
import com.muesli.music.domain.track.TrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelFacade {
    private final ChannelService channelService;
    private final TrackService trackService;
    private final PlaylistService playlistService;
    private final AlbumService albumService;

    /**
     * 큐레이팅 리스트
     * @return 큐레이팅 정보 리스트
     */
    public List<ChannelInfo.Main> retrieveChannelList() {
        System.out.println("ChannelFacade :: retrieveChannelList");
        return channelService.getChannelList();
    }

    /**
     * 채널별 앨범 리스트 호출
     * @param channelId 채널 idx
     * @param pageable 앨범 리스트 페이징 처리를 위한 객체
     * @return 채널별 앨범 정보 리스트
     */
    public List<AlbumInfo.ChannelAlbumInfo> retrieveChannelAlbum(Long channelId, Pageable pageable) {
        System.out.println("ChannelFacade :: retrieveChannelAlbum");
        return albumService.getChannelAlbum(channelId, pageable);
    }
}
