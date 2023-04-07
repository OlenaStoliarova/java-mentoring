package pl.mentoring.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"isNational"})
public class Currency {

    private String code;
    private Boolean isNational;
}
