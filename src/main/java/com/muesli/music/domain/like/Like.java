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

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "likeable_id", insertable = false, updatable = false)
    private Track track;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "likeable_id", insertable = false, updatable = false)
    private Album album;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "likeable_id", insertable = false, updatable = false)
    private Artist artist;

    @Builder
    public Like(Long userId, String likeableType, Object item) {
        if (userId == null) throw new InvalidParamException("Like.userId");
        if (StringUtils.isBlank(likeableType)) throw new InvalidParamException("Like.likeableType");
        System.out.println("item is Track" + (item instanceof Track));
        System.out.println("item is Album" + (item instanceof Album));
        System.out.println("item is Artist" + (item instanceof Artist));
        if(item instanceof Track) {
            this.track = (Track)item;
            this.likeableId = track.getId();
        } else if(item instanceof Album) {
            this.album = (Album)item;
            this.likeableId = album.getId();
        } else if(item instanceof Artist) {
            this.artist = (Artist)item;
            this.likeableId = artist.getId();
        }
        this.userId = userId;
        this.likeableType = likeableType;
        this.isLike = 1;
    }

    @Builder
    public Like(Long userId, String likeableType, Long likeableId) {
        if (userId == null) throw new InvalidParamException("Like.userId");
        if (likeableId == null) throw new InvalidParamException("Like.likeable_id");
        if (StringUtils.isBlank(likeableType)) throw new InvalidParamException("Like.likeableType");

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
