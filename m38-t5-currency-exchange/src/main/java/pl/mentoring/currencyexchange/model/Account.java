package pl.mentoring.currencyexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Account {

    @EqualsAndHashCode.Include
    private String accountNumber;

    @EqualsAndHashCode.Exclude
    private MoneyAmount moneyAmount;

    @EqualsAndHashCode.Include
    @JsonIgnore
    public Currency getCurrency() {
        return this.moneyAmount.getCurrency();
    }
}
