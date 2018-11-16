package com.bignerdranch.android.yandexkotlinproject


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_translate.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TranslateFragment : Fragment() {

    // singleton WAS object, it's created lazily when the first time it used. After that it will
    // be reused without creation
    val wikiApiService by lazy {
        WikiApiService.create()
    }

    // tracks the fetching activity. Synchronise data fetching and app (really?)
    var disposable: Disposable? = null

    lateinit var swapImageButton: ImageButton
    lateinit var sourceLanguageTV: TextView
    lateinit var requiredLanguageTV: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateViews()

    }

    private fun initiateViews() {

        sourceLanguageTV = source_language
        requiredLanguageTV = required_language

        swapImageButton = swap_image_button
        swapImageButton.setOnClickListener {
            swapLanguages()
        }
    }

    private fun swapLanguages(){
        var tempStr = sourceLanguageTV.text
        sourceLanguageTV.text = requiredLanguageTV.text
        requiredLanguageTV.text = tempStr
    }

    private fun beginSearch(srsearch: String) {
        disposable = wikiApiService
                .hitCountCheck("query", "json", "search", srsearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result -> translated_text.text = "${result.query.searchinfo.totalhits} results found" }
                )
    }
}
