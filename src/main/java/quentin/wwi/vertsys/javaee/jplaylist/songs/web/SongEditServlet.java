package quentin.wwi.vertsys.javaee.jplaylist.songs.web;

import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.jtodo.common.jpa.User;
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

        String playlistId = req.getParameter("playlist_id");

        req.setAttribute("edit", song.getId() != 0);

        if (session.getAttribute("song_form") == null) {
            session.setAttribute("song_form", this.createSongForm(song));
        }

        if (playlistId == null) {
            playlistId = "";
        }

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

    private void saveSong(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<String> errors = new ArrayList<String>();

        String songArtist = req.getParameter("song_artist");
        String songTitle = req.getParameter("song_title");

        Song song = getRequestedSong(req);
        checkAuth(song, errors);
        //check if imput is empty
        if (songArtist != null && !songArtist.trim().isEmpty()) {
            song.setArtist(songArtist);
        } else {
            errors.add("Bitte gib einen Artist an");
        }

        if (songTitle != null && !songTitle.trim().isEmpty()) {
            song.setTitle(songTitle);
        } else {
            errors.add("Bitte gib den Song Titel an, lol");
        }

        //TODO: get actual playlist and remove the set part
//        Playlist playlist = playlistBean.findAllSortedByName().get(0);
//        song.setPlaylist(playlist);
        this.validationBean.validate(song, errors);

        if (errors.isEmpty()) {
            this.songBean.update(song);
        }

        if (errors.isEmpty()) {
            resp.sendRedirect(WebUtils.appUrl(req, "/app/songs/list/" + song.getPlaylist().getId() + "/"));
        } else {

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
        
        
        Song song = this.getRequestedSong(req);
        checkAuth(song, errors);
        String playlistid = String.valueOf(song.getPlaylist().getId());

        if (errors.isEmpty()) {
            this.songBean.delete(song);
        }
        
        if(errors.isEmpty()){
            resp.sendRedirect(WebUtils.appUrl(req, "/app/songs/list/" + playlistid));
        }else{
            FormValues formValues = new FormValues();
            formValues.setValues(req.getParameterMap());
            formValues.setErrors(errors);
            HttpSession session = req.getSession();
            session.setAttribute("song_form", formValues);
            String url = req.getRequestURI() + "?" + req.getQueryString();
            resp.sendRedirect(url);
        }
        
    }

    private Song getRequestedSong(HttpServletRequest req) {
        Song song = new Song();

        //TODO: check
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

    public FormValues createSongForm(Song song) {
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

    private void checkAuth(Song song, List<String> errors) {

        User user = userBean.getCurrentUser();
        if (!user.getUsername().equals(song.getPlaylist().getOwner().getUsername())) {
            errors.add("You can only edit your own songs");
        }

    }

}
