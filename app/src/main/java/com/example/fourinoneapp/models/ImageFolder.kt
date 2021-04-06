package com.example.fourinoneapp.models

import io.realm.RealmObject

class ImageFolder {
    var path: String? = null
    var folderName: String? = null
    var numberOfPics = 0
    var firstPic: String? = null
    var isSelect : Boolean = true


    constructor() {
    }

    constructor(path: String, folderName: String) {
        this.path = path
        this.folderName = folderName
    }
    constructor(path: String?, folderName: String?, numberOfPics : Int?, firstPic : String?, isSelect : Boolean) {
        this.path = path
        this.folderName = folderName
    }

    fun addpics() {
        this.numberOfPics++
    }
}


public open class folderFac : RealmObject() {
    public open var path: String? = null
    public open var folderName: String? = null
    public open var numberOfPics : Int? = 0
    public open var firstPic: String? = null
    public open var isSelect : Boolean = true
}

fun realmToFolder(folder: folderFac): ImageFolder{
    return ImageFolder(folder.path,folder.folderName,folder.numberOfPics,folder.firstPic,folder.isSelect)
}

fun folderToRealm(export : ImageFolder):folderFac{
    var folder = folderFac()
    folder.path = export.path
    folder.folderName = export.folderName
    folder.numberOfPics = export.numberOfPics
    folder.firstPic = export.firstPic
    folder.isSelect = export.isSelect
    return folder
}