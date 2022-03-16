package com.muesli.music.domain.track.artist;

import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.track.Track;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "artist_track")
public class TrackArtist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private Track track;

    @Column(name = "artist_type")
    private String artistType;

    @Column(name = "primary")
    private int primary;

}
