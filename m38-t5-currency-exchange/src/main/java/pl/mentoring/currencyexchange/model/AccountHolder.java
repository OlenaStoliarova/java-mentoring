package pl.mentoring.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolder {

    private long id;

    private String name;

    private Map<String, Account> accounts;
}
