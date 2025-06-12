[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/tvZJeQ95)
# DreamZzz - dein Fenster zur Traumwelt

**Füge hier einen kurzen, knackigen Slogan ein, um deine App zu bewerben.**

DreamZzz ist deine digitale Begleitung für besseren Schlaf und intensivere Träume. Verwandle deine nächtlichen Abenteuer in visuelle Erinnerungen. Mit intelligentem Schlaftracking unterstützt dich die App dabei, die faszinierende Welt deiner Träume zu entschlüsseln und gleichzeitig deinen Schlaf langfristig zu optimieren.

DreamZzz ist für alle, die ihre Träume besser verstehen, ihren Schlaf optimieren oder einfach einen kreativen Zugang zu ihrer nächtlichen Reise suchen. Egal ob du nach mehr Klarheit, Entspannung oder Inspiration suchst - DreamZzz vereint innovative Technologie mit intuitivem Design, um Träume greifbar zu machen.



Für wen ist sie geeignet? Welches Problem löst sie? Was macht deine App anders/besser als andere Apps?
Vermeide es, hier allzusehr in technische Details zu gehen.


## Design
Füge hier am Ende die Screenshots deiner App ein.

<p>
  <img src="./img/screen1.png" width="200">
  <img src="./img/screen2.png" width="200">
  <img src="./img/screen3.png" width="200">
</p>

#### Minimalistisches Design
Die App setzt auf eine schlichte und moderne Ästhetik mit klaren Linien und einer ruhigen Farbgebung. Farbpalette: Grau, Schwarz und Weiß als Basisfarben, kombiniert mit Flieder als Akzentfarbe für Highlights und interaktive Elemente.

#### Typografie 
Andreas – markant und charakterstark für eine einprägsame Typografie.
Unterüberschriften: IBM Plex Mono Regular – klare, moderne Schrift für Struktur und Lesbarkeit.
Fließtexte: Manrope Regular – eine elegante, leicht lesbare Schrift für ein angenehmes Leseerlebnis.

#### Individuelle Tab Bar
Ermöglicht eine schnelle und intuitive Navigation zwischen den Hauptbereichen:
- Home: Übersicht und Eingabe neuer Träume.
- Galerie: Sammlung gespeicherter Träume mit KI-generierten Bildern.
- Profil: Einstellungen, persönliche Daten und Cloud-Synchronisation.
Klares Icon-Design für eine intuitive Bedienung.


## Features
Kernfunktionen:

- Traumaufzeichnung: Erfasse und speichere deine Träume ganz einfach per Texteingabe, damit sie nicht in Vergessenheit geraten.
- KI-Integration: Verwandle deine Traumbeschreibungen mithilfe künstlicher Intelligenz in faszinierende, individuell generierte Bilder.
- dynamische Traumgalerie: Erhalte eine visuelle Übersicht über deine gespeicherten Träume und ihre KI-generierten Bilder in einer interaktiven Galerie.
- Benutzerverwaltung und Cloud-Speicherung: Erstelle ein Benutzerkonto und sichere deine Träume zuverlässig in der Cloud – jederzeit und von überall abrufbar.
- Emotionen/Stimmung erfassen: Erfasse beim Speichern eines Traums deine Stimmung, um tiefere Einblicke in dein emotionales Erleben zu gewinnen.
- interaktiver Nachthimmel: Jeder eingetragene Traum erscheint als Stern am Himmel – erschaffe deine persönliche Traumgalaxie.
- mehrsprachige Unterstützung: Nutze die App in Deutsch und Englisch für ein intuitives und internationales Erlebnis.


## Technischer Aufbau

#### Projektaufbau

Die Architektur basiert auf MVVM (Model-View-ViewModel), ergänzt durch Repositories, Manager, ViewModels, Models und Views, um eine klare Trennung der Verantwortlichkeiten und eine einfache Wartbarkeit zu gewährleisten.

#### Datenspeicherung

<b>Firebase</b>        für die Benutzeranmeldung und -registrierung
                <br>-> synchronisiert nahtlos zwischen den Geräten und bietet Cloud-basierte Lösung, die den Zugang von überall sicherstellt
<br><b>Firestore</b>       zur Speicherung benutzerspezifischer Daten, um diese sicher und skalierbar zu verwalten
                <br>-> Flexibilität, schnelle Datenabfrage und Echtzeit-Synchronisierung zwischen Geräten
<br><b>Room</b>       für das lokale Speichern von Bildern und Medienelementen
                <br>-> Offline-Zugriff der Bilder, da die Daten direkt auf dem Gerät gespeichert werden
<br><b>DataStore</b>     speichert App-Einstellungen wie Dunkelmodus, Sprache und Benachrichigungseinstellungen, die lokal auf dem Gerät bleiben
                <br>-> einfache App-Einstellungen lokal auf dem Gerät speichern ohne externe Abhängigkeiten, wobei die Daten sofort nach der Änderung verfügbar sind

#### API Calls

Bilder generieren: DALL·E (OpenAI) zur Erstellung von Bildern basierend auf Traumbeschreibungen
Bilder analysieren und interpretieren: ChatGPT (OpenAI) zur Analyse und Interpretation von Traumbildern und -texten

## Ausblick
Beschreibe hier, wo die Reise nach deinem Praxisprojekt hin geht. Was möchtest du in Zukunft noch ergänzen?

- [ ] Geplantes Feature 1
- [ ] Geplantes Feature 2
- [ ] ...
