/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etsypricer;

/**
 * Programm for calculate final price of etsy lots.
 * @author Firsov Konstantin
 */
public class Listing {
    //Percents of WB, BANK and ETSY
    private double westernBidPercent, bankCashPersent, etsySalePercent, sumOfPercent, etsyFix;
    //Prices of lot and delivery
    private double lotPrice, deliveryPrice, finalLotPrice;
    private double etsyDollars, westernBidDollars, bankDollars, totalProfit;
    
    //Конструктор
    public void Listing(double lotPrice){
        this.westernBidPercent = 0.05;
        this.bankCashPersent = 0.03;
        this.etsySalePercent = 0.035;
        this.etsyFix = 0.2;
        //this.deliveryPrice = 6;
        
        this.lotPrice = lotPrice;
        
        this.setSumOfPercent();
        this.setDeliveryPrice(300);
        this.setFinalLotPrice();
        
    }
     
    
    //Приводим значение к долям процента и сообщаем об ошибке, если значение отрицательно
    public double toPercent(double percentValue){
        if(percentValue>=1){
            percentValue = percentValue / 100;
        }
        else if(percentValue<0){
        System.out.println("ERROR! Value is negative");
    }
        return percentValue;
    }
    
    // Сеттеры
    public void setWesternBidPercent(double percent){
        this.westernBidPercent = toPercent(percent);
    }
    public void setBankCashPersent(double percent){
        this.bankCashPersent = toPercent(percent);
    }
    public void setEtsySalePercent(double percent){
        this.etsySalePercent = toPercent(percent);
    }
    public void setSumOfPercent(){
        this.sumOfPercent = this.westernBidPercent + this.bankCashPersent + this.etsySalePercent;
    }
    public void setEtsyFix(double price){
        this.etsyFix = price;
    }
    public void setLotPrice(double price){
        this.lotPrice = price;
    }
    public void setDeliveryPrice(double weight){
        /*
        Цены Укрпочты за авиапересылку:
        до 100 г = $1.4
        свыше 100 г до 250 г = $2.8
        свыше 250 г до 500 г = $5.1
        свыше 500 г до 1 кг = $8.6
        свыше 1 кг = $14.3
        */
        double priceOfWeight[] = {1.4, 2.8, 5.1, 8.6, 14.3};//Массив цен укрпочты на пересылку
        
        if(weight<=100&&weight>0){
            this.deliveryPrice = priceOfWeight[0];
        }
        else if(weight>100&&weight<=250){
            this.deliveryPrice = priceOfWeight[1];
        }
        else if(weight>250&&weight<=500){
            this.deliveryPrice = priceOfWeight[2];
        }
        else if(weight>500&&weight<=1000){
            this.deliveryPrice = priceOfWeight[3];
        }
        else if(weight>1000){
            this.deliveryPrice = priceOfWeight[4];
        }
        else{System.out.println("Incorrect value of weight!");}
       
    }
    public void setFinalLotPrice(){
        setSumOfPercent();
        double result = (this.lotPrice + this.deliveryPrice + this.etsyFix) * (1/(1 - this.sumOfPercent));
        this.finalLotPrice = Math.round(result*100)/100.0;
    }
    
        
    public double etsyDollars(){
        //this.setFinalLotPrice();
        double result = this.finalLotPrice * this.etsySalePercent + this.etsyFix;
        this.etsyDollars = Math.round(result*100)/100.0;
        return this.etsyDollars;
    }
    
    public double westernBidDollars(){
        double result = (this.finalLotPrice - this.etsyDollars) * this.westernBidPercent;
        this.westernBidDollars = Math.round(result*100)/100.0;
        return this.westernBidDollars;
    }
    
    public double bankDollars(){
        double result = (this.finalLotPrice - this.etsyDollars - this.westernBidDollars) * this.bankCashPersent;
        this.bankDollars = Math.round(result*100)/100.0;
        return this.bankDollars;
    }
    
    public double totalProfit(){
        double result = this.finalLotPrice - this.etsyDollars - this.westernBidDollars - this.bankDollars - this.deliveryPrice;
        this.totalProfit = Math.round(result*100)/100.0;
        return this.totalProfit;
    }
    
    //Геттеры
    public double getWesternBidPercent(){
        return this.westernBidPercent;
    }
    public double getBankCashPersent(){
        return this.bankCashPersent;
    }
    public double getEtsySalePercent(){
        return this.etsySalePercent;
    }
    public double getEtsyFix(){
        return this.etsyFix;
    }
    public double getSumOfPercent(){
        return this.sumOfPercent;
    }
    public double getLotPrice(){
        return this.lotPrice;
    }
    public double getDeliveryPrice(){
        return this.deliveryPrice;
    }
    public double getFinalLotPrice(){
        return this.finalLotPrice;
    }
}
