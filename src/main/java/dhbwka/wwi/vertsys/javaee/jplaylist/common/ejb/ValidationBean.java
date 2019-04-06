/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jplaylist.common.ejb;

import dhbwka.wwi.vertsys.javaee.jplaylist.common.jpa.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa.Playlist;
import quentin.wwi.vertsys.javaee.jplaylist.songs.jpa.Song;

/**
 * Kleine EJB, die dafür genutzt werden kann, die Werte einer Entity zu
 * validieren, bevor diese gespeichert wird. Zwar validiert der Entity Manager
 * die Bean beim Speichern ebenfalls, da das aber erst am Ende der Transaktion
 * erfolgt, ist es schwer, rechtzeitig darauf zu reagieren.
 */
@Stateless
public class ValidationBean {
    
    @Resource
    Validator validator;
    
    /**
     * Wertet die "Bean Validation" Annotationen des übergebenen Objekts aus
     * (@Min, @Max, @Size, @NotNull, …) und gib eine Liste der dabei gefundenen
     * Fehler zurück.
     * 
     * @param <T>
     * @param object Zu überprüfendes Objekt
     * @return Liste mit Fehlermeldungen
     */
    public <T> List<String> validate(T object) {
        List<String> messages = new ArrayList<>();
        return this.validate(object, messages);
    }
    
    /**
     * Wertet die "Bean Validation" Annotationes des übergebenen Objekts aus
     * und stellt die gefundenen Meldungen in messages.
     * 
     * @param <T>
     * @param object Zu überprüfendes Objekt
     * @param messages List mit Fehlermeldungen
     * @return Selbe Liste wie messages
     */
    public <T> List<String> validate(T object, List<String> messages) {
        Set<ConstraintViolation<T>> violations = this.validator.validate(object);
        
        violations.forEach((ConstraintViolation<T> violation) -> {
            messages.add(violation.getMessage());
        });
        
        return messages;
    }
    
    public boolean checkPlaylistAuth(Playlist playlist, User user, List<String> messages){
        if(!playlist.getOwner().getUsername().equals(user.getUsername())){
            messages.add("You can only edit your own playlists");
            return true;
        }
        return false;
    }    
    
    public boolean checkSongAuth(Song song, User user, List<String> messages){
        if(!song.getPlaylist().getOwner().getUsername().equals(user.getUsername())){
            messages.add("You can only edit your own songs");
            return true;
        }
        return false;
    }
    
}
