package com.pingfangx.demo.retrofitdemo

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val container = findViewById<ViewGroup>(R.id.ll_conainer)
        addButton(container, "Call<>", View.OnClickListener {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.wanandroid.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val service = retrofit.create(GitHubService::class.java)
            service.callUser().enqueue(object : Callback<GitHubUser> {
                override fun onFailure(call: Call<GitHubUser>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<GitHubUser>, response: Response<GitHubUser>) {
                    this@MainActivity.toast(response.body().toString())
                }
            })
        })

        addButton(container, "Observable<>", View.OnClickListener {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            val service = retrofit.create(GitHubService::class.java)
            service.ObservableUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<GitHubUser> {
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: GitHubUser) {
                            this@MainActivity.toast("加载完成$t")
                        }

                        override fun onError(e: Throwable) {
                        }
                    })
        })
    }

    private fun addButton(viewGroup: ViewGroup, text: CharSequence, onClickListener: View.OnClickListener) {
        val btn = Button(viewGroup.context)
        btn.isAllCaps = false
        btn.text = text
        btn.setOnClickListener(onClickListener)
        viewGroup.addView(btn)
    }
}

class GitHubUser

interface GitHubService {
    @GET("users/pingfangx")
    fun callUser(): Call<GitHubUser>

    @GET("users/pingfangx")
    fun ObservableUser(): Observable<GitHubUser>
}