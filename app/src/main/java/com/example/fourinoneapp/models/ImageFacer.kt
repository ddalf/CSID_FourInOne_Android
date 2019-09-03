package com.example.fourinoneapp.models

import java.io.Serializable

class ImageFacer : Serializable {
    var picturName: String? = null
    var picturePath: String? = null
    var pictureSize: String? = null
    var imageUri: String? = null
    var picExt: String? = null
    var selected: Boolean? = false

    constructor() {

    }

    constructor(picturName: String, picturePath: String, pictureSize: String, imageUri: String, picExt: String) {
        this.picturName = picturName
        this.picturePath = picturePath
        this.pictureSize = pictureSize
        this.imageUri = imageUri
        this.picExt = picExt
    }
}
