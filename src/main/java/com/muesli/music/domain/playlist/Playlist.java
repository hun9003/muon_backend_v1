package com.muesli.music.domain.playlist;

import com.muesli.music.common.exception.InvalidParamException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "playlists")
public class Playlist {

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

    @Builder
    public Playlist(Long id, String name, Long isPublic, String image, String description, Long userId) {
        if(StringUtils.isEmpty(name)) throw new InvalidParamException("empty name");
        if(StringUtils.isEmpty(description)) throw new InvalidParamException("empty description");
        if(StringUtils.isEmpty(image)) image = "";
        if(userId == null) throw new InvalidParamException("empty userId");
        if(isPublic == null) isPublic = 0L;

        this.name = name;
        this.isPublic = isPublic;
        this.image = image;
        this.description = description;
        this.userId = userId;
    }

    public void setIsPublic(Long isPublic) {
        this.isPublic = isPublic;
    }

    public void setViews(int views) {
        this.views = views + 1;
    }
}
