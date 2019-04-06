package quentin.wwi.vertsys.javaee.jplaylist.songs.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa.Playlist;

/**
 *
 * @author D070366
 */

@Entity
@XmlRootElement
public class Song implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="song_ids")
    @TableGenerator(name = "song_ids", initialValue = 0, allocationSize = 50)
    private long id;
    
    
    @ManyToOne
    @NotNull(message = "Du musst den Song einer Playlist zuordnen")
    private Playlist playlist;
    
    ///IDEA: add Genre 
    
    
    
    @NotNull(message = "Bitte gib den Titel des Songs ein")
    @Size(min = 1, message = "Der Songtitel muss mindestens ein Zeichen haben")
    private String title;
    
    @NotNull(message = "Bitte gib den Interpreten an.")
    @Size(min = 1, message = "Der Künstler muss mindestens ein Zeichen haben")
    private String artist;
    
    @Column(length = 22)
    @Size(min = 22, max = 22, message = "ungültige spotify Id")
    private String spotifyId;

    
    //<editor-fold desc="Konstruktoren">
    public Song() {
    }
    
    
    public Song(String title, String artist){
        this.title = title;
        this.artist = artist;
        
    }
//</editor-fold>
    
    //<editor-fold desc="Getter und Setter lol" >
    
    
    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    
     
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    
    //</editor-fold>

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }
    
}
