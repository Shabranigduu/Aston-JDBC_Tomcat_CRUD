package util;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Scanner;

public class ServletUtil {
    public static String getBody(HttpServletRequest req) throws IOException {
        Scanner scanner = new Scanner(req.getInputStream());
        String body = scanner.useDelimiter("\\A").next();
        scanner.close();
        return body;
    }
}
