# KinoMaket

> Android-приложение — каталог фильмов с тёмной темой.  
> Учебный проект курса **Product Star**.

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![minSdk](https://img.shields.io/badge/minSdk-24-blue?style=for-the-badge)
![targetSdk](https://img.shields.io/badge/targetSdk-36-blue?style=for-the-badge)

---

## Скриншоты

### Телефон

| Список фильмов | Детальный экран |
|:-:|:-:|
| [![Список](https://github.com/DanielYaruta/KinoMaket/raw/main/screenshots/screen_phone_list.png)](https://github.com/DanielYaruta/KinoMaket/blob/main/screenshots/screen_phone_list.png) | [![Деталь](https://github.com/DanielYaruta/KinoMaket/raw/main/screenshots/screen_phone_detail.png)](https://github.com/DanielYaruta/KinoMaket/blob/main/screenshots/screen_phone_detail.png) |

### Планшет — Master-Detail

| Landscape | Portrait |
|:-:|:-:|
| [![Landscape](https://github.com/DanielYaruta/KinoMaket/raw/main/screenshots/screen_tablet_portrait.png)](https://github.com/DanielYaruta/KinoMaket/blob/main/screenshots/screen_tablet_portrait.png) | [![Portrait](https://github.com/DanielYaruta/KinoMaket/raw/main/screenshots/screen_tablet_landscape.png)](https://github.com/DanielYaruta/KinoMaket/blob/main/screenshots/screen_tablet_landscape.png) |

---

## Архитектура

**Single Activity + Fragments**, Navigation Component, ViewModel.

| Класс | Роль |
|---|---|
| `MainActivity` | Единственная Activity; реализует `MovieNavigator`; на планшете подписывается на `MoviesViewModel` |
| `MoviesViewModel` | Хранит `selectedMovieId: LiveData<Int>` — выбранный фильм на планшете |
| `MovieNavigator` | Интерфейс, через который фрагменты узнают режим (`isTwoPane`) без прямой зависимости на `MainActivity` |
| `MovieListFragment` | Список фильмов — `RecyclerView` + `GridLayoutManager` |
| `MovieDetailFragment` | Детализация фильма; получает `movieId` через аргумент `ARG_MOVIE_ID` |
| `MovieAdapter` | `ListAdapter<Movie>` + `DiffUtil` с ViewBinding; подсвечивает выбранную карточку в two-pane режиме |
| `Extensions.kt` | Общие расширения `Int.dp` / `Float.dp` для всего пакета |

---

## Навигация

- `nav_graph.xml` — граф с двумя destination-ами и action `action_list_to_detail`
- `NavHostFragment` + `FragmentContainerView` — контейнер на телефоне
- `NavController.navigate()` + `FragmentNavigatorExtras` — переход список → деталь
- Back stack управляется `NavController` — восстанавливается при поворотах автоматически
- На планшете выбор фильма публикуется через `MoviesViewModel`, `MainActivity` заменяет фрагмент в правой панели

---

## Shared Element Transition

Постер из карточки плавно «превращается» в постер на экране детализации:

- `transitionName = "movie_poster_${movie.id}"` задаётся в адаптере и фрагменте детали
- `FragmentNavigatorExtras` передаёт shared view в `NavController`
- `postponeEnterTransition()` / `startPostponedEnterTransition()` синхронизируют анимацию
- На планшете transition не используется (`if (!isTwoPane)`)

---

## Адаптивный Layout

| Экран | Список | Детализация |
|---|---|---|
| Телефон | 2 колонки, полный экран | Отдельный фрагмент в back stack |
| Планшет portrait (≥600dp) | 2 колонки, левая панель 40% | Правая панель 60%: постер сверху, текст снизу |
| Планшет landscape (≥600dp) | 2 колонки, левая панель 40% | Правая панель 60%: постер слева, текст справа |

**Квалификаторы ресурсов:**
- `layout/` — телефон
- `layout-sw600dp/` — планшет portrait
- `layout-sw600dp-land/` — планшет landscape

---

## Технологический стек

| Технология | Версия |
|---|---|
| Kotlin | — |
| Navigation Component | 2.8.5 |
| ViewModel / LiveData | lifecycle |
| RecyclerView | 1.3.2 |
| ViewBinding | — |
| Material Components (Material 3) | 1.10.0 |
