package com.camp.interface_demo;

import java.util.ArrayList;

public class StudentOperator2 implements StudentOperator{
    @Override
    public void printInfo(ArrayList<Student> students) {
        int count1 = 0;
        int count2 = 0;
        System.out.println("----------全班成绩如下-----------");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println("姓名：" + s.getName() + " 性别：" + s.getSex() + " 成绩：" + s.getScore());
            if(s.getSex() == '男') {
                count1++;
            }else{
                count2++;
            }
        }
        System.out.println("男生人数：" + count1 + " 女生人数：" + count2);
        System.out.println("全班人数：" + students.size());
        System.out.println("-------------------------------");
    }

    @Override
    public void printAverage(ArrayList<Student> students) {
        double allScore = 0;
        double max = students.get(0).getScore();
        double min = students.get(0).getScore();
        for (int i = 0; i < students.size(); i++) {
            if(students.get(i).getScore() > max) max = students.get(i).getScore();
            if(students.get(i).getScore() < min) min = students.get(i).getScore();
            allScore += students.get(i).getScore();
        }
        System.out.println("最高分：" + max + " 最低分：" + min);
        System.out.println("平均成绩：" + (allScore - max - min) / (students.size() - 2));
    }
}
