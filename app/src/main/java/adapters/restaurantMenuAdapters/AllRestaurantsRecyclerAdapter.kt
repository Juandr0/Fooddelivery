package adapters.restaurantMenuAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import classes.Restaurant
import com.bumptech.glide.Glide
import com.example.fooddeliveryproject.R
import fragment.restaurantMenu.*
import fragment.user.RestaurantFragment

class AllRestaurantsRecyclerAdapter(val context: RestaurantFragment, val restaurants: List<Restaurant>) :
    RecyclerView.Adapter<AllRestaurantsRecyclerAdapter.ViewHolder>() {


    //onClickListener setup
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_restaurants_list_item, parent, false)

        return ViewHolder(itemView, mListener)
    }//End of clickListener



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Takes the right restaurant from the list
        val restaurant = restaurants[position]
        //Assigns the right information to the restaurant
//        holder.restaurantHeaderImageView.setImageResource(restaurant.image)
        holder.restaurantNameView.text = restaurant.name
        holder.restaurantRatingView.text = restaurant.rating.toString()
        holder.restaurantDeliveryFeeView.text = "${restaurant.deliveryFee} kr"

        holder.itemView.setOnClickListener(object:  View.OnClickListener{
            override fun onClick(v: View?) {

                val activity = v!!.context as AppCompatActivity



                when(holder.restaurantNameView.text) {
                    "Brödernas Liljeholmen" ->{
                        val BrodernasMenuFragment = BrodernasMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, BrodernasMenuFragment)
                        transaction.commit()
                    }
                    "Max Liljeholmen" ->{
                        val MaxLiljeholmenMenuFragment= MaxLiljeholmenMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, MaxLiljeholmenMenuFragment)
                        transaction.commit()
                    }
                    "Liljeholmens Grill" ->{
                        val LiljeholmensGrillMenuFragment = LiljeholmensGrillMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, LiljeholmensGrillMenuFragment)
                        transaction.commit()
                    }
                    "McDonalds Liljeholmen" ->{
                        val McDonaldsLiljeholmenMenuFragment = McDonaldsLiljeholmenMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, McDonaldsLiljeholmenMenuFragment)
                        transaction.commit()
                    }
                    "O'Learys Liljeholmen" ->{
                        val OlearysMenuFragment = OlearysMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, OlearysMenuFragment)
                        transaction.commit()
                    }
                    "Indian Garden" ->{
                        val IndianGardenMenuFragment = IndianGardenMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, IndianGardenMenuFragment)
                        transaction.commit()

                    }
                    "Trattoria Grazie" ->{
                        val TrattoriaGrazieMenuFragment = TrattoriaGrazieMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, TrattoriaGrazieMenuFragment)
                        transaction.commit()
                    }
                    "Taco Bar" ->{
                        val TacoBarMenuFragment = TacoBarMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, TacoBarMenuFragment)
                        transaction.commit()
                    }
                    "Liljeholmens Pizzeria" ->{
                        val LiljeholmensPizzeriaMenuFragment = LiljeholmesnPizzeriaMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, LiljeholmensPizzeriaMenuFragment)
                        transaction.commit()
                    }
                    "Liljeholmens Pizzeria" ->{
                        val LiljeholmensPizzeriaMenuFragment = LiljeholmesnPizzeriaMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, LiljeholmensPizzeriaMenuFragment)
                        transaction.commit()
                    }
                    "Maxad Pizza" ->{
                        val MaxadPizzaMenuFragment = MaxadPizzaMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, MaxadPizzaMenuFragment)
                        transaction.commit()
                    }
                    "Hanami Hawaiian Poke & Sushi" ->{
                        val HanamiMenuFragment = HanamiMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, HanamiMenuFragment)
                        transaction.commit()
                    }
                    "Thai Rung" ->{
                        val ThaiRungMenuFragment = ThaiRungMenuFragment()
                        val transaction = activity.supportFragmentManager.beginTransaction().addToBackStack("ExploreFragment")
                        transaction.replace(R.id.fragment_container, ThaiRungMenuFragment)
                        transaction.commit()
                    }
                }


            }
        })

        //Glide for image
        Glide.with(context)
            .load(restaurant.image)
            .into(holder.restaurantHeaderImageView)




    }

    //counts how many restaurants there are in the list
    override fun getItemCount(): Int {
        return restaurants.size
    }


    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        //When a viewHolder is created, it finds the ImageView and textViews in our itemView
        var restaurantHeaderImageView = itemView.findViewById<ImageView>(R.id.restaurantHeaderImageVIew)
        var restaurantNameView = itemView.findViewById<TextView>(R.id.restaurantNameView)
        var restaurantRatingView = itemView.findViewById<TextView>(R.id.restaurantRatingView)
        var restaurantDeliveryFeeView = itemView.findViewById<TextView>(R.id.restaurantDeliveryFeeView)

        //init of the clicklistener that checks position
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }


}
