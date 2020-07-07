package ru.home.collaborativeeducation.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.login_activity.*
import ru.home.collaborativeeducation.ui.MainActivity
import ru.home.collaborativeeducation.R
import ru.home.collaborativeeducation.ui.base.BaseActivity

class LoginActivity : BaseActivity(), DialogCallback {

    lateinit var dialog: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        login.setOnClickListener {
            VK.login(this, arrayListOf(VKScope.WALL, VKScope.PHOTOS))
        }

        continueWithoutAuth.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // TODO remove when work on project becomes active
        dialog = MaterialDialog(this)
            .cancelable(false)
            .title(R.string.dialog_title_work_is_suspended)
            .message(R.string.dialog_content_work_is_suspended)
            .positiveButton(R.string.text_ok, null, this)

        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(this@LoginActivity, getString(R.string.error_vk_login), Toast.LENGTH_SHORT).show()
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        dialog.dismiss()
        super.onDestroy()
    }

    override fun invoke(p1: MaterialDialog) {
        if (!dialog.isShowing) return

        dialog.dismiss()
        finish()
    }
}