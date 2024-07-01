package NasaPackage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static final String nasaUrl = "https://api.nasa.gov/planetary/apod?api_key=eeY6SpQqzbvHpROr2dxLDMB3aLMkswkBly68LCL5";

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectionRequestTimeout(5000)
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        CloseableHttpResponse response = httpClient.execute(new HttpGet(nasaUrl));
        ObjectMapper mapper = new ObjectMapper();

        NasaRequest nasaRequest = mapper.readValue(response.getEntity().getContent(), NasaRequest.class);
        //System.out.println(nasaRequest.getUrl());
        CloseableHttpResponse pictureResponse = httpClient.execute(new HttpGet(nasaRequest.getUrl()));

        String[] arrNameForURL = nasaRequest.getUrl().split("/");
        String fileName = arrNameForURL[arrNameForURL.length - 1];

        HttpEntity entity = pictureResponse.getEntity();

        FileOutputStream fos = new FileOutputStream(fileName);
        entity.writeTo(fos);
        fos.close();

    }
}
