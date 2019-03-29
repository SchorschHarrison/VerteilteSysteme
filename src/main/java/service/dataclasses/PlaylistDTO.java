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

import java.util.List;
import java.util.stream.Collectors;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa.Playlist;
import quentin.wwi.vertsys.javaee.jplaylist.songs.jpa.Song;

/**
 *
 * @author D070366
 */


public class PlaylistDTO {
    
    private String playlist_name;
    private List<SongDTO> songs;
    private String username;

    public PlaylistDTO(Playlist playList) {
        this.playlist_name = playList.getPlaylistName();
        List<Song> songs = playList.getSongs();
        
        this.songs = songs.stream().map((song) -> {
            return new SongDTO((song));
        }).collect(Collectors.toList());
        
        this.username = playList.getOwner().getUsername();
        
    }

    public String getPlaylist_name() {
        return playlist_name;
    }

    public void setPlaylist_name(String playlist_name) {
        this.playlist_name = playlist_name;
    }

    public List<SongDTO> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDTO> songs) {
        this.songs = songs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
   
    
    
    
    
    
    
        
}
