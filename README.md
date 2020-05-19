# Chatify Chatserver

**FEATURE LIST (ONLY FOR DEVELOPERS)**

Legende:
[x] = finished
[ ] = unfinished
[o] = WIP (Work in progress)

*major:*
- [ ] login/register fenster verschieben
- [ ] applikation fenster verschieben
- [ ] login/register fenster nach login/register schließen
- [x] ~~application taskbar entfernen~~
- [x] ~~applikation close / minimize button~~
- [x] ~~user settings und server settings nicht gleichzeit öffnen können~~
- [ ] server settings nach erstellen eines servers automatisch schließen
- [ ] beim auswahl eines chatroom soll angezeigt werden in welchem man sich gerade befindet (actived)

- [x] ~~einheitliche Datenbank~~
- [ ] einheitlicher pfad für background video beim register / login

*minor:*
- [x] ~~login button "log me in", pressed styling~~
- [x] ~~register button "registrieren!", pressed styling~~
- [x] ~~application icon~~
- [x] ~~login/register video erneuern~~

*upcoming features:*
- [ ] tutorial
- [ ] server löschen
- [ ] username ändern
- [ ] user passwort ändern
- [ ] user email ändern
- [ ] server settings für bereits existierende server
- [ ] SOCKETS

- [ ] background video bei der applikation
- [ ] animations

**Beschreibung: Chatserver**

Team: Beckerle Florian,
	John Lim
      
Projekthintergrund: Einfacher Chat für zwei oder mehreren Usern

Chatify Chatserver ist ein Projekt aus der 3BHITM von Florian Beckerle und John Lim.
Es ist eine Anwendung, welches dazu dient eine Kommunikation zwischen zwei oder mehreren Benutzern aufzubauen, welche sich dann per Textnachrichten anschreiben können.

Chatify wird in Java geschrieben und verwendet für die Connections „Java Sockets“, welches ein Kommunikationsmechanismus zwischen zwei Computern mithilfe von TCP (Transmission Control Protocol) bereitstellt.

Die Benutzeroberfläche wird mithilfe von Gluon Scene Builder erstellt und basiert auf JavaFX, welches für ein einfaches und benutzerfreundliches UI sorgt.
Die Nachrichten sowie Userdaten und Interaktionen-Logs werden alle in Datenbanken gespeichert.

- Projekt noch in Bearbeitung 

Fortschritt (Mit Fortschrittsliste abgeglichen)

28.03.2020 (ChatifyAnimation, chatify_prototyp_v0_register)
- Erstellung eines Designs für Animationen, welche am Ende des Projektes bei der Login&Register Seite sowie auf der Applikation selbst implementiert wird.
- Erstellung eines Designs für die Registrierung.

08.04.2020 (chatify_v1.0_prototyp)
- Erstellung des ersten Prototyps mit View und Controller.
- Verbesserung durch implementierung von CSS und bessere Dateistruktur sowie Ordnerstruktur

19.04.2020 (chatify_v2.0_prototyp, chatify_v3.0_prototyp)
- Seperates Registrierungsfenster und Loginfenster wird zu einem Fenster fusioniert.
- Implementierung der Datenbank

01.05.2020 (chatify_v4.0_prototyp)
- Wechsel von Login auf Register möglich.
- Erstellung eines Users möglich
- Anzeige des Usernames auf der Applikation.

09.05.2020 (chatify_v5.0_prototyp)
- Überprüfungung for Register erneuert
- Überprüfungen für Login implementiert

- Login ermöglichen
- Login&Register Fenster schließen
- Roter / Grüner Border bei Input Felder
- AGB Check
- Chat Eingabe


Für nähere Details steht die Excel Datei "Fortschrittsliste" zur Verfügung.
