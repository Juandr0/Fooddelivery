package fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fooddeliveryproject.R


class TEMPORARY_MENU_FRAGMENT : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_t_e_m_p_o_r_a_r_y__m_e_n_u__f_r_a_g_m_e_n_t,
            container,
            false
        )
    }


}