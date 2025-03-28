package com.emro.dictionary.request.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@Service
public class FileStorageService {
	private final Path fileStorageLocation;

	// 생성자에서 fileStorageLocation 초기화
	public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
		this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not create upload directory!", e);
		}
	}

	public String storeFiles(List<MultipartFile> files, Map<String, String> filePathMap) throws IOException {
		if (files == null || files.isEmpty()) {
			return null;
		}

		StringBuilder paths = new StringBuilder();
		for (MultipartFile file : files) {
			if (file.isEmpty()) continue;

			String originalFilename = file.getOriginalFilename();
			Path targetLocation = this.fileStorageLocation.resolve(originalFilename);

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
			paths.append(fileName).append(";");
			filePathMap.put(fileName, targetLocation.toString()); // 파일명과 경로 매핑
		}
		return paths.length() > 0 ? paths.substring(0, paths.length() - 1) : null;
	}
}