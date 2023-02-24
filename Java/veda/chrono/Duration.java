package veda.chrono;

public class Duration {
    private DateGen start;
    private double duration;
    public Duration(DateGen start){
        setStart(start);
        setDuration(30, 5);
    }
    public Duration(){
        setStart(new DateGen());
        setDuration(30, 5);
    }
    @Override
    public String toString(){
        return String.valueOf(duration);
    }
    public DateGen getStart() {
        return start;
    }
    public void setStart(DateGen start) {
        this.start = start;
    }
    public double getDuration() {
        return duration;
    }
    public double getDurationYears(){
        double years=getStart().yearsDifference(getEnd());
        return years;
    }
    public double getDurationMonths(){
        double months=getStart().monthesDifference(getEnd());
        return months;
    }
    public double getDurationDays(){
        double days=getStart().dayDifference(getEnd());
        return days;
    }
    public double getDurationHours(){
        double hours=getStart().hoursDifference(getEnd());
        return hours;
    }
    public double getDurationMinutes(){
        double minutes=getStart().minutesDifference(getEnd());
        return minutes;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }
    public void setDuration(DateGen date){
        double duration=getStart().secondDifference(date);
        setDuration(duration);
    }
    public void setDuration(double valeur, int unite){
        Double d=valeur+1;
        switch(unite){
            case 1:
                DateGen limite=getStart().plusYears(d.intValue());
                double duration=getStart().secondDifference(limite);
                setDuration(duration);
                break;
            case 2:
                limite=getStart().plusMonths(d.intValue());
                duration=getStart().secondDifference(limite);
                setDuration(duration);
                break;
            case 3:
                limite=getStart().plusDays(d.intValue());
                duration=getStart().secondDifference(limite);
                setDuration(duration);
                break;
            case 4:
                limite=getStart().plusHours(d.intValue());
                duration=getStart().secondDifference(limite);
                setDuration(duration);
                break;
            case 5:
                limite=getStart().plusMinutes(d.intValue());
                duration=getStart().secondDifference(limite);
                setDuration(duration);
                break;
            case 6:
                double val=d;
                setDuration(val);
                break;
        }
    }
    public DateGen getEnd(){
        Double d=getDuration();
        DateGen limite=getStart().plusSeconds(d.intValue());
        return limite;
    }
    public boolean valid(DateGen date){
        int depasse=date.compareTo(getEnd());
        if(depasse==1){
            return false;
        }
        return true;
    }
}
