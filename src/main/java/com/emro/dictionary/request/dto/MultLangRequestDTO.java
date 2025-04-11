package com.emro.dictionary.request.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MultLangRequestDTO {
    private Long reqId;
    private Long reqUserId;
    private List<MultLangRequestDetailDTO> details;
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