package co.kr.bumaview.domain.question.domain.service.command;

import co.kr.bumaview.domain.question.domain.Question;
import co.kr.bumaview.domain.question.domain.repository.QuestionRepository;
import co.kr.bumaview.global.infra.GoogleSheetApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class QuestionCommandService {

    private final QuestionRepository questionRepository;
    private final GoogleSheetApiClient googleSheetApiClient;

    @Transactional
    public Question createQuestion(String q, String authorId) {
        Question question = new Question(q, authorId);
        return questionRepository.save(question);
    }

    @Transactional
    public Long createQuestionsFromGoogleSheet(String googleSheetUrl, String authorId) {
        try {
            // 1) URL에서 스프레드시트 ID 추출
            String spreadsheetId = extractSpreadsheetId(googleSheetUrl);

            // 2) 시트 목록 조회
            List<String> sheetNames = googleSheetApiClient.getSheetNames(spreadsheetId);
            if (sheetNames.isEmpty()) {
                throw new IllegalArgumentException("스프레드시트에 시트가 없습니다.");
            }

            // 3) 첫 번째 시트의 데이터를 읽음
            String firstSheetName = sheetNames.getFirst();
            String range = String.format("%s!A1:ZZ", firstSheetName);
            List<List<Object>> rows = googleSheetApiClient.readSheet(spreadsheetId, range);
            if (rows.isEmpty()) {
                return 0L;
            }

            // 4) 첫 행은 헤더
            List<Object> headerRow = rows.getFirst();

            List<Question> qToSave = new ArrayList<>();

            // 5) 데이터 행 반복
            for (int i = 1; i < rows.size(); i++) {
                List<Object> row = rows.get(i);
                if (row == null || row.isEmpty()) continue;

                Map<String, Object> dataMap = new HashMap<>();
                for (int col = 0; col < headerRow.size() && col < row.size(); col++) {
                    String key = headerRow.get(col).toString().trim();
                    Object value = row.get(col);
                    dataMap.put(key, value);
                }

                // 6) 필드 추출
                String questionText = Optional.ofNullable(dataMap.get("question"))
                        .map(Object::toString)
                        .orElseThrow(() -> new IllegalArgumentException("question 컬럼이 비어 있습니다."));

                String category = Optional.ofNullable(dataMap.get("category"))
                        .map(Object::toString)
                        .orElse(null);

                String company = Optional.ofNullable(dataMap.get("company"))
                        .map(Object::toString)
                        .orElse(null);

                String year = Optional.ofNullable(dataMap.get("question_at"))
                        .map(Object::toString)
                        .orElse(null);

                // 7) Question 엔티티 생성
                assert year != null;
                Question qEntity = Question.of(
                        questionText,
                        authorId,
                        company,
                        Long.valueOf(year),
                        category
                );

                qToSave.add(qEntity);
            }

            Long count = (long) qToSave.size();
            // 8) DB에 일괄 저장
            questionRepository.saveAll(qToSave);

            return count;

        } catch (Exception e) {
            throw new RuntimeException("Google Sheet에서 질문을 불러오는 중 오류 발생: " + e.getMessage(), e);
        }
    }

    private String extractSpreadsheetId(String url) {
        Pattern pattern = Pattern.compile("/spreadsheets/d/([a-zA-Z0-9-_]+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("유효하지 않은 Google Sheet URL입니다: " + url);
    }

    @Transactional
    public void updateQuestion(Long questionId, String q, String userId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));

        // 작성자 확인
        if (!question.getAuthorId().equals(userId)) {
            throw new AccessDeniedException("본인 질문만 수정할 수 있습니다.");
        }

        question.updateContent(q);
    }

    @Transactional
    public void deleteQuestion(Long questionId, String userId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));

        // 작성자 확인
        if (!question.getAuthorId().equals(userId)) {
            throw new AccessDeniedException("본인 질문만 삭제할 수 있습니다.");
        }

        questionRepository.delete(question);
    }
}
