# Table of Contents
- [Overview](#overview)
- [Runbook](#runbook)
- [Latest build and release](#latest-build-and-release)
- [Local set up](#local-set-up)
    * [With Wildfly](#with-wildfly)
- [Database](#database)
- [Local SQL Accounts](#local-sql-accounts)
- [Integrations](#integrations)

## Overview 
Liferay DXP is a framework of java application with react js library as its front end managed by OSGi modules. Customer and Broker Portal is built using Liferay DXP framework.
Producer and Insured domains are two themes customized to serve Customer and Broker users. 

## Runbook
- [Runbook](https://churchmutual.sharepoint.com/sites/itdepartment/AppDocumentationHub/_layouts/15/Doc.aspx?sourcedoc=%7B29BE3848-705E-4D24-A3D8-EDEF6D7991BB%7D&file=Producer%20and%20Insured%20Portal%20-%20Knowledge%20Transfer%20-%20CMICRunbook.docx&action=default&mobileredirect=true&DefaultItemOpen=1)

## Latest build and release
   - [Build](https://dev.azure.com/ChurchMutual/Marketing/_build/results?buildId=8820)
   - [Release](https://dev.azure.com/ChurchMutual/Marketing/_release?_a=releases&view=mine&definitionId=16)
   
## Local set up 
   #### With Wildfly
   - Clone repository: [CMICPortal repo](https://ChurchMutual@dev.azure.com/ChurchMutual/Marketing/_git/CMICPortal)
        
   - Import into IDE: Eclipse or IntelliJ 
        
   - In terminal go to CMICPortal folder. Run below command to build CMICPortal.
      * gradlew.bat clean initBundle
      * gradlew.bat clean deploy
   		
   - Run standalone.bat from /CMICPortal/bundles/wildfly-16.0.0.Final/bin as an administrator
     - Note: Subsequent runs of the portal can be performed directly through the IDE, but the first run must be
       initiated by this standalone.bat file; otherwise, the portal won't load and will instead repeatedly redirect to
       localhost:8080/home, which continuously returns 404s
   
   - go to http://localhost:8080 and the page will redirect to B2C account sign up/sign in page.
   
   #### Changing Themes Using IntelliJ
 After making appropriate updates to the files within a theme submodule (ie, cmic-insured-theme):
   - If there are any instances of the portal currently running, stop them before proceeding
   - Right-click on the theme submodule, and then click Liferay -> Deploy
   - Run `standalone.bat` from `/CMICPortal/bundles/wildfly-16.0.0.Final/bin` for the initial execution
     - Note: If the portal is instead initiated via a Liferay Server run configuration in IntelliJ, the theme changes
       will not take effect, and the preceding steps will need to be repeated

## Database
   * DEV:     az-d-sql-inst-0001.a6e5ca082a4f.database.windows.net
   * TEST:    az-t-sql-inst-0001.83602996417f.database.windows.net
   * PREPROD: az-s-sql-inst-0001.b61a4257efd8.database.windows.net
   * PROD:    az-p-sql-inst-0001.d8fddbb5d078.database.windows.net
  
## Local SQL Accounts
   * sql_D_Liferay
   * sql_Q_Liferay
   * sql_S_Liferay
   * sql_P_Liferay
        
## Integrations
 - [B2C Account set up](https://churchmutualinsurancenp.b2clogin.com/churchmutualinsurancenp.onmicrosoft.com/oauth2/v2.0/authorize?p=B2C_1A_Liferay_edit_profile&client_id=d5c0fba6-f65a-492d-924b-d7f33cda6a4e&nonce=defaultNonce&redirect_uri=https%3A%2F%2Fdev-portal.churchmutual.com%2Fc%2Fportal%2Flogin%2Fopenidconnect&scope=openid&response_type=id_token&prompt=login) 
 - [CMIC Services](https://dev.azure.com/ChurchMutual/Marketing/_wiki/wikis/Marketing%20and%20Sales/15/Integrations)
 