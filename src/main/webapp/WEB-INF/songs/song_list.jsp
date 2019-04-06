<%-- 
    Document   : song_list
    Created on : Mar 24, 2019, 5:06:29 PM
    Author     : D070366
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Playlist: ${playlist.playlistName}
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>



        <c:if test="${editable}">

            <div class="menuitem">
                <a href="<c:url value="/app/songs/song/new/?playlist_id=${playlist.id}"/>">Add Song</a>
            </div>

            <div class="menuitem">
                <a href="<c:url value="/app/playlist/edit/?playlist_id=${playlist.id}"/>">Edit Playlistinfo</a>
            </div>
        </c:if>




    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Beschreibung"/>


            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty songs}">
                <p>
                    Es wurden keine Songs gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.jplaylist.common.web.WebUtils"/>

                <table>
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Artist</th>
                            <th>Spotify Player</th>
                        </tr>
                    </thead>
                    <c:forEach items="${songs}" var="song">
                        <tr>



                            <c:if test="${editable}">
                                <td>
                                    <a href="<c:url value="/app/songs/song/?song_id=${song.id}&playlist_id=${song.playlist.id}" />">
                                        <c:out value="${song.title}"/>
                                    </a>
                                </td>
                                
                                
                            </c:if>

                            <c:if test="${!editable}">
                                <td>
                                    <c:out value="${song.title}"/>
                                </td>
                            </c:if>



                            <td>
                                <c:out value="${song.artist}"/>
                            </td>

                            <!-- //IDEA: if not empty -->
                            <td>
                                <iframe src="https://open.spotify.com/embed/track/${song.spotifyId}" width="300" height="80" frameborder="0" allowtransparency="true" allow="encrypted-media"></iframe>
                            </td>



                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>
