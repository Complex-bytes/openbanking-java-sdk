package com.bankofapis.core.model.accounts;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OBReadFormattedTransactionsDetails {

    @JsonProperty("AccountId")
    private String accountId;

    @JsonProperty("AccountSubType")
    private String accountSubType;

    @JsonProperty("Nickname")
    private String nickname;

    @JsonProperty("Identification")
    private String identification;

    @JsonProperty("AvgNet")
    private String avgNet;

    public String getAvgNet() {
        return avgNet;
    }

    public void setAvgNet(String avgNet) {
        this.avgNet = avgNet;
    }

    private OBReadMonthlyTransactionList monthlyTransactionList;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountSubType() {
        return accountSubType;
    }

    public void setAccountSubType(String accountSubType) {
        this.accountSubType = accountSubType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public OBReadMonthlyTransactionList getMonthlyTransactionList() {
        return monthlyTransactionList;
    }

    public void setMonthlyTransactionList(OBReadMonthlyTransactionList monthlyTransactionList) {
        this.monthlyTransactionList = monthlyTransactionList;
    }
}
