package com.emro.dictionary.delivery.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/down")
public class FileDeliveryController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/resource/{fileName}")
    public ResponseEntity<byte[]> downloadMultilangJson(@PathVariable(value = "fileName") String fileName) throws IOException {
        File file = new File(uploadDir + "/" + fileName);

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(("파일이 존재하지 않습니다: ").getBytes());
        }

        byte[] content = Files.readAllBytes(file.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDisposition(ContentDisposition.attachment().filename(file.getName()).build());

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

}
