package com.muesli.music.domain.album;

import com.google.common.collect.Lists;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.AbstractEntity;
import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.track.Track;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "albums")
public class Album extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @Column(name = "album_code")
    private String albumCode;
    @Column(name = "name")
    private String name;
    @Column(name = "release_date")
    private String releaseDate;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "image")
    private String image;
    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album", cascade = CascadeType.PERSIST)
    private List<Track> trackList = Lists.newArrayList();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "album", cascade = CascadeType.PERSIST)
    private Like like;

    @Builder
    public Album(Long id, String albumCode, String name, String releaseDate, String originalName, String image, String artistId, String description) {
        if (id == null) throw new InvalidParamException("Albums.id");

        this.id = id;
        this.albumCode = albumCode;
        this.name = name;
        this.releaseDate = releaseDate;
        this.originalName = originalName;
        this.image = image;
        this.description = description;
    }

    @Builder
    public Album(Long id) {
        if (id == null) throw new InvalidParamException("Albums.id");

        this.id = id;
    }
}
