<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Playlistinfo bearbeiten
            </c:when>
            <c:otherwise>
                Playlist anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_edit.css"/>" />
    </jsp:attribute>



    <jsp:attribute name="menu">


        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>



        <c:if test="${not empty playlistid}">
            <div class="menuitem">
                <a href="<c:url value="/app/songs/list/${playlistid}"/>">Zurück</a>
            </div>
        </c:if>

        <c:if test="${empty playlistid}">
            <div class="menuitem">
                <a href="<c:url value="/app/dashboard/"/>">Zurück</a>
            </div>
        </c:if>



    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="playlist_owner">owner:</label>
                <div class="side-by-side">
                    <input type="text" name="playlist_owner" value="${playlist_form.values["playlist_owner"][0]}" readonly="readonly">
                </div>

                <label for="playlist_name">name:</label>
                <div class="side-by-side">
                    <input type="text" name="playlist_name" value="${playlist_form.values["playlist_name"][0]}">
                </div>


                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty playlist_form.errors}">
                <ul class="errors">
                    <c:forEach items="${playlist_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>