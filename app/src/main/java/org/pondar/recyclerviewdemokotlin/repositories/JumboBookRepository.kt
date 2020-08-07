package org.pondar.recyclerviewdemokotlin.repositories

import org.pondar.recyclerviewdemokotlin.models.JumboBook


//object - This means we have a singleton class - so there is ONLY 1 instance of this and
//we cannot make new objects.
object JumboBookRepository {

    private var jumboBooks: MutableList<JumboBook> = mutableListOf()


    fun getData(): MutableList<JumboBook> {
        if (jumboBooks.size == 0)
            retrieveData()

        return jumboBooks

    }

    //just put mock data here - in reality it would be from a database
    private fun retrieveData() {
        jumboBooks.add(
            JumboBook(
                nr = 1,
                year = 1968,
                title = "Onkel Joakims trillioner",
                img = "j001"
            )
        )
        jumboBooks.add(
            JumboBook(
                nr = 2,
                year = 1968,
                title = "Onkel Joakims skattejagt",
                img = "j002"
            )
        )
        jumboBooks.add(JumboBook(nr = 3, year = 1969, title = "Anders And i knibe", img = "j003"))
        jumboBooks.add(JumboBook(nr = 4, year = 1969, title = "Anders And i topform", img = "j004"))
        jumboBooks.add(
            JumboBook(
                nr = 5,
                year = 1970,
                title = "Onkel Joakim Jorden Rundt",
                img = "j005"
            )
        )
    }


}