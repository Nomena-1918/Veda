package veda.chrono;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeOnly{
    private LocalTime local;
    private void setLocal(LocalTime local) {
        this.local = local;
    }
    private LocalTime getLocal() {
        return local;
    }
    public TimeOnly(int hh, int mn, int ss){
        local=LocalTime.of(hh, mn, ss);
    }
    public TimeOnly(LocalTime date){
        int hours=date.getHour();
        int minute=date.getMinute();
        int sec=date.getSecond();
        local=LocalTime.of(hours, minute, sec);
    }
    public TimeOnly(String dateTime, String pattern){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern(pattern);
        setLocal(LocalTime.parse(dateTime, formatter));
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
        setLocal(LocalTime.now());
    }
    @Override
    public String toString(){
        String rep=local.toString();
        return rep;
    }
    public long hoursDifference(TimeOnly date2){
        Duration duration=Duration.between(getLocal(), date2.getLocal());
        long hours=duration.toHours();
        return hours;
    }
    public long minutesDifference(TimeOnly date2){
        Duration duration=Duration.between(getLocal(), date2.getLocal());
        long days=duration.toMinutes();
        return days;
    }
    public long secondDifference(TimeOnly date2){
        Duration duration=Duration.between(getLocal(), date2.getLocal());
        long days=duration.getSeconds();
        return days;
    }
    public int compareTo(TimeOnly date){
        if(local.isAfter(date.getLocal())){
            return 1;
        }else if(local.isBefore(date.getLocal())){
            return -1;
        }else{
            return 0;
        }
    }
    public LocalTime toLocalTime(){
        return getLocal();
    }
    public Time toTimeSql(){
        return Time.valueOf(local);
    }
    public Timestamp toTimestamp(){
        LocalDateTime date=LocalDateTime.of(LocalDate.now(), getLocal());
        Timestamp timestamp=Timestamp.valueOf(date);
        return timestamp;
    }
    public TimeOnly plusHours(int hours){
        LocalTime newDate=local.plusHours(hours);
        TimeOnly newTimeOnly=new TimeOnly(newDate);
        return newTimeOnly;
    }
    public TimeOnly plusMinutes(int minutes){
        LocalTime newDate=local.plusMinutes(minutes);
        TimeOnly newTimeOnly=new TimeOnly(newDate);
        return newTimeOnly;
    }
    public TimeOnly plusSeconds(int seconds){
        LocalTime newDate=local.plusSeconds(seconds);
        TimeOnly newTimeOnly=new TimeOnly(newDate);
        return newTimeOnly;
    }
}