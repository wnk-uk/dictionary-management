package com.emro.dictionary.request;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileStorageService {
	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(@Value("${file.upload-dir:D:/uploads/final}") String uploadDir) {
		this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
	}

	@PostConstruct
	public void init() throws IOException {
		Files.createDirectories(fileStorageLocation);
		System.out.println("File storage location initialized: " + fileStorageLocation.toString());
	}

	public String storeFiles(List<MultipartFile> files) throws IOException {
		if (files == null || files.isEmpty()) {
			return null;
		}

		StringBuilder paths = new StringBuilder();
		for (MultipartFile file : files) {
			if (file.isEmpty()) continue;

			String originalFilename = file.getOriginalFilename();
			Path targetLocation = this.fileStorageLocation.resolve(originalFilename);

			// 파일명 중복 처리
			int counter = 1;
			String fileName = originalFilename;
			while (Files.exists(targetLocation)) {
				String baseName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
				String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
				fileName = baseName + "_" + counter + extension;
				targetLocation = this.fileStorageLocation.resolve(fileName);
				counter++;
			}

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			paths.append(targetLocation.toString()).append(";");
		}
		return paths.length() > 0 ? paths.substring(0, paths.length() - 1) : null;
	}
}