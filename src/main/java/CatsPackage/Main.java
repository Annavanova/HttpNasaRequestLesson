package CatsPackage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {

    public static String URLCats = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        //Настраиваем наш HTTP клиент, который будет отправлять запросы
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectionRequestTimeout(5000)
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        CloseableHttpResponse response = httpClient.execute(new HttpGet(URLCats));

        List<Cats> catsRequest = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
        });
        //catsRequest.forEach(System.out::println);

        catsRequest
                .stream()
                .filter(value -> (value.getUpvotes() != null && Integer.parseInt(value.getUpvotes()) > 0))
                .forEach(System.out::println);
    }
}
