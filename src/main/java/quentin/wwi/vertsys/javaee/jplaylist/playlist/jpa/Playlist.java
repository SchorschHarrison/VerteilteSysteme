/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa;

import dhbwka.wwi.vertsys.javaee.jtodo.common.jpa.User;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author D070366
 */

@Entity
public class Playlist implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="playlist_ids")
    @TableGenerator(name = "playlist_ids", initialValue = 0, allocationSize = 50)
    private long id;
    
    @NotNull
    private String playlistName;
    
    //TODO: add owner attribute @ManyToOne @NotNull
    @NotNull(message="Playlist needs an owner")
    @ManyToOne
    User owner;
   
    
    //<editor-fold desc="Konstruktoren">
    public Playlist() {
    }

    public Playlist(String playlistName, User owner) {
        this.playlistName = playlistName;
        this.owner = owner;
    }
    //</editor-fold>

    
    //<editor-fold desc="Getter und Setter">
    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
    
        public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    
    //</editor-fold>

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


    
    
   
}
