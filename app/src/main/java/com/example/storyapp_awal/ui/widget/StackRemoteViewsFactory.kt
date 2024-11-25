package com.example.storyapp_awal.ui.widget

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.storyapp_awal.R
import com.example.storyapp_awal.data.AppRepository
import com.example.storyapp_awal.data.Results
import com.example.storyapp_awal.data.remote.response.ListStoryItem
import com.example.storyapp_awal.di.Injection
import kotlinx.coroutines.runBlocking
import android.widget.AdapterView
import com.squareup.picasso.Picasso

class StackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var storyItems = mutableListOf<ListStoryItem>()
    private lateinit var repository: AppRepository

    override fun onCreate() {
        repository = Injection.provideAppRepository(context)
        Log.d("StackRemoteViewsFactory Widget", "onCreate called")
        // Ambil data di onDataSetChanged
        onDataSetChanged()
    }

    override fun onDataSetChanged() {
        Log.d("StackRemoteViewsFactory Widget", "onDataSetChanged called")
        runBlocking {
            val result = repository.getAllStoriesWidget()
            result.let {
                when (it) {
                    is Results.Success -> {
                        storyItems.clear()
                        storyItems.addAll(it.data)
                        Log.d("StackRemoteViewsFactory", "Data size: ${storyItems.size}")
                    }

                    is Results.Error -> {
                        storyItems.clear()  // Jika terjadi error, tampilkan data kosong
                        Log.d("StackRemoteViewsFactory", "Error: ${it.error}")
                    }

                    is Results.Loading -> {
                        // Jika loading, bisa ditambahkan animasi atau indikator lainnya
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        storyItems.clear()
    }

    override fun getCount(): Int = storyItems.size

    override fun getViewAt(position: Int): RemoteViews? {
        if (position == AdapterView.INVALID_POSITION || storyItems.isEmpty()) {
            return null
        }

        val story = storyItems[position]
        val views = RemoteViews(context.packageName, R.layout.widget_item)

        val imageUrl = story.photoUrl
        val bitmap = getBitmapFromUrl(imageUrl)

        if (bitmap != null) {
            Log.d("StackRemoteViewsFactory", "bitmap : $bitmap")
            views.setImageViewBitmap(R.id.imageItem, bitmap)
        } else {
            Log.d("StackRemoteViewsFactory", "Failed to load bitmap")
            views.setImageViewResource(R.id.imageItem, R.drawable.ic_error)
        }

        return views
    }


    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.story_widget_loading)
    }

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true
}

private fun getBitmapFromUrl(url: String): Bitmap? {
    return try {
        Picasso.get()
            .load(url)
            .get()
    } catch (e: Exception) {
        null
    }
}