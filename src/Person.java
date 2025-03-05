public abstract class Person {
    protected String name;
    protected String dateOfBirth;
    protected String gender;
    protected String phone;

    public Person(String name, String dateOfBirth, String gender, String phone) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public abstract void display();
}