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
import quentin.wwi.vertsys.javaee.jplaylist.songs.ejb.SongBean;
import quentin.wwi.vertsys.javaee.jplaylist.songs.jpa.Song;

/**
 *
 * @author D070366
 */

@Stateless
public class SongFacade {
    
    @EJB
    SongBean songBean;
    
    public List<SongDTO> findAll(){
        List<Song> songs = songBean.findAll();
        return songs.stream().map((song) -> {
            SongDTO songDTO = new SongDTO(song);
            return songDTO;
        }).collect(Collectors.toList());
    }
    
    public List<SongDTO> searchAllSongs(String search){
        List<Song> songs = songBean.searchAllSongs(search);
        return songs.stream().map((song) -> {
            SongDTO songDTO = new SongDTO(song);
            return songDTO;
        }).collect(Collectors.toList());
    }
    
    public SongDTO findSong(Long id){
        SongDTO songDTO = new SongDTO(songBean.findById(Long.parseLong(String.valueOf(id))));
        return songDTO;
    }
    
}
