package xg.inclass.second_springboot.dto;

public class Student implements Comparable<Student> {
    //正式提交
    //还是没改变
    //怎么要多一个push的操作啊
    //这是合并后的结果
    //这个文件的改变不提交
    private int id;
    private String name;
    private int age;

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int compareTo(Student ob) {
        return name.compareTo(ob.getName());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        final Student std = (Student) obj;
        if (this == std) {
            return true;
        } else {
            return (this.name.equals(std.name) && (this.age == std.age));
        }
    }

    @Override
    public int hashCode() {
        int hashno = 7;
        hashno = 13 * hashno + (name == null ? 0 : name.hashCode());
        return hashno;
    }
}
