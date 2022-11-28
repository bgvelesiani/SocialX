package com.gvelesiani.socialx.domain.helpers.uriPath

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface FileHelper {
    fun getPath(context: Context, uri: Uri): String?
    fun getFileFromBitmap(bitmap: Bitmap?, context: Context): File
}