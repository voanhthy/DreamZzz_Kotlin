package de.syntax_institut.androidabschlussprojekt.data.local.model.enums
import de.syntax_institut.androidabschlussprojekt.R

enum class Settings(val labelResId: Int) {
    NAME(R.string.name),
    EMAIL(R.string.email),
    MEMBERSHIP(R.string.member_since),
    NOTIFICATIONS(R.string.notifications),
    DARK_MODE(R.string.dark_mode),
    LANGUAGE(R.string.language),
    ABOUT_APP(R.string.about_this_app),
    FEEDBACK(R.string.feedback),
    FAQ(R.string.faq)
}