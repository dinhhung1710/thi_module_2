public class Teacher extends Person {
    private String teacherId;

    public Teacher(String teacherId, String name, String dateOfBirth, String gender, String phone) {
        super(name, dateOfBirth, gender, phone);
        this.teacherId = teacherId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    @Override
    public void display() {
        System.out.printf("%-10s | %-50s | %-10s | %-8s | %-10s%n", teacherId, name, dateOfBirth, gender, phone);
    }
}