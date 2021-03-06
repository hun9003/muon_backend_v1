package com.muesli.music.domain.album;

import com.google.common.collect.Sets;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.AbstractEntity;
import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.track.Track;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "albums")
public class Album extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Column(name = "views")
    private int views;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album", cascade = CascadeType.PERSIST)
    private Set<Track> trackList = Sets.newHashSet();

    @Where(clause = "likeable_type LIKE '%Album'")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album", cascade = CascadeType.PERSIST)
    private Set<Like> likeList = Sets.newHashSet();

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

    public void setViews(int views) {
        this.views = views + 1;
    }
}
