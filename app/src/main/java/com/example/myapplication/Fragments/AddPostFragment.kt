package com.example.myapplication.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.AddpostAdapter
import com.example.myapplication.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [AddPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPostFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var recycler: RecyclerView? = null
    private lateinit var spin : Spinner
    private lateinit var post : LinearLayout
    var source: ArrayList<String>? = null
    var RecyclerViewLayoutManager: RecyclerView.LayoutManager? = null
    var adapter: AddpostAdapter? = null
    var HorizontalLayout: LinearLayoutManager? = null
    var RecyclerViewItemPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view : View = inflater.inflate(R.layout.fragment_add_post, container, false)
        post=view.findViewById(R.id.post)

        post.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment()
            )?.commit()
        }
//        recycler=view.findViewById(R.id.add_post_recycler_view)
//        var adaptor = profileAdaptor()
//        HorizontalLayout = LinearLayoutManager(
//            activity,
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )
//        val layoutManager = LinearLayoutManager(activity)
//        recycler!!.layoutManager = layoutManager
//        recycler!!.layoutManager = HorizontalLayout
//        recycler!!.adapter = adaptor


//        Spinner code
        spin = view.findViewById(R.id.spinner2)

        val objects = arrayOf<String?>(
            "Choose category", "Video", "Photo", "Video", "Photo"
        )

        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            activity!!,
            android.R.layout.simple_list_item_1,
            objects
        )

        spin.adapter = adapter
//        spin.setOnItemSelectedListener()


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddPostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

