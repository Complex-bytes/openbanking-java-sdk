Goal
---
<Changes Made by Complex Byte team- Start>
	We tried to use the Get Account List and Get Transaction account APIs to get the transactions history of 06 months (Jan 2020 - Jun 2020) to get  Net Credit amount and Net debit amount.
<Changes Made by Complex Byte team- End>

The aim of the SDK is to demonstrate the flow of Open Banking *AISP/PISP* APIs with the help of Sandbox environment with default TLS configuration or your own configured Open Banking certificates(MATLS).
The SDK can be used by third party providers to integrate and test OB APIs.

The SDK application can run in two different modes, TLS (Basic Authentication) as default, or MATLS (OAuth2). All Open Banking APIs in production use MATLS and therefor it is recommended to use MATLS in production. The SDK is provided with TLS(ClientId/Client Secret) as default to get application quick started. For MATLS you need to obtain Open Banking Transport and Signing Certs. Please refer to [How to run SDK with MATLS](#how-to-run-sdk-with-matls)

### Modules
For maximum flexibility, the SDK is divided into three modules: Core, Remote and Web. Core and Remote are essential modules if you would like to use SDK to build your own microservice. An example of which is provided in the Web Module.

#### Core
<Changes Made by Complex Byte team- Start>
	Added few models in core module.
<Changes Made by Complex Byte team- End>

The core module consists of the model structure for AISP and PISP data. The information returned via the APIs are structured into relevant POJOs for ease of access and readability.

#### Remote
The remote module establishes connection with the APIs. It includes construction of headers, configuring security MATLS for sending requests and accepting responses. The requests are actioned upon and connected to internal servers to fetch the required data.

#### Web
<Changes Made by Complex Byte team- Start>
	We changed in web module to added 01 method "getAccountsWithCreDebDetails" in AispController.java to get account list and transaction history of each account.
	At end of this doc, sample JSON is shared which is created and sent back to UI code as response of "getAccountsWithCreDebDetails" method
<Changes Made by Complex Byte team- End>

The web module acts as an interface connecting the APIs to the Sandbox environment. It houses the controllers of AISP/PISP APIs.

The SDK's default configuration is with Sandbox(in web/resources/application.yml), you don't need to give any credentials or certificates, just run the main class inside web\comm.bankofapis\RestClientApplication directly.
Below are configurable property, in the case you want to use your own.

## Properties
---

##### Client Properties
<Changes Made by Complex Byte team- Start>
	 Updated Clientid and secret id for our team's app. Refer yml.properties.
<Changes Made by Complex Byte team- End>

| *Property*      |         |
| ------------- |---------|
| id          | The client_id is a public identifier for apps. Even though it’s public, it’s best that it isn’t guessable by third parties, so many implementations use something like a 32-character hex string.       |
| redirectUri |     After a user successfully authorizes an application, the authorization server will redirect the user back to the application with either an authorization code or access token in the URL.    |
| financialId      |       It is the unique identifier of the desired financial institution to interact, assigned by the service bureau where the API is provided by a service bureau which uses the same endpoint for multiple institutions.  |
| state      |      The state parameter preserves some state object set by the client in the Authorization request and makes it available to the client in the response.  |
| authorizationUsername      |    The client is providing its identification to IAM, as username.   |
| authorizationPassword      |    The client is providing the password to IAM.   |
| tokenUrl      |    This is a IAM url to get token for AISP/PISP APIs for a given client.   |


##### TLS Properties

|  *Property* |         |
| ------------- |---------|
| alias      |    A client intending to do mutual TLS (for OAuth client authentication and/or to acquire or use certificate-bound tokens) when making a request directly to the authorization server must use the alias URL of the endpoint within the mtls_endpoint_aliases, when present, in preference to the endpoint URL of the same name at top-level of metadata.      |
| truststore      |   Truststores are repositories of security certificates used for TLS encryption. It contains certificates used to verify certificates received as part of TLS handshaking. Provide the truststore location and password     |
| keystore      |   A Java KeyStore is a repository of security certificates – either authorization certificates or public key certificates – plus corresponding private keys, used for instance in SSL encryption. Provide the keystore location and password.     |

##### AISP Properties

|        |     *Property* |         |
| ------------- | ------------- |---------|
|    Target     | context      |   The authentication context and method used when the end user originally authenticated, and will remain unchanged for the JWT access tokens issued within the context of that session      |
|               | baseUri      |   You can request authorization codes and access tokens by appending only the authorize or token endpoints to the Authentication Base URI.      |
|               | audience      |      The audience of a token is the intended recipient of the token.   |

##### PISP Properties

|        |     *Property* |         |
| ------------- | ------------- |---------|
|    Target     | context      |       Context path for domestic payment  |
|               | baseUri      |   You can request authorization codes and access tokens by appending only the authorize or token endpoints to the Authentication Base URI.      |
|               | audience      |      The audience of a token is the intended recipient of the token.   |

---

# SDK example setup
---
## Preliminary Steps

1. Browse to the RBS sandbox https://developer.rbs.useinfinite.io/
2. Register an account and verify email address to login
3. On the dashboard, click on the pre-created demo app (demo-app-...)
4. Under APIs -> Version 3.1.0 of CMA9 Accounts API, click 'Request Access'...
5. Enable 'Allow security' and 'Allow Tpp' to programmatically authorise User consents'


## How to bootup the application

1.    Clone this repository.
2.    Open IntelliJ, Goto File > New > Project from Existing Resources
3.    Select the cloned Repository > Check Import project from external model and Select Maven
4.    Keep on clicking Next till it detects the pom file and then select Finish. The project would be loaded and displayed on the project pane.
5.    Open terminal in the IDE and run *mvn clean install*, upon completion, right click on the project > Goto Maven > Reimport
6.    Run the main method of *web->RestClientApplication*   

      

>#### *Note*
>- For eclipse, follow maven project setup steps from [here](https://javapapers.com/java/import-maven-project-into-eclipse/).
>- To GET/POST the APIs, download [Postman](https://www.postman.com/downloads/).
>- You have to go in Preferences or Settings > General menu in HEADERS column and make sure that Automatically follow redirects is set to ON

#### How to run SDK with MATLS

#### Scripts

Script folder contains helper scripts for working with certificates.

#### Generating JKS files from P12 certificates

Use script 
| Position | Name                      | Description                                                                                |
|----------|---------------------------|--------------------------------------------------------------------------------------------|
| 1        | ENVIRONMENT               | This specifies which environment you are generating the JKS for and controls which signing chain is added to the transport certificate |
| 2        | CERTIFICATE_TYPE          | Is this a ```transport``` or ```signing``` certificate                                     |
| 3        | SOFTWARE_ID               | The software id of this certificate from the OB Directory (must match the CN of the certificate) |
| 4        | INPUT_CERT_P12            | The file name of the input .p12 or .pfx - can be a file path too (e.g. /path/to/cert.p12)  |
| 5        | INPUT_CERT_P12_PASSPHRASE | The passphrase protecting the input .p12 or .pfx                                           |
| 6        | INPUT_CERT_KEY_ID         | The Key ID from the OB Directory for this particular certificate                           |
| 7        | OUTPUT_JKS_NAME           | The file name of the JKS to create - can be a file path too (e.g. /path/to/store.jks)      |
| 8        | OUTPUT_JKS_PASSPHRASE     | The pass phrase to protect the JKS                                                         |

#### Example usage


./generate_keystore_ob_prod.sh prod transport <SOFTWARE_ID> ../path/to/developer.natwest.com_transport.p12 <redacted> _BV7npW7Q38wzTRgMEeKYHK7Ny0 transport.jks <redacted>
```
Once you have generated the JKS file, you can set the keystore/truststore location and password in application.yml file and set the property enableMatls to true.

>**Note: For further documentation and request bodies please visit Bank of APIs [Payments](https://www.bankofapis.com/products/payments/) and [Accounts](https://www.bankofapis.com/products/accounts/)**





Sample JSON created:-

{
    "monthlyTransactionList": {
        "MonthlyTransactionDetails": [
            {
                "Month": "Jan",
                "TotalCredit": "0.0",
                "TotalDebit": "850.0",
                "Net": "-850.0"
            },
            {
                "Month": "Feb",
                "TotalCredit": "0.0",
                "TotalDebit": "450.0",
                "Net": "-450.0"
            },
            {
                "Month": "Mar",
                "TotalCredit": "0.0",
                "TotalDebit": "150.0",
                "Net": "-150.0"
            },
            {
                "Month": "Apr",
                "TotalCredit": "0.0",
                "TotalDebit": "300.0",
                "Net": "-300.0"
            },
            {
                "Month": "May",
                "TotalCredit": "0.0",
                "TotalDebit": "0.0",
                "Net": "0.0"
            },
            {
                "Month": "Jun",
                "TotalCredit": "0.0",
                "TotalDebit": "0.0",
                "Net": "0.0"
            }
        ]
    },
    "AvgNet": "-291.7"
}