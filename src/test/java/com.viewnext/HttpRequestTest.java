package com.viewnext;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {


    @Test
    void contextLoads() {
    }

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testOneSearchStartDateAndProductIdAndBrandId() throws Exception {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ms-prices/api/v1/prices/search?startDate=2020-06-14 10:00:00&productId=35455&brandId=1",
                String.class)).contains("[]");
    }

    @Test
    public void testTwoSearchStartDateAndProductIdAndBrandId() throws Exception {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ms-prices/api/v1/prices/search?startDate=2020-06-14 16:00:00&productId=35455&brandId=1",
                String.class)).contains("[]");
    }

    @Test
    public void testThreeeSearchStartDateAndProductIdAndBrandId() throws Exception {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ms-prices/api/v1/prices/search?startDate=2020-06-14 21:00:00&productId=35455&brandId=1",
                String.class)).contains("[]");
    }
    @Test
    public void testFourSearchStartDateAndProductIdAndBrandId() throws Exception {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ms-prices/api/v1/prices/search?startDate=2020-06-16 21:00:00&productId=35455&brandId=1",
                String.class)).contains("[]");
    }
    @Test
    public void testFiveSearchStartDateAndProductIdAndBrandId() throws Exception {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ms-prices/api/v1/prices/search?startDate=2020-06-14 10:00:00&productId=35455&brandId=1",
                String.class)).contains("[]");
    }

}
