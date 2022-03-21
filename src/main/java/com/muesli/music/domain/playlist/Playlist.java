package com.muesli.music.domain.playlist;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.domain.AbstractEntity;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.playlist.track.PlaylistTrack;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "playlists")
public class Playlist extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "public")
    private Long isPublic;
    @Column(name = "image")
    private String image;
    @Column(name = "views")
    private int views;
    @Column(name = "description")
    private String description;
    @Column(name = "user_id")
    private Long userId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playlist")
    List<PlaylistTrack> playlistTrackList = Lists.newArrayList();

    @Where(clause = "likeable_type LIKE '%Playlist%'")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playlist", cascade = CascadeType.PERSIST)
    private Set<Like> likeList = Sets.newHashSet();

    @Builder
    public Playlist(Long id, String name, Long isPublic, String description, Long userId) {
        if(StringUtils.isEmpty(name)) throw new InvalidParamException("empty name");
        if(StringUtils.isEmpty(description)) throw new InvalidParamException("empty description");
        if(userId == null) throw new BaseException(ErrorCode.COMMON_BAD_USERTOKEN);
        if(isPublic == null) isPublic = 0L;

        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
        this.description = description;
        this.userId = userId;
    }

    public void setPlaylist(Playlist playlist) {
        if(StringUtils.isEmpty(name)) throw new InvalidParamException("empty name");
        if(StringUtils.isEmpty(description)) throw new InvalidParamException("empty description");

        this.name = playlist.name;
        this.isPublic = playlist.isPublic;
        this.description = playlist.description;
    }

    public void setIsPublic(Long isPublic) {
        this.isPublic = isPublic;
    }

    public void setViews(int views) {
        this.views = views + 1;
    }
}
