/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package quentin.wwi.vertsys.javaee.jplaylist.songs.web;

import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa.TaskStatus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.ebj.PlaylistBean;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa.Playlist;
import quentin.wwi.vertsys.javaee.jplaylist.songs.ejb.SongBean;
import quentin.wwi.vertsys.javaee.jplaylist.songs.jpa.Song;

/**
 *
 * @author D070366
 */

@WebServlet(urlPatterns="/app/songs/list/*")
public class PlaylistServlet extends HttpServlet{
    
    @EJB 
    private SongBean songBean;
    
    @EJB
    private UserBean userBean;
    
    @EJB
    private PlaylistBean playlistBean;

    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
        List<String> errors = new ArrayList<>();
        Playlist playlist = getRequestedPlaylist(req);
        
        if(playlist == null){
            errors.add("Playlist not found");
        }
        
        if(errors.isEmpty()){
            List<Song> songs = songBean.getSongsOfPlaylist(playlist);
            req.setAttribute("songs", songs);
            req.setAttribute("playlist", playlist);
            
            req.getRequestDispatcher("/WEB-INF/songs/song_list.jsp").forward(req, resp);
            
        }else{
            //TODO: reroute to new Playlist servlet (Maybe edit Playlistinfoservlet
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        
    }
    
    private Playlist getRequestedPlaylist(HttpServletRequest req){
       
        String playlistId = req.getPathInfo();
                
        if(playlistId == null){
            playlistId = "";
        }
        
        playlistId = playlistId.replace("/", "");
        
        Playlist playlist = null;
        
        try {
            playlist = playlistBean.findById(Long.parseLong(playlistId));
        } catch (Exception e) {
              
        }

        return playlist;
    }
    
    
    
}
