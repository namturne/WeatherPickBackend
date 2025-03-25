package WeatherPick.weatherpick.domain.weather.service;


import WeatherPick.weatherpick.domain.weather.dto.WeatherDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class WeatherService {


    public List<WeatherDto> fetchWeatherData(){
        List<WeatherDto> result = new ArrayList<>();
        try {
            String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"
                    + "?serviceKey=YOUR_API_KEY_HERE" // 실제 키로 교체
                    + "&pageNo=1&numOfRows=1000&dataType=JSON"
                    + "&base_date=20250407&base_time=0500"
                    + "&nx=55&ny=127";

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300
                            ? conn.getInputStream()
                            : conn.getErrorStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode items = mapper.readTree(sb.toString())
                    .path("response").path("body").path("items").path("item");

            // 시간대별 정보를 모으기 위한 임시 Map
            Map<String, Map<String, String>> timeMap = new HashMap<>();

            for (JsonNode item : items) {
                String time = item.path("fcstTime").asText();
                if (!time.startsWith("0") || Integer.parseInt(time.substring(0, 2)) > 12) continue;

                String category = item.path("category").asText();
                String value = item.path("fcstValue").asText();

                timeMap.putIfAbsent(time, new HashMap<>());
                timeMap.get(time).put(category, value);
            }

            for (String time : new TreeSet<>(timeMap.keySet())) {
                Map<String, String> data = timeMap.get(time);
                String hour = time.substring(0, 2) + "시";
                String tmp = data.getOrDefault("TMP", "") + "℃";
                String reh = data.getOrDefault("REH", "") + "%";
                String wsd = data.getOrDefault("WSD", "");
                String pcp = data.getOrDefault("PCP", "강수없음").replace("강수없음", "없음");
                String sky = convertSkyCode(data.getOrDefault("SKY", ""));

                result.add(new WeatherDto(hour, tmp, reh, wsd, pcp, sky));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    private String convertSkyCode(String code) {
        switch (code) {
            case "1": return "맑음";
            case "3": return "구름많음";
            case "4": return "흐림";
            default: return "정보없음";
        }
    }



}
