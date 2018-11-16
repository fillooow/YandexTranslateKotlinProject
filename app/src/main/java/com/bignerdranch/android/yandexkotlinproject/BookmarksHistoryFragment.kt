package com.bignerdranch.android.yandexkotlinproject


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_bookmarks_history.*
import kotlinx.android.synthetic.main.fragment_translate.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class BookmarksHistoryFragment : Fragment() {
    // singleton WAS object, it's created lazily when the first time it used. After that it will
    // be reused without creation
    val wikiApiService by lazy {
        WikiApiService.create()
    }

    // tracks the fetching activity. Synchronise data fetching and app (really?)
    var disposable: Disposable? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmarks_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText12.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                beginSearch(editText12.text.toString())
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }


    private fun beginSearch(srsearch: String) {
        if (!srsearch.equals(""))
        disposable = wikiApiService
                .hitCountCheck("query", "json", "search", srsearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result -> trump.text = "${result.query.searchinfo.totalhits} results found" }
                )
    }
}
