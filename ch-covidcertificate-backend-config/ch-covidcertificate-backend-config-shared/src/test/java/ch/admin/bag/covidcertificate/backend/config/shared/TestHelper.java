package ch.admin.bag.covidcertificate.backend.config.shared;

import ch.admin.bag.covidcertificate.backend.config.shared.model.ConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.security.signature.JWSMessageConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestHelper {
    public static final Map<String, String> SECURITY_HEADERS =
            Map.of(
                    "X-Content-Type-Options",
                    "nosniff",
                    "X-Frame-Options",
                    "DENY",
                    "X-Xss-Protection",
                    "1; mode=block");

    public static final String PATH_TO_CA_PEM = "classpath:certs/ca.pem";
    public static final String PATH_TO_WRONG_CA_PEM = "classpath:certs/wrong.pem";

    public final ObjectMapper objectMapper;

    public TestHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ConfigResponse toConfigResponse(
            MockHttpServletResponse result, MediaType mediaType, String pathToCaPem)
            throws JsonProcessingException, UnsupportedEncodingException {
        String responseStr = result.getContentAsString(Charset.forName("utf-8"));
        if (MediaType.APPLICATION_JSON.equalsTypeAndSubtype(mediaType)) {
            return objectMapper.readValue(responseStr, ConfigResponse.class);
        } else if (JWSMessageConverter.JWS_MEDIA_TYPE.equalsTypeAndSubtype(mediaType)) {
            // verify cert chain
            Jws<Claims> claimsJws =
                    Jwts.parserBuilder()
                            .setSigningKeyResolver(new JwsKeyResolver(pathToCaPem))
                            .build()
                            .parseClaimsJws(responseStr);
            return objectMapper.convertValue(claimsJws.getBody(), ConfigResponse.class);
        } else {
            throw new RuntimeException("unexpected media type: " + mediaType);
        }
    }
}
