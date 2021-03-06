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
        Profil
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/dashboard.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">

        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>
        <!--<div class="menuitem">
            <a href="<c:url value="/app/tasks/list/"/>">Liste</a>
        </div> -->

        <!--<div class="menuitem">
            <a href="<c:url value="/app/tasks/task/new/"/>">Aufgabe anlegen</a>
        </div> -->

        <!--<div class="menuitem">
            <a href="<c:url value="/app/tasks/categories/"/>">Kategorien bearbeiten</a>
        </div> -->
    </jsp:attribute>
    
    <jsp:attribute name="content">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- Eingabefelder --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">
                    <label for="vorname">
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <input type="text" name="vorname" text="Vorname" value="${user_form.values["vorname"][0]}">


                    <label for="nachname">
                        Nachname:
                        <span class="required">*</span>
                    </label>
                    <input type="text" name="nachname" text="Nachname" value="${user_form.values["nachname"][0]}">


                    <label for="old_password">
                        Altes Passwort:
                        <span class="required">*</span>
                    </label>
                    <input type="password" name="old_password" text="Passwort" value="${user_form.values["oldpassword"][0]}">

                    <label for="new_password">
                        Neues Passwort:
                        <span class="required">*</span>
                    </label>
                    <input type="password" name="new_password" text="Passwort" value="${user_form.values["newpassword1"][0]}">

                    <label for="validate_password">
                        Neues Passwort wiederholen:
                        <span class="required">*</span>
                    </label>
                    <input type="password" name="validate_password" text="Passwort" value="${user_form.values["newpassword2"][0]}">


                    <%-- Button zum Abschicken --%>
                    <button class="icon-login" type="submit" name="action" value="save">
                        Speichern
                    </button>

                    
                </div>

                <c:if test="${!empty user_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${user_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
    </jsp:attribute>


</template:base>