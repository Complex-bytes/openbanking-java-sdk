package com.bankofapis.web.controller;

import com.bankofapis.core.model.accounts.*;
import com.bankofapis.web.service.AispService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.text.DecimalFormat;
import java.util.*;

import static com.bankofapis.core.model.common.Constants.CONSENT_ID_HEADER;
import static com.bankofapis.remote.common.Endpoints.*;

@RestController
@RequestMapping("/open-banking/*/aisp")
public class AispController {

    private AispService aispService;

    @Autowired
    public AispController(AispService aispService) {
        this.aispService = aispService;
    }

    @GetMapping(value = OB_JOURNEY_INIT)
    public String initialize(HttpServletResponse response) {
        String initialize = aispService.initialize();
        return initialize;
    }

    @PostMapping(value = ACCOUNT_ACCESS_CONSENT_ENDPOINT)
    public OBReadDomesticConsentResponse aispConsentSetup(
        @RequestBody OBReadDomesticConsent obReadDataDomesticConsent) {
        return aispService.createAispConsent(obReadDataDomesticConsent);
    }

    @GetMapping(value = AUTHORIZATION_OAUTH2_ENDPOINT)
    public String aispConsentAuthUrl(HttpServletRequest request) {
        return aispService.createAuthorizeUri(request.getParameter(CONSENT_ID_HEADER));
    }


