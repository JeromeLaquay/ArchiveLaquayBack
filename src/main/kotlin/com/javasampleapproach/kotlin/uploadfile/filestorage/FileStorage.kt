package com.javasampleapproach.kotlin.uploadfile.filestorage

import com.javasampleapproach.kotlin.uploadfile.model.FileInfo
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile;
import java.io.File

interface FileStorage {
	fun store(file: MultipartFile, path :String) : String
	fun storeMultiple(files: List<MultipartFile>, path :String) : ResponseEntity<String>
	fun loadFile(filename: String, path :String): Resource
	//fun deleteAll()
	//fun init()
	fun searchFiles(path :String): ResponseEntity<List<FileInfo>>
}