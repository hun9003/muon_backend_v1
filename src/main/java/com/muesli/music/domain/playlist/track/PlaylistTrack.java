package com.muesli.music.domain.playlist.track;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.playlist.Playlist;
import com.muesli.music.domain.track.Track;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "playlist_track")
public class PlaylistTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private Track track;

    @Column(name = "position")
    private int position;

    @Builder
    public PlaylistTrack(Long id, Playlist playlist, Track track, int position) {
        if (playlist == null) throw new InvalidParamException("empty playlist");
        if (track == null) throw new InvalidParamException("empty track");

        this.id = id;
        this.playlist = playlist;
        this.track = track;
        this.position = position;
    }
}