    @GetMapping(value = ACCOUNT_LIST_TRANSACTIONS_ENDPOINT)
    public OBReadFormattedTransactionsDetails  getAccountsWithCreDebDetails() {
        OBReadFormattedTransactionsDetails formattedTransactionsDetails = new OBReadFormattedTransactionsDetails();

        OBReadDataResponse<OBReadAccountList> accountListResponse = aispService.getAccountResponse();
        DecimalFormat df = new DecimalFormat("#.00");
            String validYear = "2020";
            double janTotalCreditAmt = 0;
        double janTotalDebitAmt = 0;

        double febTotalCreditAmt = 0;
        double febTotalDebitAmt = 0;
        double marTotalCreditAmt = 0;
        double marTotalDebitAmt = 0;

        double aprTotalCreditAmt = 0;
        double aprTotalDebitAmt = 0;

        double mayTotalCreditAmt = 0;
        double mayTotalDebitAmt = 0;

        double junTotalCreditAmt = 0;
        double junTotalDebitAmt = 0;

        double avgNetAmt = 0;
        OBReadMonthlyTransactionDetails janMonthlyTransactionDetails = new OBReadMonthlyTransactionDetails();
        OBReadMonthlyTransactionDetails febMonthlyTransactionDetails = new OBReadMonthlyTransactionDetails();
        OBReadMonthlyTransactionDetails marMonthlyTransactionDetails = new OBReadMonthlyTransactionDetails();
        OBReadMonthlyTransactionDetails aprMonthlyTransactionDetails = new OBReadMonthlyTransactionDetails();
        OBReadMonthlyTransactionDetails mayMonthlyTransactionDetails = new OBReadMonthlyTransactionDetails();
        OBReadMonthlyTransactionDetails junMonthlyTransactionDetails = new OBReadMonthlyTransactionDetails();


        formattedTransactionsDetails.setAvgNet(Double.toString(avgNetAmt));

        janMonthlyTransactionDetails.setNet(Double.toString(janTotalCreditAmt));
        janMonthlyTransactionDetails.setTotalCredit(Double.toString(janTotalCreditAmt));
        janMonthlyTransactionDetails.setTotalDebit(Double.toString(janTotalCreditAmt));
        janMonthlyTransactionDetails.setMonth("Jan");

        febMonthlyTransactionDetails.setNet(Double.toString(janTotalCreditAmt));
        febMonthlyTransactionDetails.setTotalCredit(Double.toString(janTotalCreditAmt));
        febMonthlyTransactionDetails.setTotalDebit(Double.toString(janTotalCreditAmt));
        febMonthlyTransactionDetails.setMonth("Feb");

        marMonthlyTransactionDetails.setNet(Double.toString(janTotalCreditAmt));
        marMonthlyTransactionDetails.setTotalCredit(Double.toString(janTotalCreditAmt));
        marMonthlyTransactionDetails.setTotalDebit(Double.toString(janTotalCreditAmt));
        marMonthlyTransactionDetails.setMonth("Mar");

        aprMonthlyTransactionDetails.setNet(Double.toString(janTotalCreditAmt));
        aprMonthlyTransactionDetails.setTotalCredit(Double.toString(janTotalCreditAmt));
        aprMonthlyTransactionDetails.setTotalDebit(Double.toString(janTotalCreditAmt));
        aprMonthlyTransactionDetails.setMonth("Apr");

        mayMonthlyTransactionDetails.setNet(Double.toString(janTotalCreditAmt));
        mayMonthlyTransactionDetails.setTotalCredit(Double.toString(janTotalCreditAmt));
        mayMonthlyTransactionDetails.setTotalDebit(Double.toString(janTotalCreditAmt));
        mayMonthlyTransactionDetails.setMonth("May");

        junMonthlyTransactionDetails.setNet(Double.toString(janTotalCreditAmt));
        junMonthlyTransactionDetails.setTotalCredit(Double.toString(janTotalCreditAmt));
        junMonthlyTransactionDetails.setTotalDebit(Double.toString(janTotalCreditAmt));
        junMonthlyTransactionDetails.setMonth("Jun");

        for (OBReadAccountInformation accountInfo : accountListResponse.getData().getAccount()){
            OBReadDataResponse<OBReadTransactionList> transactionResponse = aispService.getTransactionsById(accountInfo.getAccountId());
            for(OBReadTransaction transInfo : transactionResponse.getData().getTransactionList()){
                    String year = transInfo.getBookingDateTime().substring(0,4);
                    String month = transInfo.getBookingDateTime().substring(5,7);

                if(year.equalsIgnoreCase(validYear) || year.equalsIgnoreCase(validYear)) {
                    switch(month){
                        case "01":
                            if ("Credit".equalsIgnoreCase(transInfo.getCreditDebitIndicator()) || "CRDT".equalsIgnoreCase(transInfo.getCreditDebitIndicator())) {
                                janTotalCreditAmt = janTotalCreditAmt + Double.parseDouble(transInfo.getAmount().getAmount());
                            } else if ("Debit".equalsIgnoreCase(transInfo.getCreditDebitIndicator()) || "DBIT".equalsIgnoreCase(transInfo.getCreditDebitIndicator())) {
                                janTotalDebitAmt = janTotalDebitAmt + Double.parseDouble(transInfo.getAmount().getAmount());
                            }
                            janMonthlyTransactionDetails.setNet(df.format(janTotalCreditAmt-janTotalDebitAmt));

                            janMonthlyTransactionDetails.setTotalCredit(df.format(janTotalCreditAmt));
                            janMonthlyTransactionDetails.setTotalDebit(df.format(janTotalDebitAmt));
                            janMonthlyTransactionDetails.setMonth("Jan");
                            break;

                        case "02":
                            if ("Credit".equalsIgnoreCase(transInfo.getCreditDebitIndicator()) || "CRDT".equalsIgnoreCase(transInfo.getCreditDebitIndicator())) {
                                febTotalCreditAmt = febTotalCreditAmt+Double.parseDouble(transInfo.getAmount().getAmount());
                        } else if ("Debit".equalsIgnoreCase(transInfo.getCreditDebitIndicator()) || "DBIT".equalsIgnoreCase(transInfo.getCreditDebitIndicator())) {
                                febTotalDebitAmt = febTotalDebitAmt+Double.parseDouble(transInfo.getAmount().getAmount());
                            }
                            febMonthlyTransactionDetails.setNet(df.format(febTotalCreditAmt-febTotalDebitAmt));
                            febMonthlyTransactionDetails.setTotalCredit(df.format(febTotalCreditAmt));
                            febMonthlyTransactionDetails.setTotalDebit(df.format(febTotalDebitAmt));
                            febMonthlyTransactionDetails.setMonth("Feb");
                            break;

                        case "03":
                            if ("Credit".equalsIgnoreCase(transInfo.getCreditDebitIndicator()) || "CRDT".equalsIgnoreCase(transInfo.getCreditDebitIndicator())) {
                                marTotalCreditAmt = marTotalCreditAmt+Double.parseDouble(transInfo.getAmount().getAmount());
                            } else if ("Debit".equalsIgnoreCase(transInfo.getCreditDebitIndicator()) || "DBIT".equalsIgnoreCase(transInfo.getCreditDebitIndicator())) {
                                marTotalDebitAmt = marTotalDebitAmt+Double.parseDouble(transInfo.getAmount().getAmount());
                            }
                            marMonthlyTransactionDetails.setNet(df.format(marTotalCreditAmt-marTotalDebitAmt));

                            marMonthlyTransactionDetails.setTotalCredit(df.format(marTotalCreditAmt));
                            marMonthlyTransactionDetails.setTotalDebit(df.format(marTotalDebitAmt));
                            marMonthlyTransactionDetails.setMonth("Mar");
                            break;

                        case "04":
                            if ("Credit".equalsIgnoreCase(transInfo.getCreditDebitIndicator()) || "CRDT".equalsIgnoreCase(transInfo.getCreditDebitIndicator())) {
                                aprTotalCreditAmt = aprTotalCreditAmt+Double.parseDouble(transInfo.getAmount().getAmount());
                            } else if ("Debit".equalsIgnoreCase(transInfo.getCreditDebitIndicator()) || "DBIT".equalsIgnoreCase(transInfo.getCreditDebitIndicator())) {
                                aprTotalDebitAmt = aprTotalDebitAmt+Double.parseDouble(transInfo.getAmount().getAmount());
                            }
                            aprMonthlyTransactionDetails.setNet(df.format(aprTotalCreditAmt-aprTotalDebitAmt));
                            aprMonthlyTransactionDetails.setTotalCredit(df.format(aprTotalCreditAmt));
                            aprMonthlyTransactionDetails.setTotalDebit(df.format(aprTotalDebitAmt));
                            aprMonthlyTransactionDetails.setMonth("Apr");
                            break;

                        case "05":
                            if ("Credit".equalsIgnoreCase(transInfo.getCreditDebitIndicator()) || "CRDT".equalsIgnoreCase(transInfo.getCreditDebitIndicator())) {
                                mayTotalCreditAmt = mayTotalCreditAmt+Double.parseDouble(transInfo.getAmount().getAmount());
                            } else if ("Debit".equalsIgnoreCase(transInfo.getCreditDebitIndicator()) || "DBIT".equalsIgnoreCase(transInfo.getCreditDebitIndicator())) {
                                mayTotalDebitAmt = mayTotalDebitAmt+Double.parseDouble(transInfo.getAmount().getAmount());
                            }
                            mayMonthlyTransactionDetails.setNet(df.format(mayTotalCreditAmt-mayTotalDebitAmt));
                            mayMonthlyTransactionDetails.setTotalCredit(df.format(mayTotalCreditAmt));
                            mayMonthlyTransactionDetails.setTotalDebit(df.format(mayTotalDebitAmt));
                            mayMonthlyTransactionDetails.setMonth("May");
                            break;

                        case "06":
                            if ("Credit".equalsIgnoreCase(transInfo.getCreditDebitIndicator()) || "CRDT".equalsIgnoreCase(transInfo.getCreditDebitIndicator())) {
                                junTotalCreditAmt = junTotalCreditAmt+Double.parseDouble(transInfo.getAmount().getAmount());
                            } else if ("Debit".equalsIgnoreCase(transInfo.getCreditDebitIndicator()) || "DBIT".equalsIgnoreCase(transInfo.getCreditDebitIndicator())) {
                                junTotalDebitAmt = junTotalDebitAmt+Double.parseDouble(transInfo.getAmount().getAmount());
                            }
                            junMonthlyTransactionDetails.setNet(df.format(junTotalCreditAmt-junTotalDebitAmt));
                            junMonthlyTransactionDetails.setTotalCredit(df.format(junTotalCreditAmt));
                            junMonthlyTransactionDetails.setTotalDebit(df.format(junTotalDebitAmt));
                            junMonthlyTransactionDetails.setMonth("Jun");
                            break;
                    }
                }
            }
        }
        List<OBReadMonthlyTransactionDetails> monthlyTransactionDetailsList = new ArrayList<OBReadMonthlyTransactionDetails>();
        monthlyTransactionDetailsList.add(janMonthlyTransactionDetails);
        monthlyTransactionDetailsList.add(febMonthlyTransactionDetails);
        monthlyTransactionDetailsList.add(marMonthlyTransactionDetails);
        monthlyTransactionDetailsList.add(aprMonthlyTransactionDetails);
        monthlyTransactionDetailsList.add(mayMonthlyTransactionDetails);
        monthlyTransactionDetailsList.add(junMonthlyTransactionDetails);

        OBReadMonthlyTransactionList monthlyTransactionlist =new OBReadMonthlyTransactionList();
        monthlyTransactionlist.setMonthlyTransactionDetailsList(monthlyTransactionDetailsList);
        formattedTransactionsDetails.setMonthlyTransactionList(monthlyTransactionlist);
        double janNet = Double.parseDouble(janMonthlyTransactionDetails.getNet());
        double febNet = Double.parseDouble(febMonthlyTransactionDetails.getNet());
        double marNet = Double.parseDouble(marMonthlyTransactionDetails.getNet());
        double aprNet = Double.parseDouble(aprMonthlyTransactionDetails.getNet());
        double mayNet = Double.parseDouble(mayMonthlyTransactionDetails.getNet());
        double junNet = Double.parseDouble(junMonthlyTransactionDetails.getNet());
        formattedTransactionsDetails.setAvgNet(df.format((janNet+febNet+marNet+aprNet+mayNet+junNet)/6));
        return formattedTransactionsDetails;
    }


