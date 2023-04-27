package pl.mentoring.currencyexchange.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("sell")
public class SellRequest extends ExchangeRequest {

    private MoneyAmount amountToSell;

    public SellRequest(long id, AccountHolder client, Account fromAccount, Account toAccount, MoneyAmount amountToSell) {
        super(id, client, fromAccount, toAccount);
        this.amountToSell = amountToSell;
    }

    public SellRequest() {
    }
}
