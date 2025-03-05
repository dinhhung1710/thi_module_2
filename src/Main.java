import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static final String STUDENT_FILE = "data/students.csv";
    private static final String BATCH_FILE   = "data/batchs.csv";
    private static final String TEACHER_FILE = "data/teachers.csv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n----- MENU CHỨC NĂNG -----");
            System.out.println("1. Thêm mới sinh viên");
            System.out.println("2. Xóa sinh viên");
            System.out.println("3. Xem danh sách sinh viên");
            System.out.println("4. Tìm kiếm sinh viên");
            System.out.println("5. Xem thông tin giáo viên theo mã giáo viên");
            System.out.println("6. Thoát");
            System.out.print("Chọn chức năng: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập số từ 1 đến 6.");
                continue;
            }

            switch (choice) {
                case 1:
                    addNewStudent(sc);
                    break;
                case 2:
                    deleteStudent(sc);
                    break;
                case 3:
                    viewStudents();
                    break;
                case 4:
                    searchStudent(sc);
                    break;
                case 5:
                    viewTeacher(sc);
                    break;
                case 6:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 6);

        sc.close();
    }


    private static List<Student> readStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(STUDENT_FILE);
        if (!file.exists()) {
            return students;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) { // bỏ qua dòng header
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length < 6) continue;
                int id = Integer.parseInt(parts[0]);
                Student s = new Student(id, parts[1], parts[2], parts[3], parts[4], parts[5]);
                students.add(s);
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file sinh viên: " + e.getMessage());
        }
        return students;
    }


    private static void writeStudents(List<Student> students) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(STUDENT_FILE))) {

            pw.println("studentId,studentName,dateOfBirth,gender,phone,batchId");
            for (Student s : students) {
                pw.println(s.toCSVString());
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file sinh viên: " + e.getMessage());
        }
    }


    private static Map<String, Batch> readBatches() {
        Map<String, Batch> batches = new HashMap<>();
        File file = new File(BATCH_FILE);
        if (!file.exists()) {
            System.out.println("Không tìm thấy file batchs.csv");
            return batches;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
                Batch b = new Batch(parts[0], parts[1], parts[2]);
                batches.put(parts[0], b);
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file batchs.csv: " + e.getMessage());
        }
        return batches;
    }


    private static Map<String, Teacher> readTeachers() {
        Map<String, Teacher> teachers = new HashMap<>();
        File file = new File(TEACHER_FILE);
        if (!file.exists()) {
            System.out.println("Không tìm thấy file teachers.csv");
            return teachers;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length < 5) continue;
                Teacher t = new Teacher(parts[0], parts[1], parts[2], parts[3], parts[4]);
                teachers.put(parts[0], t);
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file teachers.csv: " + e.getMessage());
        }
        return teachers;
    }


    private static int getNextStudentId(List<Student> students) {
        int maxId = 0;
        for (Student s : students) {
            if (s.getStudentId() > maxId) {
                maxId = s.getStudentId();
            }
        }
        return maxId + 1;
    }


    private static boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    private static boolean isValidPhone(String phone) {
        return phone.matches("^(090|091)\\d{7}$");
    }


    private static void addNewStudent(Scanner sc) {
        List<Student> students = readStudents();
        Map<String, Batch> batches = readBatches();

        int studentId = getNextStudentId(students);
        System.out.println("\nNhập thông tin sinh viên mới:");
        String name, dob, gender, phone, batchId;


        while (true) {
            System.out.print("Tên sinh viên: ");
            name = sc.nextLine().trim();
            if (name.isEmpty() || name.length() < 4 || name.length() > 50) {
                System.out.println("Tên sinh viên phải từ 4 đến 50 ký tự.");
            } else {
                break;
            }
        }


        while (true) {
            System.out.print("Ngày sinh (dd/MM/yyyy): ");
            dob = sc.nextLine().trim();
            if (!isValidDate(dob)) {
                System.out.println("Ngày sinh không hợp lệ.");
            } else {
                break;
            }
        }


        while (true) {
            System.out.print("Giới tính: ");
            gender = sc.nextLine().trim();
            if (gender.isEmpty()) {
                System.out.println("Giới tính không được để trống.");
            } else {
                break;
            }
        }


        while (true) {
            System.out.print("Số điện thoại: ");
            phone = sc.nextLine().trim();
            if (phone.isEmpty() || !isValidPhone(phone)) {
                System.out.println("Số điện thoại phải có 10 số và bắt đầu bằng 090 hoặc 091.");
                continue;
            }
            boolean exists = false;
            for (Student s : students) {
                if (s.phone.equals(phone)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                System.out.println("Số điện thoại đã tồn tại.");
            } else {
                break;
            }
        }


        while (true) {
            System.out.print("Mã lớp học: ");
            batchId = sc.nextLine().trim();
            if (!batches.containsKey(batchId)) {
                System.out.println("Mã lớp học không tồn tại.");
            } else {
                break;
            }
        }

        Student newStudent = new Student(studentId, name, dob, gender, phone, batchId);
        students.add(newStudent);
        writeStudents(students);
        System.out.println("Thêm sinh viên thành công!");
    }


    private static void deleteStudent(Scanner sc) {
        List<Student> students = readStudents();
        if (students.isEmpty()) {
            System.out.println("Danh sách sinh viên trống.");
            return;
        }
        System.out.print("\nNhập mã sinh viên cần xóa: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Mã sinh viên phải là số.");
            return;
        }
        Student found = null;
        for (Student s : students) {
            if (s.getStudentId() == id) {
                found = s;
                break;
            }
        }
        if (found == null) {
            try {
                throw new NotFoundStudentException("Sinh viên không tồn tại.");
            } catch (NotFoundStudentException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        System.out.print("Bạn có chắc chắn muốn xóa sinh viên này? (Yes/No): ");
        String confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("Yes")) {
            students.remove(found);
            writeStudents(students);
            System.out.println("Xóa sinh viên thành công!");
            viewStudents();
        } else {
            System.out.println("Hủy xóa, quay lại menu chính.");
        }
    }


    private static void viewStudents() {
        List<Student> students = readStudents();
        Map<String, Batch> batches = readBatches();
        if (students.isEmpty()) {
            System.out.println("Danh sách sinh viên trống.");
            return;
        }
        System.out.println("\nDanh sách sinh viên:");
        System.out.printf("%-6s | %-50s | %-10s | %-8s | %-20s%n", "Mã SV", "Tên sinh viên", "Ngày sinh", "Giới tính", "Tên lớp học");
        for (Student s : students) {
            Batch b = batches.get(s.getBatchId());
            String batchName = (b != null) ? b.getBatchName() : "Unknown";
            s.display(batchName);
        }
    }


    private static void searchStudent(Scanner sc) {
        List<Student> students = readStudents();
        System.out.print("\nNhập từ khóa tìm kiếm (Tên sinh viên): ");
        String keyword = sc.nextLine().trim().toLowerCase();
        boolean foundAny = false;
        System.out.println("\nKết quả tìm kiếm:");
        System.out.printf("%-6s | %-50s | %-10s | %-8s | %-10s | %-10s%n", "Mã SV", "Tên sinh viên", "Ngày sinh", "Giới tính", "Điện thoại", "Mã lớp");
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(keyword)) {
                s.display();
                foundAny = true;
            }
        }
        if (!foundAny) {
            System.out.println("Không tìm thấy sinh viên với từ khóa: " + keyword);
        }
    }


    private static void viewTeacher(Scanner sc) {
        Map<String, Teacher> teachers = readTeachers();
        System.out.print("\nNhập mã giáo viên: ");
        String teacherId = sc.nextLine().trim();
        Teacher t = teachers.get(teacherId);
        if (t != null) {
            System.out.println("\nThông tin giáo viên:");
            System.out.printf("%-10s | %-50s | %-10s | %-8s | %-10s%n", "Mã GV", "Tên giáo viên", "Ngày sinh", "Giới tính", "Điện thoại");
            t.display();
        } else {
            System.out.println("Không tìm thấy giáo viên với mã: " + teacherId);
        }
    }
}
