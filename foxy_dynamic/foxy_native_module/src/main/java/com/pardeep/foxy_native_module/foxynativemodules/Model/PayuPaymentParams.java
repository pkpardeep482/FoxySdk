package com.pardeep.foxy_native_module.foxynativemodules.Model;

public class PayuPaymentParams {
    String key;
    String amount;
    String productInfo;
    String firstName;
    String email;
    String txnId;
    String sUrl;
    String fUrl;
    String environment;
    String UserCredentials;
    String phoneNumber;
    String offerKey;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getsUrl() {
        return sUrl;
    }

    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    }

    public String getfUrl() {
        return fUrl;
    }

    public void setfUrl(String fUrl) {
        this.fUrl = fUrl;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getUserCredentials() {
        return UserCredentials;
    }

    public void setUserCredentials(String userCredentials) {
        UserCredentials = userCredentials;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOfferKey() {
        return offerKey;
    }

    public void setOfferKey(String offerKey) {
        this.offerKey = offerKey;
    }

    public String getPaymentRelatedDetailsHash() {
        return paymentRelatedDetailsHash;
    }

    public void setPaymentRelatedDetailsHash(String paymentRelatedDetailsHash) {
        this.paymentRelatedDetailsHash = paymentRelatedDetailsHash;
    }

    public String getDeleteUserCardHash() {
        return deleteUserCardHash;
    }

    public void setDeleteUserCardHash(String deleteUserCardHash) {
        this.deleteUserCardHash = deleteUserCardHash;
    }

    public String getOfferHash() {
        return offerHash;
    }

    public void setOfferHash(String offerHash) {
        this.offerHash = offerHash;
    }

    public String getVASForMobileSDKHash() {
        return VASForMobileSDKHash;
    }

    public void setVASForMobileSDKHash(String VASForMobileSDKHash) {
        this.VASForMobileSDKHash = VASForMobileSDKHash;
    }

    public String getSaveUserCardHash() {
        return saveUserCardHash;
    }

    public void setSaveUserCardHash(String saveUserCardHash) {
        this.saveUserCardHash = saveUserCardHash;
    }

    public String getPaymentHash() {
        return paymentHash;
    }

    public void setPaymentHash(String paymentHash) {
        this.paymentHash = paymentHash;
    }

    String paymentRelatedDetailsHash;
    String deleteUserCardHash;
    String offerHash;
    String VASForMobileSDKHash;
    String saveUserCardHash;
    String paymentHash;

}