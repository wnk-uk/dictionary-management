package com.emro.dictionary.storage.scheduler;

import com.emro.dictionary.glo.dto.SheetDTO;
import com.emro.dictionary.glo.repository.SheetMapper;
import com.emro.dictionary.multLang.dto.MultLangDTO;
import com.emro.dictionary.multLang.repository.MultLangMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
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
    private final SheetMapper sheetMapper;

    @Value("${file.upload-dir}")
    private String DUMP_DIR;

    @Scheduled(cron = "0 */15 * * * *") // 15분단위
    public void dumpMultlangToJson() {
        try {
            List<MultLangDTO> DTOs = multlangMapper.findAll();

            Map<String, Map<String, String>> result = new LinkedHashMap<>();
            Map<String, Map<String, String>> resultGlo = new LinkedHashMap<>();

            for (MultLangDTO DTO : DTOs) {
                Map<String, String> langMap =  result.computeIfAbsent(DTO.getMultlangKey(), k -> new LinkedHashMap<>());
                langMap.put(DTO.getMultlangCcd(), DTO.getMultlangTranslCont());
                langMap.put("source", "다국어");
            }

            ObjectMapper mapper = new ObjectMapper();
            String sheetData = sheetMapper.getSheetData();
            JsonNode root = mapper.readTree(sheetData);

            JsonNode data = root.get(0).get("data");
            if (data == null || !data.isArray()) {
                //throw new IllegalArgumentException("Invalid luckysheet JSON: missing 'data' field");
            } else {
                for (int i = 1; i < data.size(); i++) {
                    JsonNode row = data.get(i);
                    if (row == null || !row.isArray()) continue;
                    //TODO
                    String ko_KR = getCellValue(row.get(1));
                    String en_US = getCellValue(row.get(4));
                    String key = getCellValue(row.get(2));

                    if (key == null || "".equals(key)) break;

                    Map<String, String> rowMap = new LinkedHashMap<>();
                    rowMap.put("ko_KR", ko_KR);
                    rowMap.put("en_US", en_US);
                    rowMap.put("source", "용어집");
                    resultGlo.put(key, rowMap);
                }
            }

            File dir = new File(DUMP_DIR);
            if (!dir.exists()) dir.mkdirs();

            File targetMlFile = new File(DUMP_DIR, "multilang.json");
            File targetGlFile = new File(DUMP_DIR, "glo.json");

            File backupFileMl = null;
            if (targetMlFile.exists()) {
                String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
                backupFileMl = new File(DUMP_DIR, "multilang_" + timestamp + ".json");
                if (!targetMlFile.renameTo(backupFileMl)) {
                    log.warn("⚠️ 기존 파일 백업 실패: {}", backupFileMl.getAbsolutePath());
                } else {
                    log.info("📦 기존 multilang.json 백업 완료: {}", backupFileMl.getName());
                }
            }

            File backupFileGlo = null;
            if (targetGlFile.exists()) {
                String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
                backupFileGlo = new File(DUMP_DIR, "glo_" + timestamp + ".json");
                if (!targetGlFile.renameTo(backupFileGlo)) {
                    log.warn("⚠️ 기존 파일 백업 실패: {}", backupFileGlo.getAbsolutePath());
                } else {
                    log.info("📦 기존 glo.json 백업 완료: {}", backupFileGlo.getName());
                }
            }

            String filename = DUMP_DIR + "/multilang.json";
            String filenameGlo = DUMP_DIR + "/glo.json";

            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), result);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filenameGlo), resultGlo);

            if (backupFileMl != null) backupFileMl.delete();
            if (backupFileGlo != null) backupFileGlo.delete();

            log.info("✅ multlang JSON 덤프 완료: {}", filename);
            log.info("✅ multlang JSON 덤프 완료: {}", filenameGlo);
        } catch (Exception e) {
            log.error("❌ multlang JSON 덤프 실패", e);
        }
    }

    private static String getCellValue(JsonNode cell) {
        if (cell == null || cell.isNull()) return "";
        JsonNode v = cell.get("v");
        if (v == null) return "";
        return v.asText();
    }

}
