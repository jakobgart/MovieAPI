# movieapi

A movie is defined by

```yaml
{
imdbID: string,
title: string,
yearMade: integer,
description: string
}
```

## Implemented APIs

- `[GET]    /` - Get all movies.
  Optional query parameters:
  - **orderBy** - Order results by one of the movie attributes [imdbID, title, yearMade, description] (default: title)
  - **orderDirection** - The direction of the order [asc, desc] (default: desc)
  - **page** - Which page to return (default: 0)
  - **pageSize** - Size of each page (default: -1. Returns all results)
- `[GET]    /{id}` - Get a movie which *imdbID* matches *id*
- `[POST]   /` - Requires attached JSON file with information about a movie. The movie is added to the database
- `[PUT]    /{id}` - Requires attached JSON file with information about a movie. Movie which has *id* has its information replaced
- `[PATCH]  /{id}` - Requires attached JSON file with information about a movie. Movie which has *id* has its information replaced unless the given information is missing
- `[DELETE] /{id}` - Delete the movie with the given *id*