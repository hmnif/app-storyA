package com.example.storyapp_awal.ui.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return StackRemoteViewsFactory(applicationContext)
    }
}