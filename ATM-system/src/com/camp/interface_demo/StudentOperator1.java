package com.camp.interface_demo;

import java.util.ArrayList;

public class StudentOperator1 implements StudentOperator {


    @Override
    public void printInfo(ArrayList<Student> students) {
        System.out.println("----------全班成绩如下-----------");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println("姓名：" + s.getName() + " 性别：" + s.getSex() + " 成绩：" + s.getScore());
        }
        System.out.println("-------------------------------");
    }

    @Override
    public void printAverage(ArrayList<Student> students) {
        double allScore = 0;
        for (int i = 0; i < students.size(); i++) {
            allScore += students.get(i).getScore();
        }
        System.out.println("平均成绩：" + allScore / students.size());
    }
}
