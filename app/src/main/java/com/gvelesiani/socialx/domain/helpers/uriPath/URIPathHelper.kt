package com.gvelesiani.socialx.domain.helpers.uriPath

import android.content.Context
import android.net.Uri

interface URIPathHelper {
    fun getPath(context: Context, uri: Uri): String?

}