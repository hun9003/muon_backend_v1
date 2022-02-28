package com.muesli.music.domain.like;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.AbstractEntity;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.track.Track;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Where;

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
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "likeable_type")
    private String likeableType;
    @Column(name = "is_like")
    private int isLike;
    @Column(name = "likeable_id")
    private Long likeableId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "likeable_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Track track;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "likeable_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "likeable_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Artist artist;

    @Builder
    public Like(Long userId, String likeableType, Long likeableId) {
        if (userId == null) throw new InvalidParamException("empty userId");
        if (likeableId == null) throw new InvalidParamException("empty likeable_id");
        if (StringUtils.isBlank(likeableType)) throw new InvalidParamException("empty likeableType");

        this.userId = userId;
        this.likeableType = likeableType;
        this.likeableId = likeableId;
        this.isLike = 1;
    }

    public void doLike() {
        this.isLike = 1;
    }

    public void doDisLike() {
        this.isLike = 0;
    }
}
