public class Student extends Person {
    private int studentId;
    private String batchId; // Mã lớp học

    public Student(int studentId, String name, String dateOfBirth, String gender, String phone, String batchId) {
        super(name, dateOfBirth, gender, phone);
        this.studentId = studentId;
        this.batchId = batchId;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getBatchId() {
        return batchId;
    }


    public String toCSVString() {
        return studentId + "," + name + "," + dateOfBirth + "," + gender + "," + phone + "," + batchId;
    }


    public void display(String batchName) {
        System.out.printf("%-6d | %-50s | %-10s | %-8s | %-20s%n", studentId, name, dateOfBirth, gender, batchName);
    }


    @Override
    public void display() {
        System.out.printf("%-6d | %-50s | %-10s | %-8s | %-10s | %-10s%n", studentId, name, dateOfBirth, gender, phone, batchId);
    }
}