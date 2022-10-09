package band.effective.marvelapp.data

import band.effective.marvelapp.R

data class HeroesInfo(
    val id: Int,
    val name: String
)

fun getImages(): List<HeroesInfo> {
    return listOf<HeroesInfo>(
        HeroesInfo(R.drawable.deadpool, "Deadpool"),
        HeroesInfo(R.drawable.iron_man, "Iron Man"),
        HeroesInfo(R.drawable.captain, "Captain America"),
        HeroesInfo(R.drawable.spider_man, "Spider Man"),
        HeroesInfo(R.drawable.strange, "Doctor Strange"),
        HeroesInfo(R.drawable.thor, "Thor"),
        HeroesInfo(R.drawable.thanos, "Thanos")
    )
}
