package com.example.fourinoneapp.models

import java.io.Serializable

class ImageExporter : Serializable {
    lateinit var imageFacer : ImageFacer
    var imageTXT : String? = null

    constructor() {
        imageFacer = ImageFacer()
    }

    constructor(imageFacer: ImageFacer, imageTXT: String?) {
        this.imageFacer = imageFacer
        this.imageTXT = imageTXT
    }

    override fun toString(): String {
        return imageTXT.toString()
    }
}
