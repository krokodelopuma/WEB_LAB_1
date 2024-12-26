import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import com.fastcgi.FCGIInputStream;
import com.fastcgi.FCGIOutputStream;
import com.fastcgi.FCGIRequest;
import com.fastcgi.FCGIGlobalDefs;
import com.fastcgi.FCGIInterface;

public class Main {
    private static final String RESPONSE_TEMPLATE = "Content-Type: application/json\n" +
            "Content-Length: %d\n\n%s";

    public static void main(String args[]) {
        while (new FCGIInterface().FCGIaccept() >= 0) {
            try {

                String requestMethod = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
                /*
                if (requestMethod == null || !requestMethod.equalsIgnoreCase("POST")) {
                    sendJson("{\"error\": \"Only POST requests are allowed\"}");
                    continue; // Переходим к следующему запросу
                }

                                System.out.println("Invalid +++++");
                String requestMethod = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
                System.out.println("Invalid -----");
                //String requestMethod = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
                if (requestMethod == null) {
                    System.out.println("Content-Type: text/plain\n");
                    System.out.println("Ошибка: REQUEST_METHOD не установлен");
                    continue; // Переходим к следующей итерации
                }
                if (!requestMethod.equalsIgnoreCase("POST")) {
                    // Если метод не POST
                    System.out.println("Content-Type: text/plain\n");
                    System.out.println("Ошибка: только POST-запросы разрешены");
                    continue;
                }
*/
                LocalDateTime startedAt = LocalDateTime.now();

                // Читаем тело POST-запроса
                String requestBody = readRequestBody();
                HashMap<String, String> params = parsjsonstr.parse(requestBody);

                int x = Integer.parseInt(params.get("x"));
                //String y = String.join(params.get("y"));
                float y = Float.parseFloat(params.get("y"));
                int r = Integer.parseInt(params.get("r"));

                if (valid.validX(x) && valid.validY(y) && valid.validR(r)) {
                    LocalDateTime endedAt = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    sendJson(String.format(
                            "{\"result\": %b, \"current_time\": \"%s\", \"exec_time\": \"%s\"}",
                            Prov.popod(x, y, r),
                            endedAt.format(formatter),
                            Long.toString(Duration.between(startedAt, endedAt).toMillis())
                    ));
                } else {
                    sendJson("{\"error\": \"invalid data\"}");
                }
            } catch (NumberFormatException e) {
                sendJson("{\"error\": \"wrong query param type\"}");
            } catch (NullPointerException e) {
                sendJson("{\"error\": \"missed necessary query param\"}");
            } catch (Exception e) {
                sendJson(String.format("{\"error\": \"%s\"}", e.toString()));
            }
        }
    }


    private static void sendJson(String jsonDump) {
        System.out.println(String.format(RESPONSE_TEMPLATE, jsonDump.getBytes(StandardCharsets.UTF_8).length, jsonDump));
    }

    private static String readRequestBody() throws IOException {

        // Получаем значение CONTENT_LENGTH из параметров запроса
        String contentLengthHeader = FCGIInterface.request.params.getProperty("CONTENT_LENGTH");
        if (contentLengthHeader == null) {
            throw new IOException("CONTENT_LENGTH header is missing");
        }

        int contentLength;
        try {
            contentLength = Integer.parseInt(contentLengthHeader);
        } catch (NumberFormatException e) {
            throw new IOException("Invalid CONTENT_LENGTH value");
        }

        // Проверяем допустимость длины
        if (contentLength <= 0) {
            throw new IOException("CONTENT_LENGTH is invalid or zero");
        }

        // Читаем тело запроса
        ByteBuffer buffer = ByteBuffer.allocate(contentLength);
        int totalBytesRead = 0;

        // Чтение данных по частям
        while (totalBytesRead < contentLength) {
            int bytesRead = FCGIInterface.request.inStream.read(buffer.array(), totalBytesRead, contentLength - totalBytesRead);
            if (bytesRead == -1) {
                throw new IOException("Unexpected end of input stream");
            }
            totalBytesRead += bytesRead;
        }

        // Конвертируем прочитанные байты в строку
        return new String(buffer.array(), 0, totalBytesRead, StandardCharsets.UTF_8);

        //return "{\"x\":-5,\"y\":2,\"r\":2}";
    }

}
