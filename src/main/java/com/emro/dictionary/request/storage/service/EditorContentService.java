package com.emro.dictionary.request.storage.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EditorContentService {

	@Value("${file.upload-dir}")
	private String uploadDir;

	public String moveTempImagesToUpload(String editorContent) throws IOException {
		Document doc = Jsoup.parse(editorContent);
		Elements images = doc.select("img[src*='/temp/'], a[href*='/temp/']");
		String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		Path uploadPath = Paths.get(uploadDir).resolve(today);
		Files.createDirectories(uploadPath);

		for (Element element : images) {
			String src = element.tagName().equals("img") ? element.attr("src") : element.attr("href");
			if (src == null || !src.contains("/temp/")) continue;

			String tempFile = src.substring(src.lastIndexOf("/") + 1);
			Path source = Paths.get(uploadDir).resolve("temp").resolve(tempFile);
			if (Files.exists(source)) {
				Path target = uploadPath.resolve(tempFile);
				Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
				String newUrl = "/uploads/" + today + "/" + tempFile;

				// update both img and anchor href/src if applicable
				if (element.tagName().equals("img")) {
					element.attr("src", newUrl);
					Element parent = element.parent();
					if (parent != null && parent.tagName().equals("a") && parent.hasAttr("href")) {
						parent.attr("href", newUrl);
					}
				} else if (element.tagName().equals("a")) {
					element.attr("href", newUrl);
					Element img = element.selectFirst("img");
					if (img != null) {
						img.attr("src", newUrl);
					}
				}
			}
		}

		return doc.body().html();
	}
}