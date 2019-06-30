package com.javasampleapproach.kotlin.uploadfile.filestorage

import com.javasampleapproach.kotlin.uploadfile.model.FileInfo
import org.apache.tomcat.util.http.fileupload.IOUtils
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File
import java.lang.Exception
import kotlin.streams.toList
import java.io.FileInputStream
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem







@Service
class FileStorageImpl: FileStorage{

	val log = LoggerFactory.getLogger(this::class.java)

	override fun store(file: MultipartFile, path : String): String{
		val location = Paths.get(Companion.filePath + path)
		Files.copy(file.getInputStream(), location.resolve(file.getOriginalFilename()))
		return "youpi"
	}

	override fun storeMultiple(files: List<MultipartFile>, path : String): ResponseEntity<String>{
		try{
			val location = Paths.get(Companion.filePath + path)
			for(file : MultipartFile in files){
				Files.copy(file.getInputStream(), location.resolve(file.getOriginalFilename()))
				println("youpi")
			}
			return ResponseEntity<String>("insertion réussi", HttpStatus.CREATED)
		}catch(e : Exception){
			return ResponseEntity<String>("erreur durant l'insertion des fichiers", HttpStatus.BAD_REQUEST)
		}

	}

	override fun loadFile(filename: String, path : String): Resource {
		val location = Paths.get(path)
		val file = location.resolve(filename)
		val resource = UrlResource(file.toUri())

		if(resource.exists() || resource.isReadable()) {
			return resource
		}else{
			throw RuntimeException("FAIL!")
		}
	}

	//override fun deleteAll(){
	//	FileSystemUtils.deleteRecursively(rootLocation.toFile())
	//}

	//override fun init(){
	//	Files.createDirectory(rootLocation)
	//}

	override fun searchFiles(path : String): ResponseEntity<List<FileInfo>>{
		val fullPath = Companion.filePath + path
		val location = Paths.get(fullPath)
		val paths = Files.walk(location, 1)
				.filter{path -> !path.equals(location)}
				.map(location::relativize).toList()
		var files : List<FileInfo> = ArrayList<FileInfo>()
		for(path : Path in paths){
			val fileinfo : FileInfo = FileInfo(path.toFile().name, fullPath, path.toFile().extension)
			(files!! as ArrayList).add( fileinfo)


		}
		return ResponseEntity<List<FileInfo>>(files, HttpStatus.OK)
	}

	companion object {
		const val filePath : String = "/home/laquay/"
	}
}