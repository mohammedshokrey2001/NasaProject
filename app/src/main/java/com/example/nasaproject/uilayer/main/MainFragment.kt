package com.example.nasaproject.uilayer.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasaproject.R
import com.example.nasaproject.databinding.FragmentMainBinding
import com.example.nasaproject.domain.AsteroidAppData
import com.example.nasaproject.uilayer.adapters.AstroidListAdapter
import com.example.nasaproject.utlities.Constants
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlin.math.log


class MainFragment : Fragment() {


      lateinit var binding :FragmentMainBinding
    private val viewModel :MainViewModel by lazy {
        ViewModelProvider(this,AppViewModelFactory(requireActivity().application))[MainViewModel::class.java]

    }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this


       viewModel.AppRepo.astroids.observe(viewLifecycleOwner, Observer {
            adapter(it)
        })

        viewModel.AppRepo.picOfTheDay.observe(viewLifecycleOwner, Observer {

            if (it==null) {
                imageOfDay("x")
            }
            else{
                imageOfDay(it.url)
            }
        })


        setHasOptionsMenu(true)

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

     }

    @SuppressLint("NotifyDataSetChanged")
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var x = 0
       when(item.itemId){
           R.id.show_week -> x = Constants.WEEK_CONST
           R.id.show_today ->  x = Constants.TODAY_CONST
           R.id.show_saved -> x = Constants.SAVED_CONST

       }
        viewModel.updateSelection(x).observe(viewLifecycleOwner, Observer {
           adapter(it)
        })

        return true
     }

   private fun imageOfDay(url:String){
       if (url.equals("x")){

           binding.activityMainImageOfTheDay.setImageResource(R.drawable.ic_help_circle)
//           Toast.makeText(this.context,"image is being load please wait",Toast.LENGTH_LONG).show()
       }
       else {
           Picasso.get()
               .load(url)
               .error(R.drawable.ic_help_circle)
               .fit()

               .into(binding.activityMainImageOfTheDay)
       }
   }

    private fun adapter(data:List<AsteroidAppData>){

        val adapter = AstroidListAdapter(data)
        val layoutManager = LinearLayoutManager(this.context)
        val decorate = DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL)

        if (data.isEmpty()){
            Toast.makeText(this.context,"PLEASE Check internet connection to load Asteroids data!!",Toast.LENGTH_LONG).show()

        }

        binding.asteroidRecycler.adapter = adapter
        binding.asteroidRecycler.layoutManager = layoutManager
        binding.asteroidRecycler.addItemDecoration(decorate)


        adapter.setOnItemClickListener(object :AstroidListAdapter.onItemClickListeaner{
            override fun onItemClick(position: Int) {
                this@MainFragment.findNavController().navigate(MainFragmentDirections.actionShowDetail(astroid = data[position]))
            }

        })

   }


      }
