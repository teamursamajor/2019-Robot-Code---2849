package frc.minimap;

public class StudySession{
    private String subject, location;
    private int startTime, endTime;
    private String [] people;

    StudySession(String subject){
        this.subject = subject;
    }

    public void readABook(String person){
        System.out.println(person + " has read a " + subject + " book.");
    }
    
    public static void main(String[] args){
        StudySession s = new StudySession("English");
        s.readABook("Jennnnnnnnnnnnnnnny");
    }
}