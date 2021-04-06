package com.example.fourinoneapp.models

import io.realm.RealmObject
import java.io.Serializable

class ImageExporter : Serializable {
    var picturName: String? = null
    var picturePath: String? = null
    var imageUri: String? = null
    var imageTXT : String? = null


    constructor(picturName: String?, picturePath: String?, imageUri: String?, imageTXT: String?) {
        this.picturName = picturName
        this.picturePath = picturePath
        this.imageUri = imageUri
        this.imageTXT = imageTXT
    }

    override fun toString(): String {
        return imageTXT.toString()
    }
}

public open class Exporter : RealmObject() {
    public open var picturName: String? = null
    public open var picturePath: String? = null
    public open var imageUri: String? = null
    public open var imageTXT : String? = null
}

fun realmToExport(exporter: Exporter): ImageExporter{
    return ImageExporter(exporter.picturName,exporter.picturePath,exporter.imageUri,exporter.imageTXT)
}

fun exportToRealm(export : ImageExporter):Exporter{
    var exporter = Exporter()
    exporter.picturName = export.picturName
    exporter.picturePath = export.picturePath
    exporter.imageUri = export.imageUri
    exporter.imageTXT = export.imageTXT
    return exporter
}