package kpfu.itis.valisheva.android_app.presentation.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kpfu.itis.valisheva.android_app.domain.entities.ShortCityWeather

class CityAdapter(
    private val list: List<ShortCityWeather>,
    private val itemClick: (Int) -> (Unit)
) : RecyclerView.Adapter<CityHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        return CityHolder.create(parent, itemClick)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
