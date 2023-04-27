package pl.mentoring.currencyexchange.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("buy")
public class BuyRequest extends ExchangeRequest {

    private MoneyAmount amountToBuy;

    public BuyRequest(long id, AccountHolder client, Account fromAccount, Account toAccount, MoneyAmount amountToBuy) {
        super(id, client, fromAccount, toAccount);
        this.amountToBuy = amountToBuy;
    }

    public BuyRequest() {
    }
}
