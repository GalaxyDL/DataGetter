package com.galaxydl.datagetter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.text

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataGetter.INSTANCE.init(applicationContext)
        DataGetter.INSTANCE.get("http://192.168.115.61/index.php",
                User::class.java,
                this) { re: List<User>, e: Exception?
            ->
            e?.printStackTrace()
            re.forEach {
                text.append(it.user + "\n")
            }
        }
    }
}
