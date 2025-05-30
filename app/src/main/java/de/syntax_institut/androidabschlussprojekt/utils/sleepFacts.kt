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

    // Kuriositäten & Historisches
    "Das längste Experiment ohne Schlaf dauerte über 11 Tage.",
    "Schlafentzug kann zu Halluzinationen führen.",
    "Vor der Erfindung des elektrischen Lichts hatten viele Menschen einen 'zweigeteilten Schlaf' mit einer Wachphase in der Mitte der Nacht.",
    "Manche Tiere (z.B. Delfine) können mit nur einer schlafenden Gehirnhälfte schlafen.",
    "Das Wort 'Schlaf' stammt vom altdeutschen Wort 'slāpan' ab.",
    "Schlafwandeln tritt am häufigsten im Tiefschlaf auf."
)

fun getRandomSleepFact(): String {
    return sleepFacts.random()
}