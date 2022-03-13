package kpfu.itis.valisheva.android_app.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kpfu.itis.valisheva.android_app.models.City

class CityAdapter(
    private val list: List<City>,
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
