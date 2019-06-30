package com.javasampleapproach.kotlin.uploadfile

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication

import org.springframework.context.annotation.Bean

import com.javasampleapproach.kotlin.uploadfile.filestorage.FileStorage;

@SpringBootApplication
class KotlinSpringBootUploadDownloadFilesApplication{
	
    @Autowired
	lateinit var fileStorage: FileStorage;
	
	@Bean
	fun run() = CommandLineRunner {
		//fileStorage.init()
	}	
}

fun main(args: Array<String>) {
    //runApplication<KotlinSpringBootUploadDownloadFilesApplication>(*args)
	SpringApplication.run(KotlinSpringBootUploadDownloadFilesApplication::class.java, *args)
}
