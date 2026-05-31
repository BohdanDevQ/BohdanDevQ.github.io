package com.own.bogdanpremium

/**
 * All user-facing copy in ONE place, grouped by screen. Edit the Polish text here and
 * every screen updates — the screens read their strings from this object.
 *
 * A few strings are intentionally English: the "Hey, girl!" hook, the "Bogdan Premium"
 * brand name, and the Tinder LIKE / NOPE / SUPER LIKE stamps. Change them too if you like.
 *
 * The correct name to verify against (Screen 2) lives in [AppConfig.CORRECT_NAME].
 */
object Strings {

    object Welcome {
        const val badge = "BOGDAN PREMIUM"
        const val title = "Hey, girl!"
        // Subtitle is rendered with "Bogdan Premium" bold + colored, so it's in 3 pieces.
        const val subtitlePrefix = "Zostałaś wybrana jako kandydatka do "
        const val brand = "Bogdan Premium"
        const val subtitleSuffix = ". Jeśli gotowa, klikaj w przycisk niżej!"
        const val tag = "✨ ekskluzywnie · zostało 1 miejsce ✨"
        const val cta = "Wskakuj!"
    }

    object NameVerify {
        const val title = "Podaj proszę swoje imię"
        const val subtitle = "W Bogdan Premium bardzo poważnie traktujemy tożsamość."
        const val fieldLabel = "Wpisz swoje imię…"
        const val cta = "Dalej"
        const val dialogTitle = "No jaaasne 🤨"
        const val wrongMessage =
            "Haha, chciałaś mnie sprawdzić 😂 Serio robię to pierwszy raz w życiu! " +
                "Robię to w środku nocy przed naszą randką 🌙"
        // Attempts counter — gets more dramatic as tries run out.
        const val attempts3 = "zostały 3 próby"
        const val attempts2 = "zostały 2, bez stresu 😅"
        const val attempts1 = "ostatnia próba. to twój villain origin story 💀"
        const val attemptsAutoFilled = "no dobra, ogarniam cię 🙄 po prostu naciśnij dalej ➡️"
    }

    object Appreciation {
        const val note =
            "Sporo ze sobą gadamy. No serio, mega dużo. Nie pamiętam, kiedy ostatnio " +
                "czułem się z kimś tak głęboko połączony. Jesteś świetną osobą i doceniam " +
                "każdą wiadomość, którą wymieniamy."
        const val next = "Dalej →"
        const val checklistTitle = "Jak myślisz, czemu cię lubię?"
        val reasons = listOf(
            "Bo zawsze cię rozśmieszam",
            "Bo nasze rozmowy o 3 nad ranem",
            "Bo naprawdę cię słucham",
            "Bo jestem szurnięty w uroczy sposób",
        )
        const val checklistHelper = "zaznacz wszystkie, napracowałem się nad nimi 😤"
    }

    object SurpriseVideo {
        const val title = "Mały drobiazg, który dla Ciebie zrobiłem 🎬"
        const val subtitle = "Bez ciśnienia. Po prostu robię z siebie idiotę o 2 w nocy."
        const val downloadTitle = "Akrowypady zdjęcia"
        const val downloadSubtitle = "Kliknij, aby pobrać · Drive"
        const val next = "Dalej →"
    }

    object Tinder {
        const val name = "Bogdan, 25"
        const val tagline = "Programista · 2 km stąd"
        const val aboutTitle = "O mnie"
        val aboutLines = listOf(
            "🎯 Szukam: mojej osoby (czyli ciebie)",
            "🚩→💚 zamieniłem swoje czerwone flagi na zielone",
            "☕ na bank zaproponuję kawę, a potem pogadamy 4 godziny",
            "👨‍💻 płynnie po kotlinie, słabiej w uczuciach (pracuję nad tym)",
            "📏 183 cm (apki kłamią, ja jestem szczery)",
            "🍝 robię jedno (1) danie z makaronu, ale robię je z pełną pewnością siebie",
        )
        const val nopeDialog = "😢 Na pewno??????"
        const val hint = "← nie · ★ super lajk · ♥ tak →"
        // Iconic Tinder stamps — kept in English on purpose.
        const val stampLike = "LIKE"
        const val stampNope = "NOPE"
        const val stampSuper = "SUPER LIKE"
    }

    object DateScience {
        const val introQuestion = "Jeśli dobrze rozumiem, nasze dzisiejsze spotkanie to randka? 😊"
        const val yes = "Tak! 🎉"
        const val no = "Nie…"
        const val sadDialogTitle = "💔"
        const val sadDialogMessage = "auć. dobra, udawajmy, że nie pytałem… (ale to randka, no nie? 🥺)"
        const val sadDialogConfirm = "no dobra 😅"
        const val chartTitle = "Prognoza kortyzolu na dziś"
        const val chartCaption = "nauka nie kłamie 🤓"
        const val chartBarBefore = "Przed randką 😰"
        const val chartBarDuring = "W trakcie 😌"
        const val whatToExpect = "Czego się spodziewać →"
        const val wheelTitle = "Czego się spodziewać po dzisiaj"
        const val wheelSpin = "Kręć!"
        val wheelSegments = listOf(
            "Zabawa",
            "Trochę cringe'u",
            "Słodkie momenty",
            "Całus",
            "Coś z białkiem",
            "Kwiaty 🌸",
        )
        const val next = "Dalej →"
    }

    object Subscribe {
        const val title = "Dziękuję, że tu dotarłaś!"
        const val subtitlePrefix = "Kliknij przycisk poniżej, żeby oficjalnie zasubskrybować "
        const val brand = "Bogdan Premium"
        const val subtitleSuffix = "."
        const val cta = "Subskrybuj ✨"
        const val ctaSubscribed = "Zasubskrybowano 💅"
        const val noRefunds = "(zwrotów brak)"
        const val callout =
            "Oczywiście, że zbieram wszystkie dane z Twoich wyborów!!!! " +
                "Tak łatwo mi nie uciekniesz 😈"
    }

    object Common {
        /** Step label for the progress indicator, e.g. "KROK 1 Z 5". */
        fun step(current: Int, total: Int): String = "KROK $current Z $total"
    }
}
