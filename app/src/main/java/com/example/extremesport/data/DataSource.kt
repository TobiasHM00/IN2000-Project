package com.example.extremesport.data

import com.example.extremesport.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import java.io.File

class DataSource {
    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    /*
    Date skrives på format YYYY-MM-DD, f.eks 2023-03-29.
    Offset representerer tidssonen du henter informasjon fra. I Norge er f.eks. dette +01:00.
     */
    suspend fun getSunrise(latitude: Double, longitude: Double, date: String, offset: String): SunriseData {
        val apiLink = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/sunrise/3.0/sun?lat=${latitude}&lon=${longitude}&date=${date}&offset=${offset}"
        val sunrise: SunriseData = client.get(apiLink) {
            headers {
                append("X-Gravitee-API-Key","b0285355-9b7b-44ea-a2f0-2fadb945792d")
            }
        }.body()
        return sunrise
    }

    suspend fun getNowcast(latitude: Double, longitude: Double): NowcastData {
        val apiLink =
            "https://gw-uio.intark.uh-it.no/in2000/weatherapi/nowcast/2.0/complete?lat=${latitude}&lon=${longitude}"
        val nowcast: NowcastData = client.get(apiLink) {
            headers {
                append("X-Gravitee-API-Key", "b0285355-9b7b-44ea-a2f0-2fadb945792d")
            }
        }.body()
        return nowcast
    }

    /*
    Er usikker på om altitude er over havet eller over bakken.
     */
    suspend fun getLocationForecast(altitude: Int, latitude: Double, longitude: Double): LocationForecastData {
        val apiLink = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/locationforecast/2.0/compact.json?altitude=${altitude}&lat=${latitude}&lon=${longitude}"
        val locationForecast: LocationForecastData = client.get(apiLink) {
            headers {
                append("X-Gravitee-API-Key","b0285355-9b7b-44ea-a2f0-2fadb945792d")
            }
        }.body()
        return locationForecast
    }

    suspend fun getOpenAddress(latitude: Double, longitude: Double, radius: Int): OpenAddressData {
        //Radius er vel egentlig unødvendig å ha som parameter, men kan kanskje være nyttig uansett, idk.
        val apiLink = "https://ws.geonorge.no/adresser/v1/punktsok?lat=${latitude}&lon=${longitude}&radius=${radius}"
        val openAddress: OpenAddressData = client.get(apiLink).body()
        return openAddress
    }

    suspend fun getLocationData() {
        val locationData: String = File("C:\\Users\\Marius Warlo\\AndroidStudioProjects\\IN2000-Prosjekt\\app\\src\\main\\java\\com\\example\\extremesport\\data\\Locations.json").readText(Charsets.UTF_8)
        println(locationData + "\n\n\n\n")
    }
}