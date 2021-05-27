package ch.admin.bag.covidcertificate.backend.config.shared;

import java.util.Map;

public class TestHelper {
    public static final Map<String, String> SECURITY_HEADERS =
            Map.of(
                    "X-Content-Type-Options",
                    "nosniff",
                    "X-Frame-Options",
                    "DENY",
                    "X-Xss-Protection",
                    "1; mode=block");
}
