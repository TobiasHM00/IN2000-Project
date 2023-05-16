package com.example.extremesport.view

import com.example.extremesport.model.*
import kotlinx.coroutines.flow.update

data class ESUiState(
    val sunrise: SunriseData? = null,
    val nowcast: NowcastData? = null,
    val locationForecast: LocationForecastData? = null,
    val openAdress: OpenAddressData? = null
) {
    fun getInfo(): RequirementsResult {
        //val nowcastData = nowcast?.properties?.timeseries?.get(0)?.data!!
        val locationForecastData = locationForecast?.properties?.timeseries?.get(0)?.data!!
        //val sunriseData = sunrise?.properties!!
        val openAddressData = openAdress?.adresser?.get(0)!!

        val summaryCode1 = locationForecastData.next_1_hours.summary.symbol_code
        val summaryCode6 = locationForecastData.next_6_hours.summary.symbol_code
        val summaryCode12 = locationForecastData.next_12_hours.summary.symbol_code
        val currentTemp = locationForecastData.instant.details.air_temperature
        val highTemp1 = locationForecastData.next_1_hours.details.air_temperature_max
        val lowTemp1 = locationForecastData.next_1_hours.details.air_temperature_min
        val highTemp6 = locationForecastData.next_6_hours.details.air_temperature_max
        val lowTemp6 = locationForecastData.next_6_hours.details.air_temperature_min
        val highTemp12 = locationForecastData.next_12_hours.details.air_temperature_max
        val lowTemp12 = locationForecastData.next_12_hours.details.air_temperature_min
        val windStrength = locationForecastData.instant.details.wind_speed
        val windDirection = locationForecastData.instant.details.wind_from_direction
        val openAddressName = openAddressData.adressenavn
        return RequirementsResult(summaryCode1, summaryCode6, summaryCode12, currentTemp, highTemp1, lowTemp1, highTemp6, lowTemp6, highTemp12, lowTemp12, windStrength, windDirection, openAddressName)
    }
}