package com.bankofapis.core.model.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OBReadMonthlyTransactionList {

    @JsonProperty("MonthlyTransactionDetails")
    private List<OBReadMonthlyTransactionDetails> monthlyTransactionDetailsList = null;

    public List<OBReadMonthlyTransactionDetails> getMonthlyTransactionDetailsList() {
        return monthlyTransactionDetailsList;
    }

    public void setMonthlyTransactionDetailsList(List<OBReadMonthlyTransactionDetails> monthlyTransactionDetailsList) {
        this.monthlyTransactionDetailsList = monthlyTransactionDetailsList;
    }
}