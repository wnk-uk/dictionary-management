package com.emro.dictionary.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
public class MultLangRequestDetailDTO {
	private String existingWord;
	private String multlangCcd;
	private String multlangKey;
	private String multlangTranslCont;
	private String multlangTranslContAbbr;
	private String multlangTyp;
	private String screenPath;
	private String sourcePath;
	private String comment;
	private String editorContent; // New field for editor content
	private List<MultipartFile> files; // New field for uploaded files
	private String imagePath;

	public void setImagePath(String imagePath) {
		if (imagePath != null && !imagePath.trim().isEmpty()) {
			this.imagePath = imagePath.trim(); // Example: trim the path
		} else {
			this.imagePath = null; // Or set a default value if needed
		}
	}
}
