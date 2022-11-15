package com.example.nasaproject.uilayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaproject.R
import com.example.nasaproject.domain.AsteroidAppData
import com.example.nasaproject.uilayer.main.MainFragment

class AstroidListAdapter (val list:List<AsteroidAppData>) :RecyclerView.Adapter<AstroidListAdapter.ViewHolder>(){
  public var data = list
 private lateinit var mListiener : onItemClickListeaner


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view,parent,false)
        return ViewHolder(view,mListiener)

    }

    fun setOnItemClickListener(listeaner: onItemClickListeaner){
        mListiener = listeaner
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val dataItem = data[position]

        if (dataItem.isPotentiallyHazardous){
            holder.imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        }
        else{
            holder.imageView.setImageResource(R.drawable.ic_status_normal)

        }

        holder.textViewDate.text = dataItem.closeApproachDate.toString()
        holder.textViewName.text = dataItem.codename


        }



    override fun getItemCount(): Int {
       return data.size
    }



     class ViewHolder(itemView: View,listeaner: onItemClickListeaner):RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.card_view_imageView)
        val textViewName: TextView = itemView.findViewById(R.id.card_view_name)
        val textViewDate: TextView = itemView.findViewById(R.id.card_view_date)


         init {
             itemView.setOnClickListener{
                 listeaner.onItemClick(position = position)
             }
         }
     }

interface onItemClickListeaner{
    fun onItemClick(position: Int)
}

}



