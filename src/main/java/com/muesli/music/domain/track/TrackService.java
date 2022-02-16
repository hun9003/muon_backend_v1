package com.muesli.music.domain.track;

import com.muesli.music.domain.user.UserInfo;

public interface TrackService {
    TrackInfo.Main findTrackInfo(Long trackId, UserInfo.Main userInfo);
}
