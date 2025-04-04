package com.emro.dictionary.request.storage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;

@Component
public class TempFileCleanupScheduler {

	private static final Logger logger = LoggerFactory.getLogger(TempFileCleanupScheduler.class);

	@Value("${file.upload-dir}")
	private String uploadDir;

	// 하루에 한 번 실행 (예: 매일 자정)
	@Scheduled(cron = "0 0 0 * * ?")
	public void cleanupTempFiles() {
		logger.info("Starting temp file cleanup task...");

		String tempPath = uploadDir + "/temp";

		File directory = new File(tempPath);
		if (!directory.exists() || !directory.isDirectory()) {
			logger.warn("Temp directory does not exist or is not a directory: {}", tempPath);
			return;
		}

		// 디렉토리 내 모든 파일을 가져옴
		File[] files = directory.listFiles();
		if (files == null || files.length == 0) {
			logger.info("No files found in temp directory: {}", tempPath);
			return;
		}

		// 현재 시간
		long now = Instant.now().toEpochMilli();
		// 삭제 기준 시간 (예: 24시간 지난 파일)
		long expirationTime = 24 * 60 * 60 * 1000; // 24시간 (밀리초)

		Arrays.stream(files)
				.filter(File::isFile)
				.forEach(file -> {
					try {
						long lastModified = file.lastModified();
						if ((now - lastModified) > expirationTime) {
							Files.delete(file.toPath());
							logger.info("Deleted expired temp file: {}", file.getAbsolutePath());
						}
					} catch (Exception e) {
						logger.error("Failed to delete temp file: {}", file.getAbsolutePath(), e);
					}
				});

		logger.info("Temp file cleanup task completed.");
	}
}