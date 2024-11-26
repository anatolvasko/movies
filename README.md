# movies
Movies Catalog
To run the application, you need to create the variable TMDB_ACCESS_TOKEN in the gradle.properties file. Example:
TMDB_ACCESS_TOKEN = your_access_token_from_themoviedb_org


List of improvements or comments on the project:
1)Add UI and unit tests.
2)Improve the implementation of Pagination. The current implementation implies that the movie-presentation module knows about movie-data. Ideally, the viewModel should communicate with the movie-domain module through a UseCase, and the movie-domain should interact with the movie-data layer using a repository interface. Since the current Paging3 implementation does not solve this issue out of the box, I decided to leave it as is. If necessary, a custom PagingSource can be implemented.
