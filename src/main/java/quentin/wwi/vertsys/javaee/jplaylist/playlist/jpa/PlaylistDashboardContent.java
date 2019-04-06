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

import dhbwka.wwi.vertsys.javaee.jplaylist.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jplaylist.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.jplaylist.dashboard.ejb.DashboardContentProvider;
import dhbwka.wwi.vertsys.javaee.jplaylist.dashboard.ejb.DashboardSection;
import dhbwka.wwi.vertsys.javaee.jplaylist.dashboard.ejb.DashboardTile;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.ebj.PlaylistBean;
import quentin.wwi.vertsys.javaee.jplaylist.songs.ejb.SongBean;

/**
 *
 * @author D070366
 */

@Stateless(name="playlists")
public class PlaylistDashboardContent implements DashboardContentProvider{

    
    @EJB 
    UserBean userBean;
    
    @EJB
    PlaylistBean playlistBean;
    
    @EJB
    SongBean songBean;
    
    
    @Override
    public void createDashboardContent(List<DashboardSection> sections) {
        DashboardSection section = new DashboardSection();
        
        User user = userBean.getCurrentUser();
        //List<Playlist> myPlaylists = playlistBean.findAllSortedByName();
        List<Playlist> myPlaylists = user.getPlaylists();
        
        for(Playlist playlist: myPlaylists){
            DashboardTile tile = createDashboardTile(playlist);
            section.getTiles().add(tile);
        }
        
        section.setLabel("My Playlists");
        
        sections.add(section);
        
        //Playlists of other Users        
        section = new DashboardSection();
        List<Playlist> otherPlaylists = playlistBean.findAllPlaylistsOfOtherUsers(user);
        
        for(Playlist playlist: otherPlaylists){
            DashboardTile tile = createDashboardTile(playlist);
            section.getTiles().add(tile);
        }
        
        section.setLabel("Playlists of other Users");
        
        sections.add(section);
        
    }
    
    
    private DashboardSection createDashboardSection(){
        return null;
    }
    
    private DashboardTile createDashboardTile(Playlist playlist){
        int amount = songBean.getSongsOfPlaylist(playlist).size();
        String href = "/app/songs/list/"+playlist.getId();
        
        DashboardTile tile = new DashboardTile();
        tile.setAmount(amount);
        tile.setLabel(playlist.getPlaylistName());
        tile.setHref(href);
        
        return tile;
    }
    
}
