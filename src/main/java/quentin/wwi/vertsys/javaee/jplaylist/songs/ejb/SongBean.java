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

import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.EntityBean;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
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
   
    
    
    public List<Song> getSongsOfPlaylist(long playlistId){
        //TODO: SQL
        return null;
    }
    
    
}
