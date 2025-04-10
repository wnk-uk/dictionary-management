package com.emro.dictionary.request.storage.api;

import com.emro.dictionary.request.service.resolver.RequestServiceResolver;
import com.emro.dictionary.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class ImageUploadController {

	private final SecurityUtil securityUtil;

	@Value("${file.upload-dir}")
	private String uploadDir;

	@PostMapping("/temp-image")
	public ResponseEntity<Map<String, String>> uploadTempImage(
			@RequestParam("file") MultipartFile file
	) throws IOException {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
		}

		Path tempPath = Paths.get(uploadDir).resolve("temp");
		Files.createDirectories(tempPath);

		String username = securityUtil.getUsername();
		String sanitizedUser = username.replaceAll("[^a-zA-Z0-9]", "_");
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

		String originalFileName = file.getOriginalFilename();
		String extension = "";
		if (originalFileName != null && originalFileName.contains(".")) {
			extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		}

		String fileName = sanitizedUser + "_" + timestamp + extension;
		Path target = tempPath.resolve(fileName);
		Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

		String imageUrl = "/uploads/temp/" + fileName;
		String wrappedImage = "<a href='" + imageUrl + "' target='_blank'><img src='" + imageUrl + "'/></a>";

		return ResponseEntity.ok(Map.of("url", imageUrl, "html", wrappedImage));
	}
}
