/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package quentin.wwi.vertsys.javaee.jplaylist.playlist.ebj;

import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.EntityBean;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa.Playlist;

/**
 *
 * @author D070366
 */
@Stateless
@RolesAllowed("app-user")
public class PlaylistBean extends EntityBean<Playlist, Long>{

    public PlaylistBean() {
        super(Playlist.class);
    }
    
    public PlaylistBean(Class<Playlist> entityClass) {
        super(entityClass);
    }
    
    public List<Playlist> findAllSortedByName(){
        return em.createQuery("SELECT p FROM Playlist p ORDER BY p.playlistName").getResultList();
    }
    
}
