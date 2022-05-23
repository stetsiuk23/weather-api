package com.stetsiuk.weatherapi.repository.net

object MainData{
    const val apiKey = "fdf78754389f465ab1c6b6f82385515d"
    const val oneCallBaseUrl = "https://api.openweathermap.org"
    const val geocodingBaseUrl = "http://api.openweathermap.org"
    const val iconsUrl: String = "http://openweathermap.org/img/wn/"
    const val iconExtention = ".png"
}

object Units{
    const val standard: String = "standard"
    const val metric: String = "metric"
    const val imperial: String = "imperial"
}

object Lang {
    const val af: String = "af"
    const val al: String = "al"
    const val ar: String = "ar"
    const val az: String = "az"
    const val bg: String = "bg"
    const val ca: String = "ca"
    const val cz: String = "cz"
    const val da: String = "da"
    const val de: String = "de"
    const val el: String = "el"
    const val en: String = "en"
    const val eu: String = "eu"
    const val fa: String = "fa"
    const val fi: String = "fi"
    const val fr: String = "fr"
    const val gl: String = "gl"
    const val he: String = "he"
    const val hi: String = "hi"
    const val hr: String = "hr"
    const val hu: String = "hu"
    const val id: String = "id"
    const val it: String = "it"
    const val ja: String = "ja"
    const val kr: String = "kr"
    const val la: String = "la"
    const val lt: String = "lt"
    const val mk: String = "mk"
    const val no: String = "no"
    const val nl: String = "nl"
    const val pl: String = "pl"
    const val pt: String = "pt"
    const val pt_br: String = "pt_br"
    const val ro: String = "ro"
    const val ru: String = "ru"
    const val sv: String = "sv"
    const val se: String = "se"
    const val sk: String = "sk"
    const val sl: String = "sl"
    const val sp: String = "sp"
    const val es: String = "es"
    const val sr: String = "sr"
    const val th: String = "th"
    const val tr: String = "tr"
    const val ua: String = "ua"
    const val uk: String = "uk"
    const val vi: String = "vi"
    const val zh_cn: String = "zh_cn"
    const val zh_tw: String = "zh_tw"
    const val zu: String = "zu"
}

object Default{
    const val units: String = Units.metric
    const val lang: String = Lang.en
    const val limit: Long = 3
}
