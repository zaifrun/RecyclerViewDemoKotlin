package org.pondar.recyclerviewdemokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_main.*
import org.pondar.recyclerviewdemokotlin.adapters.JumboAdapter
import org.pondar.recyclerviewdemokotlin.interfaces.UpdateCollection
import org.pondar.recyclerviewdemokotlin.models.JumboBook
import org.pondar.recyclerviewdemokotlin.repositories.JumboBookRepository

//Notice we us an interface here - the UpdateCollection interface - see that file.
class MainActivity : AppCompatActivity(),UpdateCollection {

    private lateinit var jumboList: MutableList<JumboBook>
    private lateinit var adapter: JumboAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initially we have 0 in the collection
        antalisamlingTextView.text = resources.getString(R.string.antal_i_samlingen,0)
        updateUI()
    }


    private fun updateUI() {
        jumboList = JumboBookRepository.getData()
        //create a new recyclerview layout mananager
        jumbo_recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        //creating our adapter - the "this" parameter means that the update listener is implemented
        //by this class i.e. it is located in the MainActivity
        adapter = JumboAdapter(
            jumboList,resources,this
        )

        //now adding the adapter to recyclerview
        jumbo_recyclerview.adapter = adapter
    }

    //this is the update function called when we click on the checklist
    override fun update(nr: Int) {
        antalisamlingTextView.text = resources.getString(R.string.antal_i_samlingen,nr)

    }


}