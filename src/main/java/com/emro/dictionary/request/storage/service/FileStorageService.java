package com.emro.dictionary.request.storage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

		// 현재 날짜로 폴더 생성 (예: 2025-04-02)
		String dateFolder = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE); // YYYY-MM-DD
		Path datePath = this.fileStorageLocation.resolve(dateFolder);
		Files.createDirectories(datePath); // 날짜 폴더가 없으면 생성

		StringBuilder paths = new StringBuilder();
		for (MultipartFile file : files) {
			if (file.isEmpty()) continue;

			String originalFilename = file.getOriginalFilename();
			Path targetLocation = datePath.resolve(originalFilename); // 날짜 폴더 내 파일 경로

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			// 상대 경로로 저장 (예: 2025-04-02/fileName)
			paths.append(dateFolder).append("/").append(originalFilename).append(";");
			filePathMap.put(originalFilename, targetLocation.toString()); // 원래 파일명 기준으로 저장
		}
		return paths.length() > 0 ? paths.substring(0, paths.length() - 1) : null;
	}
}