package com.yoond.vidaily.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.amazonaws.util.IOUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * Copies the selected file into the internal storage
 */
fun getFileFromUri(uri: Uri, context: Context): File? {
    var file: File? = null

    val fileDescriptor = context.contentResolver?.openFileDescriptor(uri, "r", null)
    fileDescriptor?.let {
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        file = File(context.cacheDir, getPathFromUri(uri, context.contentResolver))
        val outputStream = FileOutputStream(file)
        IOUtils.copy(inputStream, outputStream)
    }
    return file
}

private fun getPathFromUri(uri: Uri, contentResolver: ContentResolver): String {
    var path = ""
    val cursor = contentResolver.query(uri, null, null, null, null)

    if (cursor != null) {
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        path = cursor.getString(nameIndex)
        cursor.close()
    }
    return path
}