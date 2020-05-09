package example.micronaut;

import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.HttpStatusType;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTest {

    @Test
    public void testNameSupplied() throws Exception {
        HttpRequestMessage<Optional<String>> request = new MockHttpRequestMessage(Collections.singletonMap("name", "sergio"));
        try (Function function = new Function()) {
            assertEquals("Sergio", function.echo(request, null));
        }
    }

    @Test
    public void testNoNameParamSupplied() throws Exception {
        HttpRequestMessage<Optional<String>> request = new MockHttpRequestMessage(Collections.emptyMap());
        try (Function function = new Function()) {
            assertEquals("The supplied name must be not blank", function.echo(request, null));
        }
    }

    static class MockHttpRequestMessage implements HttpRequestMessage<Optional<String>> {

        private final Map<String, String> queryParameters;
        public MockHttpRequestMessage(Map<String, String> queryParameters) {
            this.queryParameters = queryParameters;
        }

        @Override
        public URI getUri() {
            return URI.create("/");
        }

        @Override
        public HttpMethod getHttpMethod() {
            return HttpMethod.GET;
        }

        @Override
        public Map<String, String> getHeaders() {
            return null;
        }

        @Override
        public Map<String, String> getQueryParameters() {
            return queryParameters;
        }

        @Override
        public Optional<String> getBody() {
            return Optional.empty();
        }

        @Override
        public HttpResponseMessage.Builder createResponseBuilder(HttpStatus status) {
            return null;
        }

        @Override
        public HttpResponseMessage.Builder createResponseBuilder(HttpStatusType status) {
            return null;
        }
    }
}
