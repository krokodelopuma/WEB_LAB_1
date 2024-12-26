/*import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;
import com.fastcgi.FCGIInputStream;
import com.fastcgi.FCGIOutputStream;
import com.fastcgi.FCGIRequest;
import com.fastcgi.FCGIGlobalDefs;
import com.fastcgi.FCGIInterface;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import com.google.gson.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Server {

    private final Gson gson = new Gson();

    public void start() {
        FCGIInterface fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            String method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");

            if (method.equals("POST")) {  // Метод изменен на POST
                StringBuilder requestData = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(FCGIInterface.request.inStream, "UTF-8"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        requestData.append(line);
                    }
                } catch (IOException e) {
                    System.out.println(error("Error reading request data"));
                    continue;
                }

                if (requestData.length() > 0) {
                    try {
                        // Парсим JSON из тела запроса
                        JsonObject json = gson.fromJson(requestData.toString(), JsonObject.class);

                        Integer x = json.get("x").getAsInt();
                        Double y = json.get("y").getAsDouble();
                        Double r = json.get("r").getAsDouble();

                        // Проверка данных
                        HashMap<String, Number> mappedData = new HashMap<>();
                        mappedData.put("x", x);
                        mappedData.put("y", y);
                        mappedData.put("r", r);

                        if (!validate(mappedData)) {
                            System.out.println(error("Invalid data"));
                        } else {
                            long timeNow = System.nanoTime();
                            String result = checkHit(x, y, r) ? "Попадание" : "Промах";

                            System.out.println(response(result, x.toString(), y.toString(), r.toString(), System.nanoTime() - timeNow, ""));
                        }
                    } catch (Exception exc) {
                        System.out.println(error("Invalid JSON data: " + exc.getMessage()));
                    }
                } else {
                    System.out.println(error("Empty request data"));
                }
            } else {
                System.out.println(error("Only POST requests"));
            }
        }
    }

    // Метод для проверки попадания точки (x, y) в область с радиусом r
    private boolean checkHit(int x, double y, double r) {
        return (x >= 0 && y >= 0 && x * x + y * y <= r * r) ||  // Круг
                (x <= 0 && y >= 0 && x >= -r && y <= r) ||        // Прямоугольник
                (x >= 0 && y <= 0 && y >= x - r / 2);             // Треугольник
    }

    // Метод для валидации данных
    private boolean validate(HashMap<String, Number> data) {
        Integer x = (Integer) data.get("x");
        Double y = (Double) data.get("y");
        Double r = (Double) data.get("r");

        return x != null && y != null && r != null &&
                x >= -5 && x <= 5 &&                      // Проверка допустимого диапазона x
                y >= -5.0 && y <= 5.0 &&                  // Проверка допустимого диапазона y
                r > 0 && r <= 5;                          // Проверка допустимого диапазона r
    }

    // Метод для формирования ответа об ошибке
    private String error(String text) {
        return """
                Content-Type: application/json; charset=utf-8\r
                \r
                {\"error\":\"%s\"}
                """.formatted(text);
    }

    // Метод для формирования успешного ответа
    private String response(String result, String x, String y, String r, Long time, String error) {
        return """
                Content-Type: application/json; charset=utf-8\r            
                \r
                {\"result\":\"%s\",\"x\":\"%s\",\"y\":\"%s\",\"r\":\"%s\",\"currentTime\":\"%s\",\"executionTime\":\"%s\",\"error\":\"%s\"}
                """.formatted(result, x, y, r, LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")), time.toString(), error);
    }
}*/