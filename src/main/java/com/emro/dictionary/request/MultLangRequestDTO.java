package com.emro.dictionary.request;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
public class MultLangRequestDTO {
    private Long dicReqId;
    private String reqUsrNm;
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