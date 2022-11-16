package adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryproject.R
import fragment.restaurantMenu.*
import fragment.user.RestaurantFragment

class RecyclerAdapterRestaurantFragment(val context : RestaurantFragment, val restaurantNameList : List<String> ) :
    RecyclerView.Adapter<RecyclerAdapterRestaurantFragment.ViewHolder>()
{

lateinit var thisContext : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        thisContext = parent.context
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_search, parent, false)
        return  ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurantTitle = restaurantNameList[position]
        holder.restaurantTitle.text = restaurantTitle
        holder.menuButton.setOnClickListener {
            fragmentDecider(restaurantTitle)
        }
    }

    override fun getItemCount(): Int {
       return restaurantNameList.size
    }

    //Swaps fragment depending on what restaurant name that is displayed next to the button
    fun fragmentDecider(restaurantName : String){


        when (restaurantName){
            //"Liljeholmens Pizzeria" -> setCurrentFragment()
            "Liljeholmens Grill" -> setCurrentFragment(LiljeholmensGrillMenuFragment(), null)
            "Trattoria Grazie" -> setCurrentFragment(TrattoriaGrazieMenuFragment(), null)
            "McDonalds Liljeholmen" -> setCurrentFragment(McDonaldsLiljeholmenMenuFragment(), null)
            "Thai Rung" -> setCurrentFragment(ThaiRungMenuFragment(), null)
            "O'Learys Liljeholmen" -> setCurrentFragment(OlearysMenuFragment(), null)
            "Indian Garden" -> setCurrentFragment(IndianGardenMenuFragment(), null)
            "Max Liljeholmen" -> setCurrentFragment(MaxLiljeholmenMenuFragment(), null)
            "Hanami Hawaiian Poke & Sushi" -> setCurrentFragment(HanamiMenuFragment(), null)
            "Taco Bar" -> setCurrentFragment(TacoBarMenuFragment(), null)
            "Maxad Pizza" -> setCurrentFragment(MaxadPizzaMenuFragment(), null)
            "Brödernas Liljeholmen" -> setCurrentFragment(BrodernasMenuFragment(), null)
        }


    }



    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val menuButton : Button = itemView.findViewById(R.id.serachRecycler_goToMenuButton)
        val restaurantTitle : TextView = itemView.findViewById(R.id.serachRecycler_restaurantTitleView)
    }


    open fun setCurrentFragment(fragment : Fragment, bundle: Bundle?){

        val fragmentManager = thisContext as AppCompatActivity
        fragment.arguments = bundle
        fragmentManager.supportFragmentManager.beginTransaction().addToBackStack("RestaurantFragment")
        .replace(R.id.fragment_container, fragment)
        .commit()
    }
}