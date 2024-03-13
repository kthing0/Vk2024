package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/albums/*")
public class ApiAlbumsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = "https://jsonplaceholder.typicode.com/posts" + request.getPathInfo();
        String result = performRequest(new HttpGet(url));
        response.getWriter().write(result);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = "https://jsonplaceholder.typicode.com/posts";
        String requestBody = extractRequestBody(request);
        String result = performRequest(new HttpPost(url), requestBody);
        response.getWriter().write(result);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = "https://jsonplaceholder.typicode.com/posts" + request.getPathInfo();
        String requestBody = extractRequestBody(request);
        String result = performRequest(new HttpPut(url), requestBody);
        response.getWriter().write(result);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = "https://jsonplaceholder.typicode.com/posts" + request.getPathInfo();
        String result = performRequest(new HttpDelete(url));
        response.getWriter().write(result);
    }

    private String performRequest(HttpGet request) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(request);
        HttpEntity entity = httpResponse.getEntity();
        return EntityUtils.toString(entity);
    }

    private String performRequest(HttpPost request, String requestBody) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        request.setEntity(new StringEntity(requestBody));
        HttpResponse httpResponse = httpClient.execute(request);
        HttpEntity entity = httpResponse.getEntity();
        return EntityUtils.toString(entity);
    }

    private String performRequest(HttpPut request, String requestBody) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        request.setEntity(new StringEntity(requestBody));
        HttpResponse httpResponse = httpClient.execute(request);
        HttpEntity entity = httpResponse.getEntity();
        return EntityUtils.toString(entity);
    }

    private String performRequest(HttpDelete request) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(request);
        HttpEntity entity = httpResponse.getEntity();
        return EntityUtils.toString(entity);
    }

    private String extractRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody.toString();
    }
}
