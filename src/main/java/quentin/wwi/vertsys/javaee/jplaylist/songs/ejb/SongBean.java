/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package quentin.wwi.vertsys.javaee.jplaylist.songs.ejb;

import dhbwka.wwi.vertsys.javaee.jplaylist.common.ejb.EntityBean;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa.Playlist;
import quentin.wwi.vertsys.javaee.jplaylist.songs.jpa.Song;

/**
 *
 * @author D070366
 */

@Stateless
@RolesAllowed("app-user")
public class SongBean extends EntityBean<Song, Long>{

    public SongBean() {
        super(Song.class);
    }
   
    
    //find all songs of playlist (alternative to playlist.getSongs()
    public List<Song> getSongsOfPlaylist(Playlist playlist){
      //  return em.createQuery("SELECT s FROM Song s where s.id = :id").setParameter("id", playlistId).getResultList();
      return em.createQuery("SELECT s FROM Song s WHERE s.playlist = :playlist")
              .setParameter("playlist", playlist)
              .getResultList();
    }
    
    //search in playlist (searches for pattern in artist and title
    public List<Song> findInPlaylist(Playlist playlist, String search){
        return em.createQuery("SELECT s FROM Song s WHERE s.playlist = :playlist AND ( LOWER(s.title) LIKE :search OR LOWER(s.artist) LIKE :search)")
                .setParameter("playlist", playlist)
                .setParameter("search", "%"+search.toLowerCase()+"%")
                .getResultList();
    }
    
    //search all songs (searches for pattern in artist and title) for rest API
    public List<Song> searchAllSongs(String search){
        return em.createQuery("SELECT s FROM Song s WHERE (LOWER(s.title) LIKE :search OR LOWER(s.artist) LIKE :search)")
                .setParameter("search", "%"+search.toLowerCase()+"%")
                .getResultList();
    }
    
    
}
