package quentin.wwi.vertsys.javaee.jplaylist.songs.web;


import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.web.WebUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import quentin.wwi.vertsys.javaee.jplaylist.songs.ejb.SongBean;
import quentin.wwi.vertsys.javaee.jplaylist.songs.jpa.Song;

@WebServlet(urlPatterns = "/app/songs/song/*")
public class SongEditServlet extends HttpServlet{
   
    @EJB
    SongBean songBean;
    
    @EJB
    UserBean userBean;
    
    @EJB
    ValidationBean validationBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        
        req.getRequestDispatcher("/WEB-INF/songs/song_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String action = req.getParameter("action");
       if(action == null){
           action = "";
       }
       
       switch(action){
           case "save": 
               this.saveSong(req, resp);
               break;
           case "delete":
               this.deleteSong(req, resp);
               break;
          
       }
    }
    
    private void saveSong(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        List<String> errors = new ArrayList<String>();
        
        String songArtist = req.getParameter("song_artist");
        String songTitle = req.getParameter("song_title");
        
        
        Song song = getRequestedSong(req);
        //check if imput is empty
        if(songArtist != null){
            song.setArtist(songArtist);
        }else{
            errors.add("Bitte gib einen Artist an");
        }
        
        if(songTitle != null){
            song.setTitle(songTitle);
        }else{
            errors.add("Bitte gib den Song Titel an, lol");
        }
        
        
        
        this.validationBean.validate(song, errors);
        
        if(errors.isEmpty()){
            this.songBean.update(song);
        }
        
        if(errors.isEmpty()){
             resp.sendRedirect(WebUtils.appUrl(req, "/app/songs/list/"));
        }else{
            //TODO: Rerender dialoge
            resp.getWriter().println(errors.get(0));
        }
        
        
    }
    
    private void deleteSong(HttpServletRequest req, HttpServletResponse resp){

        
        
    }
    
    private Song getRequestedSong(HttpServletRequest req){
        Song song = new Song();
        
        
        //maybe set plaaylist idk?
        
        String songId = req.getPathInfo();
        if(songId == null){
            songId = "";
        }
        
        if (songId.endsWith("/")) {
            songId = songId.substring(0, songId.length() - 1);
        }
        
        try {
            song = this.songBean.findById(Long.parseLong(songId));
        } catch (Exception e) {
        
        }
        return song;
    }
    
    
    
    
    
}