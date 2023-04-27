package pl.mentoring.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    private MoneyAmount clientGives;

    private MoneyAmount clientGets;

}
