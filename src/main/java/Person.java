public class Person {

    private String firstname;
    private String lastname;
    private int age;
    private boolean isDeveloper;

    public Person(String firstname, String lastname, int age, boolean isDeveloper) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.isDeveloper = isDeveloper;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isDeveloper() {
        return isDeveloper;
    }

    public void setDeveloper(boolean developer) {
        isDeveloper = developer;
    }
}
