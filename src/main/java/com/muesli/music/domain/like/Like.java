package com.muesli.music.domain.like;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.AbstractEntity;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.track.Track;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "likes")
public class Like extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long likeable_id;
    private Long user_id;
    private String likeable_type;
    private ZonedDateTime created_at;

    @Builder
    public Like(Long id, Long likeable_id, Long user_id, String likeable_type) {
        if (id == null) throw new InvalidParamException("Like.id");
        if (likeable_id == null) throw new InvalidParamException("Like.likeable_id");
        if (user_id == null) throw new InvalidParamException("Like.user_id");
        if (StringUtils.isBlank(likeable_type)) throw new InvalidParamException("Like.likeable_type");

        this.id = id;
        this.likeable_id = likeable_id;
        this.user_id = user_id;
        this.likeable_type = likeable_type;
    }
}
