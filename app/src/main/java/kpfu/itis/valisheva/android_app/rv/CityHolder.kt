package kpfu.itis.valisheva.android_app.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kpfu.itis.valisheva.android_app.databinding.ItemCityBinding
import kpfu.itis.valisheva.android_app.models.City

class CityHolder(
    private val binding: ItemCityBinding,
    private val action: (Int) -> Unit
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: City) {
        with(binding) {
            tvName.text = item.name
            tvTemp.text = item.temp.toString()
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
