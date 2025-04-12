package com.emro.dictionary.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class JsonDataLoader implements ApplicationRunner {

	private final JdbcTemplate jdbcTemplate;

	public JsonDataLoader(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		String json = Files.readString(Paths.get("D:/dic/sample.json")); // 외부 JSON 파일 로드
//		jdbcTemplate.update("INSERT INTO SHEET_DATA (json_data) VALUES (?)", json);
	}
}