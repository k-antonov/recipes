# Recipes

- [Description](#description)
- [Technology](#technology)
- [Features](#features)
- [Database scheme](#database-scheme)

# Description

A client-server application with meal recipes.

# Technology

API: https://www.themealdb.com/api.php

- Clean Architecture (close)
- MVVM
- Single Activity
- LiveData
- OkHttp3
- Room
- Hilt

# Features
- [Search by category](#search-by-category)
- [Search by cuisine](#search-by-cuisine)
- [Add to favorites](#add-to-favorites)
- [Offline mode](#offline-mode)

### Search by category

Beef, chicken, pork, seafood, etc.

![search-by-category](https://user-images.githubusercontent.com/64361468/172686714-01248af1-449c-4e96-ac20-a9bc8958bbe8.gif)

### Search by cuisine

American, British, Mexican, etc.

![search-by-cuisine](https://user-images.githubusercontent.com/64361468/172687413-b142d47a-a9a2-4a60-bdcb-f36c5a7fb944.gif)

### Add to favorites

Recipes added to favorites are shown in the corresponding item.

![add-to-favorites](https://user-images.githubusercontent.com/64361468/172687687-7f81dd17-945a-45a0-a935-c0c26d2cf291.gif)

### Offline mode

When there is no Internet connection, cached recipes are available. When connection is established, you can swipe up to get updated information.

![offline-mode](https://user-images.githubusercontent.com/64361468/172688302-d48590b7-fdbd-492b-a265-7421dc47610f.gif)

# Database scheme

![image](https://user-images.githubusercontent.com/64361468/172689899-fad22afd-48a8-4a5a-9212-29f59af445b1.png)
