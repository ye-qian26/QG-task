package com.camp.interface_demo;

import java.util.ArrayList;

public class ClassManager {
    //创建一个集合来储存学生对象
    ArrayList<Student> students = new ArrayList<>();

    public ClassManager() {
        //全班数据
        students.add(new Student("张三",'男', 80));
        students.add(new Student("李四",'男', 99));
        students.add(new Student("小红",'女', 77));
        students.add(new Student("小丽",'女', 88));
    }
    //创建学生操作对象，可通过修改接口编程直接修改功能方案
    StudentOperator studentOperator = new StudentOperator2();
    public void printStudents() {
        studentOperator.printInfo(students);
    }

    public void printAverageScore(){
        studentOperator.printAverage(students);
    }
}
