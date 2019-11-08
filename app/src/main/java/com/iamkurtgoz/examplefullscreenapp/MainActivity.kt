package com.iamkurtgoz.examplefullscreenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.InterstitialAd

class MainActivity : AppCompatActivity() {

    private lateinit var fullscreenAdView: InterstitialAd
    private lateinit var btnLoad: Button
    private lateinit var btnShow: Button
    private var isLoad: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Reklamı ekledik
        MobileAds.initialize(this)
        btnLoad = findViewById(R.id.btnLoad)
        btnShow = findViewById(R.id.btnShow)

        btnLoad.setOnClickListener {
            btnLoad.isEnabled = false
            loadAd()
            Toast.makeText(this, "Loading..", Toast.LENGTH_LONG).show()
        }

        btnShow.setOnClickListener {
            fullscreenAdView.show()
            isLoaded(false)
        }
    }

    private fun loadAd(){
        fullscreenAdView = InterstitialAd(applicationContext)
        fullscreenAdView.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        val adRequest: AdRequest
        if (BuildConfig.DEBUG) {//Eğer proje debug modunda ise reklamları test olarak başlatacağız.
            adRequest = AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build()
        } else {//Eğer release modunda ise normal başlatıyoruz.
            adRequest = AdRequest.Builder().build()
        }
        fullscreenAdView.loadAd(adRequest)
        fullscreenAdView.adListener = object : AdListener(){
            override fun onAdLoaded() {
                super.onAdLoaded()
                isLoaded(true)
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                super.onAdFailedToLoad(errorCode)
                isLoaded(false)
            }

            override fun onAdOpened() {
                super.onAdOpened()
                isLoaded(false)
            }

            override fun onAdClicked() {
                super.onAdClicked()
                isLoaded(false)
            }

            override fun onAdLeftApplication() {
                super.onAdLeftApplication()
                isLoaded(false)
            }

            override fun onAdClosed() {
                super.onAdClosed()
                isLoaded(false)
            }
        }
    }

    private fun isLoaded(isLoaded:Boolean){
        isLoad = isLoaded;
        if (!isLoaded){
            btnLoad.isEnabled = true
            btnShow.isEnabled = false
        } else{
            btnLoad.isEnabled = false
            btnShow.isEnabled = true
        }
    }
}
