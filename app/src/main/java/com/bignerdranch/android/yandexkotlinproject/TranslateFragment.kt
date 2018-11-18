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

    companion object {
        private val API_KEY: String =
                "trnsl.1.1.20170330T085156Z.928b6e6d5afb8d9a.32082885800f6b054b0b0ec2becc4adf884fb27a"
    }

    // singleton WAS object, it's created lazily when the first time it used. After that it will
    // be reused without creation
    val yandexApiService by lazy {
        YandexApiService.create()
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
        beginSearch("qwe")
    }

    private fun beginSearch(srsearch: String) {
        disposable = yandexApiService
                .translateText(API_KEY, "муравей", "ru-en", "plain")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result -> translated_text.text = "${result.text.get(0)} translated" }
                )
    }
}
