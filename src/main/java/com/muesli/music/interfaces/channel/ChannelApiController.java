package com.muesli.music.interfaces.channel;

import com.muesli.music.application.channel.ChannelFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.interfaces.album.AlbumDtoMapper;
import com.muesli.music.interfaces.playlist.PlaylistDtoMapper;
import com.muesli.music.interfaces.track.TrackDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channel")
public class ChannelApiController {

    private final ChannelFacade channelFacade;
    private final ChannelDtoMapper channelDtoMapper;
    private final AlbumDtoMapper albumDtoMapper;
    private final TrackDtoMapper trackDtoMapper;
    private final PlaylistDtoMapper playlistDtoMapper;

    @GetMapping
    public CommonResponse retrieveChannelAll(@PageableDefault(size = 8, page = 1) Pageable pageable) {
        System.out.println("ChannelApiController :: retrieveChannelAll");
        var channelInfoList = channelFacade.retrieveChannelList();
        var channelInfo = channelInfoList.get(0);
        var channelDto = channelDtoMapper.of(channelInfo);
        var response = new Object();
        switch (channelInfo.getContentType()) {
            case "album":
              var albumList = channelFacade.retrieveChannelAlbum(channelInfo.getId(), pageable);
              var albumDtoList = albumList.stream().map(albumDtoMapper::of).collect(Collectors.toList());
              response = new ChannelDto.CuratingInfoInAlbum(channelDto, albumDtoList);
        }
        return CommonResponse.success(response);
    }
}
