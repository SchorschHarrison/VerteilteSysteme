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
import quentin.wwi.vertsys.javaee.jplaylist.playlist.jpa.Playlist;
import service.dataclasses.PlaylistDTO;
import service.dataclasses.PlaylistFacade;

/**
 *
 * @author D070366
 */
@Stateless
@Path("playlist")
public class PlaylistFacadeREST extends AbstractFacade<Playlist> {

    @PersistenceContext(unitName = "default")
    private EntityManager em;
    
    @EJB
    PlaylistFacade playlistFacade;

    public PlaylistFacadeREST() {
        super(Playlist.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Playlist entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Playlist entity) {
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
    public Playlist find(@PathParam("id") Long id) {
        return super.find(id);
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public PlaylistDTO findDTO(@PathParam("id") Long id) {
        return playlistFacade.findById(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Playlist> findAll() {
        return super.findAll();
    }
    
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PlaylistDTO> findAllDTO() {
        return playlistFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Playlist> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    //fix this fuck
    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON})
    public List<PlaylistDTO> searchDTO(@QueryParam("search") String search){
        return playlistFacade.search(search);
    }
//    @GET
//    @PathParam("search")
//    @Produces(MediaType.APPLICATION_JSON)
//    public PlaylistDTO findPlaylist()
    
    //TODO: hide the pain
    private String hideThePain(){
        return "";
    }
}
