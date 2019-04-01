/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import quentin.wwi.vertsys.javaee.jplaylist.songs.jpa.Song;
import service.dataclasses.SongDTO;
import service.dataclasses.SongFacade;

/**
 *
 * @author D070366
 */
@Stateless
@Path("song")
public class SongFacadeREST extends AbstractFacade<Song> {
        
    
    @PersistenceContext(unitName = "default")
    private EntityManager em;
    
    @EJB
    SongFacade songFacade;

    public SongFacadeREST() {
        super(Song.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Song entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Song entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Song find(@PathParam("id") Long id) {
        return super.find(id);
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public SongDTO findDTO(@PathParam("id") Long id) {
        return songFacade.findSong(id);
    }
    
    
    //TODO: fix this 
    @GET
    @Path("find")
    @QueryParam("{search}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<SongDTO> searchDTO(@QueryParam("search") String search){
       return songFacade.searchAllSongs(search);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Song> findAll() {
        return super.findAll();
    }
    
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<SongDTO> findAllDTO() {
       return songFacade.findAll();
    }
    

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Song> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
