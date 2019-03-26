<%-- 
    Copyright © 2019 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Dashboard test
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/dashboard.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/tasks/list/"/>">Liste</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tasks/task/new/"/>">Aufgabe anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tasks/categories/"/>">Kategorien bearbeiten</a>
        </div>
    </jsp:attribute>
        
    <jsp:attribute name="content">
        <div class="container">
            <form action="EditProfile" method="post" class="stacked">
                <div class="column">
                    <%-- Eingabefelder --%>
                    
                    <label for="j_vorname">
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <input type="text" name="j_vorname" text="Vorname">
                    
                    
                    <label for="j_nachname">
                        Nachname:
                        <span class="required">*</span>
                    </label>
                    <input type="text" name="j_nachname" text="Nachname">
                    

                    <label for="j_password">
                        Passwort:
                        <span class="required">*</span>
                    </label>
                    <input type="password" name="j_password" text="Passwort">
                    

                    <%-- Button zum Abschicken --%>
                    <button class="icon-login" type="submit">
                        Speichern
                    </button>
                </div>
            </form>
        </div>
    </jsp:attribute>
        
   
</template:base>