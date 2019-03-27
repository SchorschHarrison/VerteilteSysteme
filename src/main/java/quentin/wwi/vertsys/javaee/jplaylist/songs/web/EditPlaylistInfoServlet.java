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
import jdk.nashorn.internal.ir.ForNode;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.ebj.PlaylistBean;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa.Playlist;
import quentin.wwi.vertsys.javaee.jplaylist.songs.jpa.Song;

/**
 *
 * @author D070366
 */
@WebServlet(urlPatterns = "/app/playlist/edit/*")
public class EditPlaylistInfoServlet extends HttpServlet {

    @EJB
    UserBean userBean;

    @EJB
    PlaylistBean playlistBean;

    @EJB
    ValidationBean validationBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Playlist playlist = this.getRequestedPlaylist(req);
        req.setAttribute("edit", playlist.getId() != 0);

        if (session.getAttribute("playlist_form") == null) {
            session.setAttribute("playlist_form", this.createPlaylistForm(playlist));
        }

        req.setAttribute("playlistid", req.getParameter("playlist_id"));
        req.getRequestDispatcher("/WEB-INF/playlist/playlist_info_edit.jsp").forward(req, resp);
        session.removeAttribute("playlist_form");
    }

    private Playlist getRequestedPlaylist(HttpServletRequest req) {
        Playlist playlist = new Playlist();
        playlist.setOwner(userBean.getCurrentUser());

        String playlistId = req.getParameter("playlist_id");

        if (playlistId == null) {
            playlistId = "";
        }

        try {
            Playlist existing = playlistBean.findById(Long.parseLong(playlistId));
            if (existing != null) {
                playlist = existing;
            }
        } catch (Exception e) {
        }

        return playlist;
    }

    private FormValues createPlaylistForm(Playlist playlist) {
        Map<String, String[]> values = new HashMap<>();

        values.put("playlist_owner", new String[]{
            playlist.getOwner().getUsername()
        });

        values.put("playlist_name", new String[]{
            playlist.getPlaylistName()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.savePlaylist(req, resp);
                break;
            case "delete":
                this.deletePlaylist(req, resp);
                break;
            default:
        }
    }

    private void savePlaylist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<String> errors = new ArrayList<>();
        String playlistName = req.getParameter("playlist_name");
        Playlist playlist = this.getRequestedPlaylist(req);
        checkAuth(errors, playlist);

        if (playlistName != null && !playlistName.trim().isEmpty()) {
            playlist.setPlaylistName(playlistName);
        } else {
            errors.add("Please enter a Playlist name");
        }

        validationBean.validate(playlist, errors);

        if (errors.isEmpty()) {
            playlist = playlistBean.update(playlist);
        }

        if (errors.isEmpty()) {
            //reroute to new Playlist

            resp.sendRedirect(WebUtils.appUrl(req, "/app/songs/list/" + playlist.getId() + "/"));
        } else {
            //reload page, no edit
            FormValues formValues = new FormValues();
            formValues.setValues(req.getParameterMap());
            formValues.setErrors(errors);
            HttpSession session = req.getSession();
            session.setAttribute("playlist_form", formValues);
            resp.sendRedirect(req.getRequestURI());
        }

    }

    private void deletePlaylist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<String> errors = new ArrayList<>();
        Playlist playlist = getRequestedPlaylist(req);
        checkAuth(errors, playlist);

        if (errors.isEmpty()) {
            playlistBean.delete(playlist);

        }

        if (errors.isEmpty()) {
            resp.sendRedirect(WebUtils.appUrl(req, "/app/dashboard/"));
        } else {
            //Do not delete! reload page
            FormValues formValues = new FormValues();
            formValues.setValues(req.getParameterMap());
            formValues.setErrors(errors);
            HttpSession session = req.getSession();
            session.setAttribute("playlist_form", formValues);
            resp.sendRedirect(req.getRequestURI());
        }

    }

    
    //checks if the currentuser is the owner of the playlist
    private void checkAuth(List<String> errors, Playlist playlist) {
        User user = userBean.getCurrentUser();
        if (!user.getUsername().equals(playlist.getOwner().getUsername())) {
            errors.add("you can only edit your own Playlists");
        }
    }

}
