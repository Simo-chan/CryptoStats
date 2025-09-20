# CryptoStats ([Google Play link]([https://appgallery.huawei.com/app/C112123737](https://play.google.com/store/apps/details?id=com.simochan.cryptostats)))
-is a simple cryptocurrency tracker for everyday use, that was build using Kotlin, Coroutines, Ktor and etc. (full list down below)


## Overview

- **<ins>Main Screen</ins>**
  
  After :dizzy:**splash screen** and custom built :boom:**shimmer loading effect**, the main screen presents us the list of top 100 crypto coins by market capitalization using [CoinCap API](https://coincap.io/) as data source. Also on the screen a floating button appears as you scroll down, when pressed it takes you back to the top of the list, so you don't have to scroll up yourself thus saving approximately 0.86 seconds of your life.

![Main Screen demo](https://github.com/user-attachments/assets/1b6b86ba-d1ca-4dfa-aebc-1fc14f9aa7fb)


On the same screen there's a theme change button that allows you to choose between Light and Dark modes, which will persist across configuration changes and app process death by saving your preferences in DataStore. You could also notice :dizzy:**fabilous** custom bult **animation** when the button is triggered.

![Theme Change Demo](https://github.com/user-attachments/assets/e9fb60d4-5cc8-48a3-a82d-51a75a8e0e6a)


Next, if you save at least one coin to favorites a second list will appear on top of the Main screen and show all of your favorite crypto. This feature is implemented with Room :smiling_face_with_three_hearts:.

![Add to favorites demo](https://github.com/user-attachments/assets/d4af096e-3a46-49fd-8363-3411e5b526b8)


If for some reason you have no internet connection :skull: or something went wrong on the server, you can first enjoy :100: nice lottie animation and then try to load data again.

![No internet demo](https://github.com/user-attachments/assets/47844d48-9aa7-4224-b2ea-2920d09bbdda)


- **<ins>Details screen</ins>**

Now we are approaching the crown jewel of the app and that is the beautiful and shining :sparkling_heart:chart graph:sparkling_heart: built from scratch using Canvas API. The graph is drawn with wonderful animation and shows relevant price history of a coin for either 6h, 12h or 24h period. You can also, using drag gesture, see more specific price at a given hour. Apart from the graph there's also a star button which will add the coin to the favorite list when pressed and on the bottom of the screen the coin's market cap and 24h price change are shown.

![Graph demo](https://github.com/user-attachments/assets/d938fe16-a1ac-49b2-b089-d39676c32838)


- **<ins>Search screen</ins>**

On the search screen you can search for a specific coin so you don't have to do it manually. The search bar has a convinient button to clear the search. If no result is found you'll be met with another nice lottie animation that also moves with consideration of available space, so it's not accidentally hidden behind the keyboard. 

![SearchScreen demo](https://github.com/user-attachments/assets/31bcabc8-20b5-4fe5-b4c9-9ad3137704c1)


And that's about it.

**Full list of libraries/technologies that were used:**
- Kotlin
- Jetpack Compose
- Coroutines
- Coroutines Flow
- Compose Navigation
- Room
- Ktor
- Koin
- Lottie Compose
- Splash screes
- Data store
- MVI
- Clean architecture


