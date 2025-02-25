package uz.pdp.readfrompdfandimage.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class Conf {

  @Scheduled(fixedRateString = "1200000") //20 minut
        public void PingMe(){
            try {
                URL url = new URL("https://fromfiletotext.herokuapp.com/ping");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                log.info("Ping{}, OK.response code{}", url.getHost(), connection.getResponseCode());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
