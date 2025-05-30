package de.syntax_institut.androidabschlussprojekt.data.local.model.enums

import androidx.annotation.DrawableRes
import de.syntax_institut.androidabschlussprojekt.R

enum class ImageStyle(
    val titleResId: Int,
    @DrawableRes val drawableResId: Int
) {
    WATERCOLOR(R.string.style_watercolor, R.drawable.watercolor) {
        override fun description(): String = "gemalt im Aquarellstil. Ein sanftes, verträumtes Aquarellbild mit weichen Farben und fließenden Übergängen."
    },
    SKETCHY(R.string.style_sketchy, R.drawable.sketchy) {
        override fun description(): String = "Schwarz-weiße Bleistiftzeichnung mit feinen Linien, Schraffuren und detaillierten Schattierungen, wie eine realistische Künstler-Skizze auf Papier."
    },
    ABSTRACT(R.string.style_abstract, R.drawable.resource_abstract) {
        override fun description(): String = "Eine abstrakte Komposition mit unvorhersehbaren Formen, lebhaften Farben und kreativen Strukturen, die keine klare Realität darstellen."
    },
    GEOMETRIC(R.string.style_geometric, R.drawable.geometric) {
        override fun description(): String = "Bestehend aus klaren geometrischen Formen und Mustern, mit symmetrischen Strukturen und kräftigen Farben."
    },
    REALISM(R.string.style_realism, R.drawable.realism) {
        override fun description(): String = "Realistisch und detailgetreu dargestellt, mit feinen Schattierungen, realistischen Texturen und lebensechten Farben."
    },
    COMIC(R.string.style_comic, R.drawable.comic) {
        override fun description(): String = "Im Stil eines klassischen Comicbuchs, mit dicken schwarzen Umrissen, lebendigen Farben und einer dynamischen, ausdrucksstarken Darstellung."
    },
    RETRO(R.string.style_retro, R.drawable.retro) {
        override fun description(): String = "Im Vintage-Stil der 80er- oder 90er-Jahre, mit nostalgischen Farben, körnigen Texturen und einem ikonischen Design vergangener Jahrzehnte."
    },
    BAUHAUS(R.string.style_bauhaus, R.drawable.bauhaus) {
        override fun description(): String = ""
    },
    SURREALISM(R.string.style_surrealism, R.drawable.surrealism) {
        override fun description(): String = ""
    },
    IMPRESSIONISM(R.string.style_impressionism, R.drawable.impressionism) {
        override fun description(): String = "Impressionistisch gemalt, mit lockeren, sichtbaren Pinselstrichen und einer Atmosphäre, die Licht und Bewegung einfängt."
    },
    POPART(R.string.style_popart, R.drawable.popart) {
        override fun description(): String = "Inspiriert von der Pop-Art-Bewegung, mit kräftigen, kontrastreichen Farben, Punkten und ikonischen Designs, die an Andy Warhol erinnern."
    },
    MINIMALISM(R.string.style_minimalism, R.drawable.minimalism) {
        override fun description(): String = "chlicht und modern, mit reduzierten Formen, wenigen Farben und klaren Linien für eine minimalistische Ästhetik."
    };

    abstract fun description(): String
}