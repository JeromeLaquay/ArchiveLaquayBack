package com.javasampleapproach.kotlin.uploadfile.controller


import com.javasampleapproach.kotlin.uploadfile.filestorage.FileStorage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/files")
class FileController {

    @Autowired
    lateinit var fileStorage: FileStorage

    @CrossOrigin
    @RequestMapping("/download") fun downloadFile(@RequestParam filename: String,@RequestParam path: String) :ResponseEntity<Resource> {
        val file = fileStorage.loadFile(filename, path)
        println("filename : "+ filename)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @CrossOrigin
    @RequestMapping("", MediaType.APPLICATION_OCTET_STREAM_VALUE)fun findFiles(@RequestParam path: String) = fileStorage.searchFiles(path)

    @CrossOrigin
    @PostMapping fun addfile(@RequestParam path: String, @RequestPart file : MultipartFile) = fileStorage.store(file, path)

    @CrossOrigin
    @PostMapping("/multiple") fun addfiles(@RequestParam path: String, @RequestPart files : List<MultipartFile>) = fileStorage.storeMultiple(files, path)
}