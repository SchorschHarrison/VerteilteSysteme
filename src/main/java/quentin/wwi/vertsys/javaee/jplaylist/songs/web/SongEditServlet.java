package quentin.wwi.vertsys.javaee.jplaylist.songs.web;

import dhbwka.wwi.vertsys.javaee.jplaylist.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jplaylist.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.jplaylist.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.jplaylist.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.jplaylist.common.web.WebUtils;
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

@WebServlet(urlPatterns = "/app/songs/song/*")
public class SongEditServlet extends HttpServlet {

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
        
        //get playlist id (url)
        String playlistId = req.getParameter("playlist_id");

        
        req.setAttribute("edit", song.getId() != 0);

        if (session.getAttribute("song_form") == null) {
            session.setAttribute("song_form", this.createSongForm(song));
        }

        if (playlistId == null) {
            playlistId = "";
        }
        
        //call jsp
        req.setAttribute("playlist_id", playlistId);
        req.getRequestDispatcher("/WEB-INF/songs/song_edit.jsp").forward(req, resp);
        session.removeAttribute("song_form");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.saveSong(req, resp);
                break;
            case "delete":
                this.deleteSong(req, resp);
                break;

        }
    }
    
    //checks userinput and authorization, then saves to database
    private void saveSong(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<String> errors = new ArrayList<String>();

        String songArtist = req.getParameter("song_artist");
        String songTitle = req.getParameter("song_title");
        String songSpotifyId = req.getParameter("song_spotify_id");

        Song song = getRequestedSong(req);
        //checks if user is the owner of the song
        validationBean.checkSongAuth(song, userBean.getCurrentUser(), errors);
        //checkAuth(song, errors);
        
        //sets new title and artist
        song.setArtist(songArtist.trim());
        song.setTitle(songTitle.trim());
                
        song.setSpotifyId(songSpotifyId);

        //check if input is correct (not null & length >= 1
        this.validationBean.validate(song, errors);

        if (errors.isEmpty()) {
            this.songBean.update(song);
        }

        if (errors.isEmpty()) {
            //success! reroute to the playlists servlet
            resp.sendRedirect(WebUtils.appUrl(req, "/app/songs/list/" + song.getPlaylist().getId() + "/"));
        } else {
            //if error
            //reload Page but keep parameters
            FormValues formValues = new FormValues();
            formValues.setValues(req.getParameterMap());
            formValues.setErrors(errors);
            HttpSession session = req.getSession();
            session.setAttribute("song_form", formValues);
            String url = req.getRequestURI() + "?" + req.getQueryString();
            resp.sendRedirect(url);
        }

    }

    private void deleteSong(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<String> errors = new ArrayList<>();
        
        //find requested song and check if the user can edit the song
        Song song = this.getRequestedSong(req);
        validationBean.checkSongAuth(song, userBean.getCurrentUser(), errors);
        
        //checkAuth(song, errors);
        String playlistid = String.valueOf(song.getPlaylist().getId());

        if (errors.isEmpty()) {
            this.songBean.delete(song);
        }
        
        if(errors.isEmpty()){
            resp.sendRedirect(WebUtils.appUrl(req, "/app/songs/list/" + playlistid));
        }else{
            //reload page but keep parameter
            FormValues formValues = new FormValues();
            formValues.setValues(req.getParameterMap());
            formValues.setErrors(errors);
            HttpSession session = req.getSession();
            session.setAttribute("song_form", formValues);
            String url = req.getRequestURI() + "?" + req.getQueryString();
            resp.sendRedirect(url);
        }
        
    }
    
    //Finds the requested song if it already exists in database,
    //otherwhise creates new Song object
    private Song getRequestedSong(HttpServletRequest req) {
        Song song = new Song();

        String songId = req.getParameter("song_id");
        String playlistId = req.getParameter("playlist_id");

        if (songId == null) {
            songId = "";
        }

        if (playlistId == null) {
            playlistId = "";
        }

        try {
            Playlist playlist = playlistBean.findById(Long.parseLong(playlistId));
            song.setPlaylist(playlist);
        } catch (Exception e) {
        }

        try {
            song = this.songBean.findById(Long.parseLong(songId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return song;
    }
    
    
    //creates the Song form for jsp
    public FormValues createSongForm(Song song) {
        Map<String, String[]> values = new HashMap<String, String[]>();

        values.put("song_artist", new String[]{
            song.getArtist()
        });

        values.put("song_title", new String[]{
            song.getTitle()
        });
        
        values.put("song_spotify_id", new String[]{
            song.getSpotifyId()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);

        return formValues;
    }

    
    //checks if the current User is Owner of the playlist
    //moved to validation bean
    /*
    private void checkAuth(Song song, List<String> errors) {

        User user = userBean.getCurrentUser();
        if (!user.getUsername().equals(song.getPlaylist().getOwner().getUsername())) {
            errors.add("You can only edit your own songs");
        }

    }
*/

}
