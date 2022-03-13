package kpfu.itis.valisheva.android_app.rv

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kpfu.itis.valisheva.android_app.R
import kpfu.itis.valisheva.android_app.databinding.ItemCityBinding
import kpfu.itis.valisheva.android_app.helpers.UnitHelper
import kpfu.itis.valisheva.android_app.models.City
import kpfu.itis.valisheva.android_app.services.TemperatureService

class CityHolder(
    private val binding: ItemCityBinding,
    private val action: (Int) -> Unit
): RecyclerView.ViewHolder(binding.root){

    private lateinit var temperatureService: TemperatureService
    private lateinit var unitHelper: UnitHelper

    fun bind(item: City) {
        with(binding) {
            unitHelper = UnitHelper()
            temperatureService = TemperatureService()
            tvName.text = item.name

            tvTemp.text = unitHelper.addTempUnit(
                temperatureService.convertToDegrees(
                    item.temp
                )
            )
            tvTemp.setTextColor(
                temperatureService.changeTempColour(
                    item.temp
                )
            )
        }
        itemView.setOnClickListener {
            action(item.id)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            action: (Int) -> Unit
        ) = CityHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            action
        )
    }

}
