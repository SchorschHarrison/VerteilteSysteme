/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package service.dataclasses;

import quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa.Playlist;
import quentin.wwi.vertsys.javaee.jplaylist.songs.jpa.Song;

/**
 *
 * @author D070366
 */

public class SongDTO {
    
    private String artist, title;
    private String playlistName;
    private String username;
    private Long id;
    private String spotifyId;

    public SongDTO(Song song) {
        this.artist = song.getArtist();
        this.title = song.getTitle();
        this.playlistName = song.getPlaylist().getPlaylistName();
       // String b = "b";
        this.username = song.getPlaylist().getOwner().getUsername();
        this.id = song.getId();
        this.spotifyId = song.getSpotifyId();
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }
    
    
    
    
    
    
}
