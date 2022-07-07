package com.example.newsfeed.presentation.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*
Whenever we want to use Hilt for dependency Injection needs an application class.

After this, we must open the manifest file, and add application class name to it.

Before with dagger, we had to add some code in here. We don't need that code with Dagger/Hilt.

We also don't need to create component interfaces.
 */
@HiltAndroidApp
class NewsApp: Application()