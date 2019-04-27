package ar.edu.unq.desapp.grupoa.model.user.account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static ar.edu.unq.desapp.grupoa.model.utils.StreamUtils.repeat;

public class Loan {

    List<Quota> quotas;

    public Loan(){
       this.quotas = new ArrayList<>();
       repeat(5,()-> quotas.add(new Quota(200)));
    }


    public List<Quota> quotas() {
        return this.quotas;
    }

    public Integer debt() {
        return quotas.stream().flatMapToInt(
                quota -> IntStream.of(quota.amount()))
                .sum();
    }
}
