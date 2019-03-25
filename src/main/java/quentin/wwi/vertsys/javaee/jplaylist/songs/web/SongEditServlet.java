package quentin.wwi.vertsys.javaee.jplaylist.songs.web;


import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.jtodo.common.web.WebUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.ebj.PlaylistBean;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa.Playlist;
import quentin.wwi.vertsys.javaee.jplaylist.songs.ejb.SongBean;
import quentin.wwi.vertsys.javaee.jplaylist.songs.jpa.Song;
import sun.invoke.empty.Empty;

@WebServlet(urlPatterns = "/app/songs/song/*")
public class SongEditServlet extends HttpServlet{
   
    @EJB
    SongBean songBean;
    
    @EJB
    UserBean userBean;
    
    @EJB
    ValidationBean validationBean;
    
    @EJB
    PlaylistBean playlistBean;
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        Song song = this.getRequestedSong(req);
        req.setAttribute("edit" ,  song.getId() != 0);
        
        if(session.getAttribute("song_form") == null){
            session.setAttribute("song_form" , this.createSongForm(song));
        }
        
        req.getRequestDispatcher("/WEB-INF/songs/song_edit.jsp").forward(req, resp);
        session.removeAttribute("song_form");
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
        if(songArtist != null && !songArtist.trim().isEmpty()){
            song.setArtist(songArtist);
        }else{
            errors.add("Bitte gib einen Artist an");
        }
        
        if(songTitle != null && !songTitle.trim().isEmpty()){
            song.setTitle(songTitle);
        }else{
            errors.add("Bitte gib den Song Titel an, lol");
        }
        
        Playlist playlist = playlistBean.findAllSortedByName().get(0);
        song.setPlaylist(playlist);
        
        
        
        this.validationBean.validate(song, errors);
        
        if(errors.isEmpty()){
            this.songBean.update(song);
        }
        
        if(errors.isEmpty()){
             resp.sendRedirect(WebUtils.appUrl(req, "/app/songs/list/"+playlist.getId()+"/"));
        }else{
            //TODO: Rerender dialoge
            
            FormValues formValues = new FormValues();
            formValues.setValues(req.getParameterMap());
            formValues.setErrors(errors);
            HttpSession session = req.getSession();
            session.setAttribute("song_form", formValues);
            resp.sendRedirect(req.getRequestURI());
        }
        
        
    }
    
    private void deleteSong(HttpServletRequest req, HttpServletResponse resp) throws IOException{

        Song song = this.getRequestedSong(req);
        
        this.songBean.delete(song);
        resp.sendRedirect(WebUtils.appUrl(req, "/app/songs/list/"));
    }
    
    private Song getRequestedSong(HttpServletRequest req){
        Song song = new Song();
        
        
        //maybe set plaaylist idk?
        //TODO: check
        //TODO: add playlist

        
        String songId = req.getPathInfo();
        if(songId == null){
            songId = "";
        }
        
       
        /*
        if (songId.endsWith("/")) {
            songId = songId.substring(0, songId.length() - 1);
        }
        
        if(songId.startsWith("/")){
            songId = songId.substring(1 , songId.length());
        }
*/
        
        songId = songId.replace("/", "");
        
        try {
            song = this.songBean.findById(Long.parseLong(songId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
        return song;
    }
    
    
    public FormValues createSongForm(Song song){
        Map<String, String[]> values = new HashMap<String, String[]>();
        
        values.put("song_artist", new String[]{
            song.getArtist()
        });
        
        values.put("song_title", new String[]{
            song.getTitle()
        });
        
        FormValues formValues = new FormValues();
        formValues.setValues(values);
          
        return formValues;
    }
    
    
    
    
}