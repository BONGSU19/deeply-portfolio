package kr.spring.chatbot.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.util.MathArrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@Controller
@RequestMapping("/chatbot")
public class ChatbotController {
	@Value("${dataconfig.openai-secret-key}")
	private String apiKey;
	
	String filePath = "/assets/data/site_info.csv"; // 입력 CSV 파일 경로
	
	private static final Gson gson = new Gson();
	private static final OkHttpClient client = new OkHttpClient();

	@GetMapping("/list")
	public String list() {
		return "chatbotList";
	}

	@PostMapping("/chatbot")
	@ResponseBody
	public Map<String,String> chatMain(String message, HttpSession session,HttpServletRequest request) throws IOException{
		//세션에 대화 기록을 가져옴
		String path = request.getServletContext().getRealPath(filePath);
		log.debug("<<파일 경로>> : " +  path);
		List<Map<String, Object>> records = processCsv(request.getServletContext().getRealPath(filePath));

		JSONArray conversationSite = (JSONArray) session.getAttribute("conversationSite");
		if(conversationSite==null) {
			//대화 기록이 없으면 새로운 JSONArray 생성
			conversationSite= new JSONArray();
			//시스템 역할 추가
			JSONObject systemMessage = new JSONObject();
			systemMessage.put("role", "system");
			systemMessage.put("content", "당신은 사이트 매니저입니다. 다음의 컨텍스트를 바탕으로 질문에 정중하게 대답하세요. 컨텍스트가 부족하면 '모르겠습니다'라고 답하세요.");
			conversationSite.put(systemMessage);
		}
		// 사용자 입력을 컨텍스트에 추가
		String context2 = findContext(message, records, 200);
		String prompt = context2 + "\n질문: " + message;
		JSONObject userMessage = new JSONObject();
		userMessage.put("role", "user");
		userMessage.put("content", prompt);
		conversationSite.put(userMessage);

		log.debug("<<conversationSite>> : " + conversationSite);

		String response = chatWithGPT(conversationSite);
		Map<String,String> mapJson = new HashMap<String,String>();
		if (response != null) {
			log.debug("ChatGPT: " + response);
			mapJson.put("response", response);

			// ChatGPT 응답을 컨텍스트에 추가
			JSONObject assistantMessage = new JSONObject();
			assistantMessage.put("role", "assistant");
			assistantMessage.put("content", response);
			conversationSite.put(assistantMessage);

			session.setAttribute("conversationSite", conversationSite);
		} else {
			System.out.println("ChatGPT와의 통신 중 오류가 발생했습니다.\n");
		}

		return mapJson;

	}

