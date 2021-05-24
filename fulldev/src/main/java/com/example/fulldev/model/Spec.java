package com.example.fulldev.model;

public class Spec {
    private Long keyId;
    private String key;
    private Long valueId;
    private String value;

    public Spec() {

    }

    public Spec(Long keyId, String key, Long valueId, String value) {
        this.keyId = keyId;
        this.key = key;
        this.valueId = valueId;
        this.value = value;
    }

    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
