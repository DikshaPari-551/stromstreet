package com.example.myapplication.Fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.io.ByteArrayInputStream
import java.io.InputStream


class AddPostFragment : Fragment() {


    private var param1: String? = null
    private var param2: String? = null
    private var recycler: RecyclerView? = null
    private lateinit var spin : Spinner
    private lateinit var img : ImageView
    private lateinit var postBackButton : ImageView

//    lateinit var mBitmap: Bitmap

    private lateinit var post : LinearLayout
    var source: ArrayList<String>? = null
    var RecyclerViewLayoutManager: RecyclerView.LayoutManager? = null
//    var adapter: AddpostAdapter? = null
    var HorizontalLayout: LinearLayoutManager? = null
    var RecyclerViewItemPosition = 0

//    fun newInstance(b: Bitmap) {
//        mBitmap = b
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view : View = inflater.inflate(R.layout.fragment_add_post, container, false)
        post=view.findViewById(R.id.post)
        img = view.findViewById(R.id.image1)
        postBackButton = view.findViewById(R.id.post_back_button)

        var imageData: Bitmap? = arguments?.getParcelable("BitmapImage")
        if(imageData!=null){
            img.setImageBitmap(imageData)
        }
//        val strtext = arguments?.getString("edttext")
//        val b: Bitmap? = StringToBitMap(strtext)
//        img.setImageBitmap(b)



//        if (strtext != null) {
//            Log.d("Add Post Fragment :-",strtext)
//        }

        postBackButton.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment()
            )?.commit()
        }
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

    fun StringToBitMap(image: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(image, Base64.DEFAULT)
            val inputStream: InputStream = ByteArrayInputStream(encodeByte)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.message
            null
        }
    }

}

