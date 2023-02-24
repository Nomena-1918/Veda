package veda.chrono;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateGen{
    private LocalDateTime local;
    private void setLocal(LocalDateTime local) {
        this.local = local;
    }
    private LocalDateTime getLocal() {
        return local;
    }
    public DateGen(){
        setLocal(LocalDateTime.now());
    }
    public DateGen(int yyyy, int mm, int dd){
        local=LocalDateTime.of(yyyy, mm, dd, 0, 0, 0);
    }
    public DateGen(int yyyy, int mm, int dd, int hh, int mn){
        local=LocalDateTime.of(yyyy, mm, dd, hh, mn, 0);
    }
    public DateGen(int yyyy, int mm, int dd, int hh, int mn, int ss){
        local=LocalDateTime.of(yyyy, mm, dd, hh, mn, ss);
    }
    public DateGen(LocalDateTime date){
        int years=date.getYear();
        int month=date.getMonthValue();
        int days=date.getDayOfMonth();
        int hours=date.getHour();
        int minute=date.getMinute();
        int sec=date.getSecond();
        local=LocalDateTime.of(years, month, days, hours, minute, sec);
    }
    public DateGen(LocalDate date){
        int years=date.getYear();
        int month=date.getMonthValue();
        int days=date.getDayOfMonth();
        local=LocalDateTime.of(years, month, days, 0, 0, 0);
    }
    public DateGen(Date dateSql){
        LocalDate date=dateSql.toLocalDate();
        int years=date.getYear();
        int month=date.getMonthValue();
        int days=date.getDayOfMonth();
        local=LocalDateTime.of(years, month, days, 0, 0, 0);
    }
    public DateGen(String dateTime, String pattern){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern(pattern);
        setLocal(LocalDateTime.parse(dateTime, formatter));
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
    public int getHour(){
        return local.getHour();
    }
    public int getMinute(){
        return local.getMinute();
    }
    public int getSecond(){
        return local.getSecond();
    }
    public int getNano(){
        return local.getNano();
    }
    public void now(){
        setLocal(LocalDateTime.now());
    }
    @Override
    public String toString(){
        String rep=local.toString();
        rep=rep.replaceFirst("T", " ");
        return rep;
    }
    public long yearsDifference(DateGen date2){
        long years=ChronoUnit.YEARS.between(getLocal(), date2.getLocal());
        return years;
    }
    public long monthesDifference(DateGen date2){
        long months=ChronoUnit.MONTHS.between(getLocal(), date2.getLocal());
        return months;
    }
    public long dayDifference(DateGen date2){
        Duration duration=Duration.between(getLocal(), date2.getLocal());
        long days=duration.toDays();
        return days;
    }
    public long hoursDifference(DateGen date2){
        Duration duration=Duration.between(getLocal(), date2.getLocal());
        long hours=duration.toHours();
        return hours;
    }
    public long minutesDifference(DateGen date2){
        Duration duration=Duration.between(getLocal(), date2.getLocal());
        long days=duration.toMinutes();
        return days;
    }
    public long secondDifference(DateGen date2){
        Duration duration=Duration.between(getLocal(), date2.getLocal());
        long days=duration.getSeconds();
        return days;
    }
    public int compareTo(DateGen date){
        if(local.isAfter(date.getLocal())){
            return 1;
        }else if(local.isEqual(date.getLocal())){
            return 0;
        }else{
            return -1;
        }
    }
    public LocalDateTime toLocalDateTime(){
        return getLocal();
    }
    public Timestamp toTimestamp(){
        Timestamp timestamp=Timestamp.valueOf(getLocal());
        return timestamp;
    }
    public DateOnly toDateOnly(){
        DateOnly date=new DateOnly(getLocal().toLocalDate());
        return date;
    }
    public TimeOnly toTimeOnly(){
        TimeOnly time=new TimeOnly(getLocal().toLocalTime());
        return time;
    }
    public DateGen plusYears(int years){
        LocalDateTime newDate=local.plusYears(years);
        DateGen newDateGen=new DateGen(newDate);
        return newDateGen;
    }
    public DateGen plusMonths(int months){
        LocalDateTime newDate=local.plusMonths(months);
        DateGen newDateGen=new DateGen(newDate);
        return newDateGen;
    }
    public DateGen plusDays(int days){
        LocalDateTime newDate=local.plusDays(days);
        DateGen newDateGen=new DateGen(newDate);
        return newDateGen;
    }
    public DateGen plusHours(int hours){
        LocalDateTime newDate=local.plusHours(hours);
        DateGen newDateGen=new DateGen(newDate);
        return newDateGen;
    }
    public DateGen plusMinutes(int minutes){
        LocalDateTime newDate=local.plusMinutes(minutes);
        DateGen newDateGen=new DateGen(newDate);
        return newDateGen;
    }
    public DateGen plusSeconds(int seconds){
        LocalDateTime newDate=local.plusSeconds(seconds);
        DateGen newDateGen=new DateGen(newDate);
        return newDateGen;
    }
}