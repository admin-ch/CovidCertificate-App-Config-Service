package ch.admin.bag.covidcertificate.backend.config.shared;

import ch.admin.bag.covidcertificate.backend.config.shared.model.ConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.model.VerifierConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.model.WalletConfigResponse;
import ch.admin.bag.covidcertificate.backend.config.shared.security.signature.JWSMessageConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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

    public static final String PATH_TO_CA_PEM = "classpath:certs/test_ca.pem";
    public static final String PATH_TO_WRONG_CA_PEM = "classpath:certs/wrong_test_ca.pem";

    public final ObjectMapper objectMapper;

    public TestHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ConfigResponse internalToConfigResponse(
            MockHttpServletResponse result,
            MediaType mediaType,
            String pathToCaPem,
            Class<? extends ConfigResponse> clazz)
            throws JsonProcessingException, UnsupportedEncodingException {
        String responseStr = result.getContentAsString(StandardCharsets.UTF_8);
        if (MediaType.APPLICATION_JSON.equalsTypeAndSubtype(mediaType)) {
            return objectMapper.readValue(responseStr, clazz);
        } else if (JWSMessageConverter.JWS_MEDIA_TYPE.equalsTypeAndSubtype(mediaType)) {
            // verify cert chain
            Jws<Claims> claimsJws =
                    Jwts.parserBuilder()
                            .setSigningKeyResolver(new JwsKeyResolver(pathToCaPem))
                            .build()
                            .parseClaimsJws(responseStr);
            return objectMapper.convertValue(claimsJws.getBody(), clazz);
        } else {
            throw new RuntimeException("unexpected media type: " + mediaType);
        }
    }

    public ConfigResponse toConfigResponse(
            MockHttpServletResponse result, MediaType mediaType, String pathToCaPem)
            throws JsonProcessingException, UnsupportedEncodingException {
        return internalToConfigResponse(result, mediaType, pathToCaPem, ConfigResponse.class);
    }

    public WalletConfigResponse toWalletConfigResponse(
            MockHttpServletResponse result, MediaType mediaType, String pathToCaPem)
            throws JsonProcessingException, UnsupportedEncodingException {
        return (WalletConfigResponse)
                internalToConfigResponse(
                        result, mediaType, pathToCaPem, WalletConfigResponse.class);
    }

    public VerifierConfigResponse toVerifierConfigResponse(
            MockHttpServletResponse result, MediaType mediaType, String pathToCaPem)
            throws JsonProcessingException, UnsupportedEncodingException {
        return (VerifierConfigResponse)
                internalToConfigResponse(
                        result, mediaType, pathToCaPem, VerifierConfigResponse.class);
    }
}
