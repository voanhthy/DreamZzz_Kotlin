package de.syntax_institut.androidabschlussprojekt.data.local.model.enums

import androidx.annotation.DrawableRes
import de.syntax_institut.androidabschlussprojekt.R

enum class ImageStyle(
    val titleResId: Int,
    @DrawableRes val drawableResId: Int
) {
    WATERCOLOR(R.string.style_watercolor, R.drawable.watercolor),
    SKETCHY(R.string.style_sketchy, R.drawable.sketchy),
    ABSTRACT(R.string.style_abstract, R.drawable.resource_abstract),
    GEOMETRIC(R.string.style_geometric, R.drawable.geometric),
    REALISM(R.string.style_realism, R.drawable.realism),
    COMIC(R.string.style_comic, R.drawable.comic),
    RETRO(R.string.style_retro, R.drawable.retro),
    BAUHAUS(R.string.style_bauhaus, R.drawable.bauhaus),
    SURREALISM(R.string.style_surrealism, R.drawable.surrealism),
    IMPRESSIONISM(R.string.style_impressionism, R.drawable.impressionism),
    POPART(R.string.style_popart, R.drawable.popart),
    MINIMALISM(R.string.style_minimalism, R.drawable.minimalism)
}