package com.hamza.kartvizit.model;

public class Soru {
    private String soru;
    private String secenekA;
    private String secenekB;
    private String secenekC;
    private String secenekD;
    private int dogruYanit;

    public Soru() {
    }

    public Soru(String soru, String secenekA, String secenekB, String secenekC, String secenekD, int dogruYanit) {
        this.soru = soru;
        this.secenekA = secenekA;
        this.secenekB = secenekB;
        this.secenekC = secenekC;
        this.secenekD = secenekD;
        this.dogruYanit = dogruYanit;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getSecenekA() {
        return secenekA;
    }

    public void setSecenekA(String secenekA) {
        this.secenekA = secenekA;
    }

    public String getSecenekB() {
        return secenekB;
    }

    public void setSecenekB(String secenekB) {
        this.secenekB = secenekB;
    }

    public String getSecenekC() {
        return secenekC;
    }

    public void setSecenekC(String secenekC) {
        this.secenekC = secenekC;
    }

    public String getSecenekD() {
        return secenekD;
    }

    public void setSecenekD(String secenekD) {
        this.secenekD = secenekD;
    }

    public int getDogruYanit() {
        return dogruYanit;
    }

    public void setDogruYanit(int dogruYanit) {
        this.dogruYanit = dogruYanit;
    }
}
