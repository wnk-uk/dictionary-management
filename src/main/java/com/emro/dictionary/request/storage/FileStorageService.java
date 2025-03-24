package com.emro.dictionary.request.storage;

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

	// 생성자에서 fileStorageLocation 초기화 (예: application.properties에서 설정)
	public FileStorageService() {
		this.fileStorageLocation = Paths.get("D:\\uploads\\final").toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not create upload directory!", e);
		}
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

			// 파일 저장
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			// 파일명만 paths에 추가 (절대 경로 대신)
			paths.append(fileName).append(";");
		}
		return paths.length() > 0 ? paths.substring(0, paths.length() - 1) : null;
	}
}