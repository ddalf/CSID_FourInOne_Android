package com.example.fourinoneapp.models

/**
 * Author CodeBoy722
 *
 * Custom class for holding data of images on the device external storage
 */
class ImageFacer {
    var picturName: String? = null
    var picturePath: String? = null
    var pictureSize: String? = null
    var imageUri: String? = null
    var selected: Boolean? = false

    constructor() {

    }

    constructor(picturName: String, picturePath: String, pictureSize: String, imageUri: String) {
        this.picturName = picturName
        this.picturePath = picturePath
        this.pictureSize = pictureSize
        this.imageUri = imageUri
    }
}
