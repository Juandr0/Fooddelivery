package classes

import android.content.res.Resources
import android.provider.Settings.Global.getString
import com.example.fooddeliveryproject.R

data class User (var name : String? = null,
                 var email : String? = null,
                 var address : String? = null,
                 var phoneNumber : Int? = null,
                 var type : String? = "user",
                 var uID : String? = null,
                 var lastOrderRestaurant : String? = null,
                 var lastOrder : List<String>? = null
                 )
{

}
