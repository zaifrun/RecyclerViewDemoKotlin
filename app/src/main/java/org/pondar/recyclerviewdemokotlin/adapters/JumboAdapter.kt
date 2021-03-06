package org.pondar.recyclerviewdemokotlin.adapters


import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import org.pondar.recyclerviewdemokotlin.R
import org.pondar.recyclerviewdemokotlin.databinding.ListElementBinding
import org.pondar.recyclerviewdemokotlin.interfaces.UpdateCollection
import org.pondar.recyclerviewdemokotlin.models.JumboBook


//This class is our custom adapter - this is where most of the work is done.
class JumboAdapter(
    var jumboList: List<JumboBook>,
    var resources: Resources,
    var updateListener: UpdateCollection
) :
    RecyclerView.Adapter<JumboAdapter.JumboViewHolder>() {


    //The context refers to the ui parent so to speak
    private lateinit var context: Context

    //This is a set of the items we have in our collection
    private var favorites: MutableSet<String> = HashSet()
    private lateinit var binding: ListElementBinding

    //this method is returning the view for each item in the list
    //also something you must override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JumboViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_element, parent, false)
        context = parent.context
        binding = ListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // val itemBinding = RowPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JumboViewHolder(itemBinding = binding)
    }

    //this method is binding the data on the list - notice that this
    //overrides the same method in the RecyclerView.Adapter that we are extending
    override fun onBindViewHolder(holder: JumboViewHolder, position: Int) {
        holder.bindItems(jumboList[position], favorites, context, resources, updateListener)
    }

    //this method is giving the size of the list - this MUST be implemented as this
    //also overrides something from the RecyclerView Class
    override fun getItemCount(): Int {
        return jumboList.size
    }


    //the class is holding the actual UI elements - the .xml file to use for each element
    //comes from the R.layout.list_element from the onCreateViewHolder method

    class JumboViewHolder(private val itemBinding: ListElementBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItems(
            jumbo: JumboBook,
            favorites: MutableSet<String>,
            context: Context,
            resources: Resources,
            updateListener: UpdateCollection

        ) {

            //This refers to id's from the .xml file.
            itemBinding.listElementNr.text = jumbo.nr.toString()
            itemBinding.listElementTitel.text = jumbo.title
            itemBinding.listElementYear.text = jumbo.year.toString()


            //getting the image
            val resID = resources.getIdentifier(jumbo.img, "drawable", context.packageName)
            itemBinding.listElementImage.setImageResource(resID)


            //making the zoom window when we click on an image
            itemBinding.listElementImage.setOnClickListener {

                val builder = Dialog(context)
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
                builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val imageView = ImageView(context)
                imageView.maxWidth = 1100
                imageView.maxHeight = 1100
                imageView.adjustViewBounds = true
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP


                imageView.setImageResource(resID)

                builder.window?.setGravity(Gravity.CENTER)
                builder.addContentView(
                    imageView, LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )

                builder.window?.setGravity(Gravity.CENTER)
                //makes the dialog go away when we click on it
                imageView.setOnClickListener {
                    builder.dismiss()
                }

                builder.show()
            } //image click listener

            //makes sure the checkbox is click if the jumbobook is
            //in our collection
            if (favorites.contains(jumbo.nr.toString())) {
                itemBinding.checkbox.isChecked = true
                Log.d("adapter", "nr " + jumbo.nr + " is in favorites")
            } else
                itemBinding.checkbox.isChecked = false

            //this is called whenever we click on a checkbox
            itemBinding.checkbox.setOnClickListener {
                Log.d("adapter", "box clicked for jumbo nr : " + jumbo.nr)
                Log.d("state : ", itemBinding.checkbox.isChecked.toString())
                if (itemBinding.checkbox.isChecked) {
                    favorites.add(jumbo.nr.toString())
                } else {
                    favorites.remove(jumbo.nr.toString())
                }
                //we need to update the textview saying how many books we
                //have in the collection and this update should take place
                //in the MainActivity - that is why we use the interface here.
                updateListener.update(favorites.size)

            }

        }
    }


}