    @GetMapping(value = ACCOUNT_LIST_ENDPOINT)
    public OBReadDataResponse<OBReadAccountList> getAccounts() {
        return aispService.getAccountResponse();
    }

    @GetMapping(value = ACCOUNT_ID_ENDPOINT)
    public OBReadDataResponse<OBReadAccountList> getAccountById(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getAccountById(accountId);
    }

    @GetMapping(value = ACCOUNT_ID_BALANCES_ENDPOINT)
    public OBReadDataResponse<OBReadBalanceList> getBalance(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getBalanceById(accountId);
    }

    @GetMapping(value = ACCOUNT_ID_TRANSACTIONS_ENDPOINT)
    public OBReadDataResponse<OBReadTransactionList> getTransactions(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getTransactionsById(accountId);
    }

    @GetMapping(value = ACCOUNT_ID_BENEFICIARIES_ENDPOINT)
    public OBReadDataResponse<OBReadBeneficiaryList> getBeneficiaries(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getBeneficiariesById(accountId);
    }

    @GetMapping(value = ACCOUNT_ID_DIRECT_DEBITS_ENDPOINT)
    public OBReadDataResponse<OBReadDirectDebitList> getDirectDebitsById(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getDirectDebitsById(accountId);
    }

    @GetMapping(value = ACCOUNT_ID_STANDING_ORDERS_ENDPOINT)
    public OBReadDataResponse<OBReadStandingOrderList> getStandingOrderById(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getStandingOrdersById(accountId);
    }

    @GetMapping(value = ACCOUNT_ID_PRODUCT_ENDPOINT)
    public OBReadDataResponse<OBReadProductList> getProductById(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getProductById(accountId);
    }
}