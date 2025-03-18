package com.emro.dictionary.request;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {
	private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

	@PostConstruct
	public void init() throws IOException {
		Files.createDirectories(fileStorageLocation);
	}

	public String storeFiles(List<MultipartFile> files) throws IOException {
		StringBuilder paths = new StringBuilder();
		for (MultipartFile file : files) {
			String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			paths.append(targetLocation.toString()).append(";");
		}
		return paths.toString();
	}
}