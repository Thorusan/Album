package com.example.album.ui.photo

import android.app.ApplicationErrorReport
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.album.R
import com.example.album.base.AppPreferences
import com.example.album.common.GlideApp
import com.example.album.datamodel.AlbumData
import com.example.album.datamodel.ListsData
import com.example.album.datamodel.PhotoData
import com.example.album.datamodel.UserData
import kotlinx.android.synthetic.main.activity_photo.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class PhotoActivity : AppCompatActivity() {
    @BindView(R.id.img_photo_details)
    lateinit var imgPhotoDetails: ImageView
    @BindView(R.id.fullscreen_content_controls)
    lateinit var fullScreenContentControls: LinearLayout

    @BindView(R.id.text_photo_title)
    lateinit var textPhotoTitle: TextView
    @BindView(R.id.text_album_title)
    lateinit var textAlbumTitle: TextView
    @BindView(R.id.text_user)
    lateinit var textUser: TextView

    private lateinit var photoDetails: PhotoData
    private lateinit var album: AlbumData
    private lateinit var user: UserData.User

    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar
        imgPhotoDetails.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private val mShowPart2Runnable = Runnable {
        supportActionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }
    private var mVisible: Boolean = true
    private val mHideRunnable = Runnable { show() }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI.
     */
    private val mDelayHideTouchListener = View.OnTouchListener { _, _ ->
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS)
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        ButterKnife.bind(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        mVisible = true

        registerListeners()

        getPhotoDetails()
        loadPhotoDetails()
    }

    @Override
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getPhotoDetails() {
        getPhotoFromBundle()

        val userId = AppPreferences.userId
        val albumId = AppPreferences.albumId

        album = ListsData.albums.find { it.id == albumId }!!
        user = ListsData.users.find { it.id == userId }!!
    }

    private fun getPhotoFromBundle() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            photoDetails = bundle.getParcelable("parPhoto");
        }
    }

    private fun loadPhotoDetails() {
        textPhotoTitle.text = photoDetails.title
        textAlbumTitle.text = album.title
        textUser.text = user.name

        GlideApp.with(this)
            .load(photoDetails.url)
            .into(imgPhotoDetails)
    }

    private fun registerListeners() {
        // Set up the user interaction to manually show or hide the system UI.
        imgPhotoDetails.setOnClickListener { toggle() }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun toggle() {
        if (mVisible) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        fullscreen_content_controls.visibility = View.GONE
        mVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        // Show the system bar
        imgPhotoDetails.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        mVisible = true

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable)
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
    }
}
