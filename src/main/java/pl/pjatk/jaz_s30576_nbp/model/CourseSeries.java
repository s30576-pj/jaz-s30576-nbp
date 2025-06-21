package pl.pjatk.jaz_s30576_nbp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@NoArgsConstructor
public class CourseSeries {
    private String table;
    private String currency;
    private String code;
    private List<Rates> rates;
}