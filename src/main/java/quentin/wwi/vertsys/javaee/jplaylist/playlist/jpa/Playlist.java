package quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa;

import dhbwka.wwi.vertsys.javaee.jtodo.common.jpa.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import quentin.wwi.vertsys.javaee.jplaylist.songs.jpa.Song;

/**
 *
 * @author D070366
 */

@Entity
@XmlRootElement
public class Playlist implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="playlist_ids")
    @TableGenerator(name = "playlist_ids", initialValue = 0, allocationSize = 50)
    private long id;
    
    @NotNull
    private String playlistName;
    
    @NotNull(message="Playlist needs an owner")
    @ManyToOne
    User owner;
    
    
    
    
    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Song> songs = new ArrayList<>();
   
    
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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
    
    
    
    //</editor-fold>

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


    
    
   
}
