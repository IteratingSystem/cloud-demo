package org.example.fallback;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class RestTemplateFallback {

    public static ClientHttpResponse handleFallback(HttpRequest request, byte[] body,
                                                    ClientHttpRequestExecution execution, Throwable ex) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatusCode getStatusCode() throws IOException {
                return HttpStatusCode.valueOf(500);
            }


            @Override
            @SuppressWarnings("deprecation")
            public String getStatusText() throws IOException {
                return getStatusCode().toString();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                String msg = "【Fallback】调用失败，URL=" + request.getURI() + "，原因：" + ex.getMessage();
                return new ByteArrayInputStream(msg.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "text/plain;charset=UTF-8");
                return headers;
            }
        };
    }

    public static ClientHttpResponse handleBlock(HttpRequest request, byte[] body,
                                                 ClientHttpRequestExecution execution, BlockException ex) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatusCode getStatusCode() throws IOException {
                return HttpStatusCode.valueOf(429);
            }


            @Override
            @SuppressWarnings("deprecation")
            public String getStatusText() throws IOException {
                return getStatusCode().toString();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                String msg = "【BlockHandler】服务被限流或熔断，URL=" + request.getURI() +
                        "，原因：" + ex.getClass().getSimpleName();
                return new ByteArrayInputStream(msg.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "text/plain;charset=UTF-8");
                return headers;
            }
        };
    }
}