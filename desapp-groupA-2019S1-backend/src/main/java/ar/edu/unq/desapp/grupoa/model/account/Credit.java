package ar.edu.unq.desapp.grupoa.model.account;

import ar.edu.unq.desapp.grupoa.model.user.User;
import java.time.LocalDateTime;

public class Credit {

    private final User user;
    private final Integer quotasToPay;
    private final Boolean hasDefaulted;
    private final LocalDateTime date;

    public Credit(User user, Integer quotasToPay) {
        this.user=user;
        this.quotasToPay=quotasToPay;
        this.hasDefaulted = user.hasDefaulted();
        this.date =  LocalDateTime.now();
    }

    public Integer getQuotasToPay() {
        return quotasToPay;
    }
}
