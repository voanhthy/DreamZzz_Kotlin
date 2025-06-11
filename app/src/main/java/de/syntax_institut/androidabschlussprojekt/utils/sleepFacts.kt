package de.syntax_institut.androidabschlussprojekt.utils

val sleepFacts = listOf(
    // Allgemeine Schlaf-Fakten
    "Wusstest du, dass der Mensch etwa ein Drittel seines Lebens mit Schlafen verbringt?",
    "Schlaf ist für die körperliche und geistige Erholung unerlässlich.",
    "Während des Schlafs festigt dein Gehirn Erinnerungen.",
    "Schlafmangel kann deine Konzentration und Stimmung negativ beeinflussen.",
    "Ein regelmäßiger Schlaf-Wach-Rhythmus ist wichtig für einen gesunden Schlaf.",
    "Die meisten Erwachsenen brauchen 7-9 Stunden Schlaf pro Nacht.",
    "Babys schlafen oft 14-17 Stunden pro Tag.",
    "Der Schlaf ist essenziell für die Immunfunktion.",
    "Im Schlaf werden Wachstumshormone ausgeschüttet.",
    "Blaues Licht von Bildschirmen kann die Schlafqualität beeinträchtigen.",
    "Eine kühle Raumtemperatur (etwa 18°C) ist optimal zum Schlafen.",
    "Koffein und Alkohol können den Schlaf stören, auch Stunden nach dem Konsum.",
    "Nickerchen (Power Naps) von 20-30 Minuten können die Wachsamkeit verbessern.",
    "Schlaf ist wichtiger für die Gesundheit als Nahrung – du kannst länger ohne Essen als ohne Schlaf überleben.",

    // Fakten über Träume
    "Jeder Mensch träumt jede Nacht, auch wenn wir uns nicht immer daran erinnern.",
    "Die intensivsten Träume finden im REM-Schlaf statt.",
    "Träume können dir helfen, Emotionen zu verarbeiten.",
    "Albträume sind normale Träume, die Angst oder Furcht auslösen.",
    "Blind geborene Menschen träumen in Klängen, Gefühlen und Gerüchen, nicht in Bildern.",
    "Man kann nicht gleichzeitig schnarchen und träumen.",
    "Haustiere können auch träumen!",
    "Luzide Träume sind Träume, in denen du dir bewusst bist, dass du träumst, und den Traum manchmal steuern kannst.",
    "Träume spiegeln oft unsere täglichen Erfahrungen, Ängste und Wünsche wider.",
    "Manche Theorien besagen, dass Träume dem Gehirn helfen, Informationen zu sortieren und zu speichern.",
    "Etwa 50 % aller Menschen haben mindestens einmal in ihrem Leben einen luziden Traum – aber nur etwa 20 % erleben ihn regelmäßig.",
    "Im luziden Traum kannst du fliegen, Wände durchqueren oder mit Fantasiefiguren sprechen – und dir dabei bewusst sein, dass es ein Traum ist.",
    "Im Schlaflabor nachgewiesen: Luzide Träumer konnten während des REM-Schlafs bewusst ihre Augen nach bestimmten Mustern bewegen – so konnten Forscher erkennen, wann sie luzide waren.",
    "Einige Spitzensportler nutzen luzide Träume zum mentalen Training, um Bewegungsabläufe zu perfektionieren.",
    "Traumfiguren in luziden Träumen können sich „eigenständig“ verhalten, auch wenn man weiß, dass sie nur im eigenen Kopf entstehen – ein zentrales Thema der Klartraumforschung.",
    "Manche Menschen berichten, im luziden Traum Schmerz oder Geschmack zu spüren, obwohl diese Sinneseindrücke nur simuliert werden.",
    "Luzides Träumen kann helfen, Albträume zu überwinden – viele Klarträumer berichten, dass sie sich bewusst aus einem Albtraum befreien konnten.",

    // Kuriositäten & Historisches
    "Das längste Experiment ohne Schlaf dauerte über 11 Tage.",
    "Schlafentzug kann zu Halluzinationen führen.",
    "Vor der Erfindung des elektrischen Lichts hatten viele Menschen einen 'zweigeteilten Schlaf' mit einer Wachphase in der Mitte der Nacht.",
    "Manche Tiere (z.B. Delfine) können mit nur einer schlafenden Gehirnhälfte schlafen.",
    "Schlafwandeln tritt am häufigsten im Tiefschlaf auf.",
    "Giraffen schlafen nur etwa 30 Minuten bis 2 Stunden pro Tag – oft in kurzen Nickerchen.",
    "Wale und Delfine schlafen mit nur einer Gehirnhälfte, damit sie weiter atmen und sich orientieren können.",
    "Menschen, die polyphasisch schlafen, teilen ihren Schlaf auf mehrere kurze Phasen über den Tag.",
    "Im alten Japan war das Einschlafen bei der Arbeit ein Zeichen von Fleiß.",
    "Schlafparalyse kann beängstigend sein: Du bist wach, aber dein Körper bewegt sich nicht – oft begleitet von Halluzinationen.",
    "Träume bestehen aus bekannten Elementen: Gesichter, Orte und Geräusche stammen meist aus echten Erinnerungen.",
    "Menschen, die Traumtagebuch führen, erinnern sich häufiger und intensiver an ihre Träume.",
    "Während des REM-Schlafs ist das Gehirn fast so aktiv wie im Wachzustand, aber der Körper ist vollständig gelähmt."
)

fun getRandomSleepFact(): String {
    return sleepFacts.random()
}