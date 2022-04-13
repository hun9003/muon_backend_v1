package com.muesli.music.interfaces.search;

import com.muesli.music.domain.search.SearchCommand;
import com.muesli.music.interfaces.track.TrackDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class SearchDto {

    @Getter
    @Builder
    @ToString
    public static class SearchRequest {

        @NotEmpty(message = "키워드는 필수 값입니다.")
        @Size(min=1, message = "키워드는 1자 이상 입력해야 합니다.")
        private final String keyword;
        private final String type;

        public SearchRequest(String keyword, String type) {
            this.keyword = keyword;
            this.type = type;
        }

        public SearchCommand.SearchRequest toCommand() {
            return SearchCommand.SearchRequest.builder()
                    .keyword(keyword)
                    .type(type)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class SearchTrackResult {
        private final String keyword;
        private final String type;
        private final List<TrackDto.TrackSearchInfo> trackList;

        public SearchTrackResult(String keyword, String type, List<TrackDto.TrackSearchInfo> trackList) {
            this.keyword = keyword;
            this.type = type;
            this.trackList = trackList;
        }
    }
}
