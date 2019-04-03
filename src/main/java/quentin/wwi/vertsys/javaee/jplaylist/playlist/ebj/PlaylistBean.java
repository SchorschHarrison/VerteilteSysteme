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
import dhbwka.wwi.vertsys.javaee.jtodo.common.jpa.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa.Playlist;

/**
 *
 * @author D070366
 */
@Stateless
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
    
    //Returns all Playlist where user 1= @param user
    public List<Playlist> findAllPlaylistsOfOtherUsers(User user){
     return em.createQuery("SELECT p FROM Playlist p WHERE NOT p.owner = :user")
             .setParameter("user", user).getResultList();
    }
    
    
    public List<Playlist> search(String search){
        return em.createQuery("SELECT p FROM Playlist p WHERE LOWER(p.playlistName) LIKE :search")
                .setParameter("search", "%"+search.toLowerCase()+"%")
                .getResultList();
    }
    
    public List<Playlist> getByIdByQueryToTestFuckedUpHTTPResponse(long id){
        return em.createQuery("SELECT p FROM Playlist p WHERE p.id = :id").setParameter("id", id).getResultList();
    }
    
}
