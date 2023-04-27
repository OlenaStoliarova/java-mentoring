package pl.mentoring.currencyexchange.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = BuyRequest.class, name = "buy"),
    @Type(value = SellRequest.class, name = "sell")
})
public abstract class ExchangeRequest {

    private long id;

    private AccountHolder client;

    private Account fromAccount;

    private Account toAccount;
}
