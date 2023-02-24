package veda.chrono;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateOnly{
    private LocalDate local;
    private void setLocal(LocalDate local) {
        this.local = local;
    }
    private LocalDate getLocal() {
        return local;
    }
    public DateOnly(int yyyy, int mm, int dd){
        local=LocalDate.of(yyyy, mm, dd);
    }
    public DateOnly(LocalDate date){
        int years=date.getYear();
        int month=date.getMonthValue();
        int days=date.getDayOfMonth();
        local=LocalDate.of(years, month, days);
    }
    public DateOnly(String dateTime, String pattern){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern(pattern);
        setLocal(LocalDate.parse(dateTime, formatter));
    }
    public int getYear(){
        return local.getYear();
    }
    public int getMonthValue(){
        return local.getMonthValue();
    }
    public int getDayOfMonth(){
        return local.getDayOfMonth();
    }
    public int getDayOfYear(){
        return local.getDayOfYear();
    }
    public int getDayOfWeek(){
        return local.getDayOfWeek().getValue();
    }
    public void now(){
        setLocal(LocalDate.now());
    }
    @Override
    public String toString(){
        String rep=local.toString();
        return rep;
    }
    public long yearsDifference(DateOnly date2){
        long years=ChronoUnit.YEARS.between(getLocal(), date2.getLocal());
        return years;
    }
    public long monthesDifference(DateOnly date2){
        long months=ChronoUnit.MONTHS.between(getLocal(), date2.getLocal());
        return months;
    }
    public long dayDifference(DateOnly date2){
        Duration duration=Duration.between(getLocal(), date2.getLocal());
        long days=duration.toDays();
        return days;
    }
    public int compareTo(DateOnly date){
        if(local.isAfter(date.getLocal())){
            return 1;
        }else if(local.isEqual(date.getLocal())){
            return 0;
        }else{
            return -1;
        }
    }
    public LocalDate toLocalDate(){
        return getLocal();
    }
    public Date toDateSql(){
        return Date.valueOf(local);
    }
    public DateOnly plusYears(int years){
        LocalDate newDate=local.plusYears(years);
        DateOnly newDateOnly=new DateOnly(newDate);
        return newDateOnly;
    }
    public DateOnly plusMonths(int months){
        LocalDate newDate=local.plusMonths(months);
        DateOnly newDateOnly=new DateOnly(newDate);
        return newDateOnly;
    }
    public DateOnly plusDays(int days){
        LocalDate newDate=local.plusDays(days);
        DateOnly newDateOnly=new DateOnly(newDate);
        return newDateOnly;
    }
}