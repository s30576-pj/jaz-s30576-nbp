package pl.pjatk.jaz_s30576_nbp.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import pl.pjatk.jaz_s30576_nbp.model.CourseSeries;
import pl.pjatk.jaz_s30576_nbp.model.QueryLog;
import pl.pjatk.jaz_s30576_nbp.model.Rates;
import pl.pjatk.jaz_s30576_nbp.repository.QueryLogRepository;

import java.text.DecimalFormat;
import java.time.LocalDate;

@Service
public class CurrencyService {
    private final QueryLogRepository queryLogRepository;
    private final RestTemplate restTemplate;

    public CurrencyService(QueryLogRepository queryLogRepository, RestTemplate restTemplate) {
        this.queryLogRepository = queryLogRepository;
        this.restTemplate = restTemplate;
    }

    public String calculateRate(String currency, LocalDate startDate, LocalDate endDate) {
        try {
            CourseSeries courseSeries = restTemplate.getForEntity("https://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/" + startDate + "/" + endDate + "/", CourseSeries.class).getBody();

            double averageRate = courseSeries.getRates().stream()
                    .mapToDouble(Rates::getMid)
                    .average()
                    .orElse(0.0);

            QueryLog queryLog = new QueryLog();
            queryLog.setCurrency(currency);
            queryLog.setStartDate(startDate);
            queryLog.setEndDate(endDate);

            DecimalFormat df = new DecimalFormat("#.####");
            queryLog.setCalculateRate(df.format(averageRate));

            queryLogRepository.save(queryLog);

//            DecimalFormat df = new DecimalFormat("#.####");
            return df.format(averageRate);

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else {
                throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
            }
        } catch (HttpServerErrorException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}