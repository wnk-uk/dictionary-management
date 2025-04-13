package com.emro.dictionary.storage.scheduler;

import com.emro.dictionary.multLang.dto.MultLangDTO;
import com.emro.dictionary.multLang.repository.MultLangMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MultlangDumpScheduler {

    private final MultLangMapper multlangMapper;

    @Value("${file.upload-dir}")
    private String DUMP_DIR;

    @Scheduled(cron = "0 */15 * * * *") // 15분단위
    public void dumpMultlangToJson() {
        try {
            List<MultLangDTO> DTOs = multlangMapper.findAll();

            Map<String, Map<String, String>> result = new LinkedHashMap<>();

            for (MultLangDTO DTO : DTOs) {
                result
                    .computeIfAbsent(DTO.getMultlangKey(), k -> new LinkedHashMap<>())
                    .put(DTO.getMultlangCcd(), DTO.getMultlangTranslCont());
            }

            File dir = new File(DUMP_DIR);
            if (!dir.exists()) dir.mkdirs();

            String filename = DUMP_DIR + "/multlang_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".json";

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), result);

            log.info("✅ multlang JSON 덤프 완료: {}", filename);

        } catch (Exception e) {
            log.error("❌ multlang JSON 덤프 실패", e);
        }
    }

}
