package pl.pjatk.jaz_s30576_nbp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Rates {
    private String no;
    private LocalDate effectiveDate;
    private Double mid;
}