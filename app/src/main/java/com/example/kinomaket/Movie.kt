package com.example.kinomaket

import androidx.annotation.DrawableRes

data class Movie(
    val id: Int,
    val title: String,
    val year: Int,
    val genres: List<String>,
    val ageRating: String,
    val rating: Float,
    val storyline: String,
    @param:DrawableRes val posterRes: Int,
    val cast: List<CastMember>
)

data class CastMember(
    val name: String,
    val photoColor: Int
)

object MovieRepository {
    val movies = listOf(
        Movie(
            id = 0,
            title = "Star Trek: Picard",
            year = 2020,
            genres = listOf("Action", "Adventure", "Drama"),
            ageRating = "16+",
            rating = 4.0f,
            storyline = "Set twenty years after the events of Star Trek: Nemesis, Picard follows the eponymous character as he leads a mission to help a group of Romulans, and must confront the ghosts of his past.",
            posterRes = R.drawable.poster_startrek,
            cast = listOf(
                CastMember("Patrick Stewart", 0xFF3A5A8C.toInt()),
                CastMember("Alison Pill", 0xFF8C3A5A.toInt()),
                CastMember("Isa Briones", 0xFF5A8C3A.toInt()),
                CastMember("Evan Evagora", 0xFF8C5A3A.toInt())
            )
        ),
        Movie(
            id = 1,
            title = "The Mandalorian",
            year = 2020,
            genres = listOf("Action", "Adventure", "Fantasy"),
            ageRating = "12+",
            rating = 4.5f,
            storyline = "The travels of a lone bounty hunter in the outer reaches of the galaxy, far from the authority of the New Republic.",
            posterRes = R.drawable.poster_mandalorian,
            cast = listOf(
                CastMember("Pedro Pascal", 0xFF3A5C8C.toInt()),
                CastMember("Carl Weathers", 0xFF5C3A3A.toInt()),
                CastMember("Gina Carano", 0xFF3A5C4A.toInt()),
                CastMember("Giancarlo Esposito", 0xFF5C5C3A.toInt())
            )
        ),
        Movie(
            id = 2,
            title = "The Witcher",
            year = 2019,
            genres = listOf("Action", "Adventure", "Fantasy"),
            ageRating = "14+",
            rating = 4.0f,
            storyline = "Geralt of Rivia, a solitary monster hunter, struggles to find his place in a world where people often prove more wicked than beasts.",
            posterRes = R.drawable.poster_witcher,
            cast = listOf(
                CastMember("Henry Cavill", 0xFF4A5C3A.toInt()),
                CastMember("Anya Chalotra", 0xFF5C3A5C.toInt()),
                CastMember("Freya Allan", 0xFF3A4A5C.toInt()),
                CastMember("Joey Batey", 0xFF5C4A3A.toInt())
            )
        ),
        Movie(
            id = 3,
            title = "Joker",
            year = 2019,
            genres = listOf("Crime", "Drama", "Thriller"),
            ageRating = "18+",
            rating = 5.0f,
            storyline = "In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society. He then embarks on a downward spiral of revolution and bloody crime.",
            posterRes = R.drawable.poster_joker,
            cast = listOf(
                CastMember("Joaquin Phoenix", 0xFF5C3A3A.toInt()),
                CastMember("Robert De Niro", 0xFF3A3A5C.toInt()),
                CastMember("Zazie Beetz", 0xFF3A5C3A.toInt()),
                CastMember("Frances Conroy", 0xFF5C5C3A.toInt())
            )
        ),
        Movie(
            id = 4,
            title = "Tenet",
            year = 2020,
            genres = listOf("Action", "Sci-Fi", "Thriller"),
            ageRating = "18+",
            rating = 3.5f,
            storyline = "Armed with only one word, Tenet, and fighting for the survival of the entire world, a Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            posterRes = R.drawable.poster_tenet,
            cast = listOf(
                CastMember("John David Washington", 0xFF3A4A5C.toInt()),
                CastMember("Robert Pattinson", 0xFF5C3A4A.toInt()),
                CastMember("Elizabeth Debicki", 0xFF4A5C3A.toInt()),
                CastMember("Kenneth Branagh", 0xFF5C4A3A.toInt())
            )
        ),
        Movie(
            id = 5,
            title = "Blade Runner 2049",
            year = 2017,
            genres = listOf("Action", "Drama", "Sci-Fi"),
            ageRating = "12+",
            rating = 4.5f,
            storyline = "Young Blade Runner K's discovery of a long-buried secret leads him to track down former Blade Runner Rick Deckard, who has been missing for thirty years.",
            posterRes = R.drawable.poster_bladerunner,
            cast = listOf(
                CastMember("Ryan Gosling", 0xFF3A3A5C.toInt()),
                CastMember("Harrison Ford", 0xFF5C3A3A.toInt()),
                CastMember("Ana de Armas", 0xFF3A5C4A.toInt()),
                CastMember("Robin Wright", 0xFF5C4A5C.toInt())
            )
        )
    )
}
