package com.carnetdevoyage.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


import org.springframework.stereotype.Service;

@Service
public class FileService {

	public Path save(InputStream data, String originalFileName) throws IOException {
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		Path p;
		do {
			p = Paths.get("C:\\Users\\guill\\Pictures\\ImagesPostLesCarnetsDeVoyage" + UUID.randomUUID() + extension);
		} while (Files.exists(p));
		Files.copy(data, p);
		return p.getFileName();
	}

}
