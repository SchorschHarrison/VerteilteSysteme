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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.ebj.PlaylistBean;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa.Playlist;

/**
 *
 * @author D070366
 */

@Stateless
public class PlaylistFacade {
    
    @EJB
    PlaylistBean playlistBean;
    
    public List<PlaylistDTO> findAll(){
        List<Playlist> playlists = playlistBean.findAll();
        return playlists.stream().map((playlist) -> {
            return new PlaylistDTO((playlist));
        }).collect(Collectors.toList());
    }
    
}
