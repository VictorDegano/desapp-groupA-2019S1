package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.account.Credit;


import java.time.LocalDateTime;

public class CreditDTO {

    private  UserDTO user;
    private  Integer quotasToPay;
    private  Boolean hasDefaulted;
    private  LocalDateTime date;

    public  CreditDTO(){}

    public CreditDTO(UserDTO user, Integer quotasToPay, Boolean hasDefaulted, LocalDateTime date) {
        this.user = user;
        this.quotasToPay = quotasToPay;
        this.hasDefaulted = hasDefaulted;
        this.date = date;
    }

    public static CreditDTO from(Credit credit) {
        return new CreditDTO(
                UserDTO.from(credit.getUser()),
                credit.getQuotasToPay(),
                credit.getHasDefaulted(),
                credit.getDate());
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getQuotasToPay() {
        return quotasToPay;
    }

    public void setQuotasToPay(Integer quotasToPay) {
        this.quotasToPay = quotasToPay;
    }

    public Boolean getHasDefaulted() {
        return hasDefaulted;
    }

    public void setHasDefaulted(Boolean hasDefaulted) {
        this.hasDefaulted = hasDefaulted;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