	//OPEN AI 연동
	private String chatWithGPT(JSONArray messages) {
		HttpURLConnection connection = null;
		try {
			//요청 본문 생성
			JSONObject data = new JSONObject();
			data.put("model", "gpt-4o-mini");//사용할 모델 지정
			data.put("messages", messages);//대화 기록 포함
			data.put("max_tokens", 1000);//최대 토큰 수 설정
			data.put("temperature", 0.5);//응답의 창의성 조절

			//HTTP 연결 설정
			URL url = 
					new URL("https://api.openai.com/v1/chat/completions");
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			//인증 헤더 추가
			connection.setRequestProperty(
					"Authorization", "Bearer " + apiKey);
			connection.setRequestProperty(
					"Content-Type", "application/json");
			//출력 스트림 사용 가능 설정
			connection.setDoOutput(true);

			//요청 데이터 전송
			try (OutputStream os = connection.getOutputStream()){
				//데이터를 UTF-8 형식으로 변환하여 전송
				os.write(data.toString().getBytes("utf-8"));
			}
			//응답 받기
			int responseCode = connection.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_OK) {
				//HTTP 200 응답 처리
				try(BufferedReader br = 
						new BufferedReader(
								new InputStreamReader(
										connection.getInputStream(),"utf-8"))){
					StringBuilder response = new StringBuilder();
					String line;
					while((line = br.readLine())!=null) {
						response.append(line.trim());
					}
					//JSON 응답 파싱 및 응답 텍스트 추출
					JSONObject result = 
							new JSONObject(response.toString());
					//ChatGPT의 응답 내용 반환
					return result.getJSONArray("choices")
							.getJSONObject(0)
							.getJSONObject("message")
							.getString("content");
				}
			}else {
				log.error("HTTP 오류 코드 : " + responseCode);
			}			
		}catch(IOException | JSONException e) {
			log.error("오류 발생 : " +  e.getMessage());
		}finally {
			if(connection != null)
				connection.disconnect();
		}
		return null;
	}

	private List<Double> getEmbedding2(String text) throws IOException {
		text = text.replace("\n", " "); // 줄 바꿈 제거

		// 요청 payload 구성
		Map<String, Object> payload = Map.of("input", List.of(text), "model", "text-embedding-3-small");
		String jsonPayload = new Gson().toJson(payload);

		// URL 및 HttpURLConnection 초기화
		URL url = new URL("https://api.openai.com/v1/embeddings");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try {
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "Bearer " + apiKey);
			connection.setDoOutput(true);

			// 요청 본문 쓰기
			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonPayload.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			// 응답 코드  확인
			int responseCode = connection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new IOException("Unexpected response code: " + responseCode);
			}

			// 응답 본문 읽기
			try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					response.append(line.trim());
				}

				// JSON 파싱 및 임베딩 추출
				JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
				JsonArray embeddingArray = json.getAsJsonArray("data").get(0).getAsJsonObject().getAsJsonArray("embedding");

				List<Double> embedding = new ArrayList<>();
				for (JsonElement e : embeddingArray) {
					embedding.add(e.getAsDouble());
				}

				return embedding;
			}
		} finally {
			connection.disconnect();
		}
	}

	// Embedding 요청 함수
	private List<Double> getEmbedding(String text) throws IOException {
		text = text.replace("\n", " "); // 줄 바꿈 제거
		Map<String, Object> payload = Map.of("input", List.of(text), "model", "text-embedding-3-small");

		RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(payload));
		Request request = new Request.Builder()
				.url("https://api.openai.com/v1/embeddings")
				.addHeader("Authorization", "Bearer " + apiKey)
				.post(body)
				.build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

		JsonObject json = gson.fromJson(response.body().string(), JsonObject.class);
		JsonArray embeddingArray = json.getAsJsonArray("data").get(0).getAsJsonObject().getAsJsonArray("embedding");

		List<Double> embedding = new ArrayList<>();
		for (JsonElement e : embeddingArray) {
			embedding.add(e.getAsDouble());
		}
		return embedding;
	}

	// CSV에서 텍스트 읽고 임베딩 계산
	private List<Map<String, Object>> processCsv(String filePath) throws IOException {
		List<Map<String, Object>> records = new ArrayList<>();
		try (Reader reader = new FileReader(filePath);
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

			for (CSVRecord csvRecord : csvParser) {
				String text = csvRecord.get("text");
				if (text != null && !text.isEmpty()) {
					List<Double> embedding = getEmbedding(text);
					Map<String, Object> record = new HashMap<>();
					record.put("text", text);
					record.put("embedding", embedding);
					records.add(record);
				}
			}
		}
		return records;
	}

	// 코사인 유사도 계산 함수
	private double cosineSimilarity(List<Double> a, List<Double> b) {
		double[] aArr = a.stream().mapToDouble(Double::doubleValue).toArray();
		double[] bArr = b.stream().mapToDouble(Double::doubleValue).toArray();
		return 1 - MathArrays.distance(aArr, bArr);
	}

	// 유사한 문맥 찾기
	private String findContext(String question, List<Map<String, Object>> records, int maxTokens) throws IOException {
		List<Double> questionEmbedding = getEmbedding2(question);
		List<Map.Entry<String, Double>> distances = new ArrayList<>();

		for (Map<String, Object> record : records) {
			List<Double> embedding = (List<Double>) record.get("embedding");
			double similarity = cosineSimilarity(questionEmbedding, embedding);
			distances.add(new AbstractMap.SimpleEntry<>((String) record.get("text"), similarity));
		}

		distances.sort((a, b) -> Double.compare(b.getValue(), a.getValue())); // 유사도 기준 정렬

		StringBuilder contextBuilder = new StringBuilder();
		int tokenCount = 0;

		for (Map.Entry<String, Double> entry : distances) {
			String text = entry.getKey();
			tokenCount += text.split(" ").length;
			if (tokenCount > maxTokens) break;
			contextBuilder.append(text).append("\n");
		}
		return contextBuilder.toString();
	}


}





