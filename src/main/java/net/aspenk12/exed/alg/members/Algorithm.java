package net.aspenk12.exed.alg.members;

import net.aspenk12.exed.alg.containers.Pick;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Algorithm {
    private Map<String, Course> courses;
    private List<Student> students;
    private List<Student> unlucky = new ArrayList<>();
    private List<Student> secondLotto = new ArrayList<>();

    public Algorithm(Map<String, Course> courses, List<Student> students) {
        this.courses = courses;
        this.students = students;
    }

    public void run(){

        for (Student student : students) {
            //only attempt to place students who weren't already placed
            if (student.profile.placedCourse.isEmpty()) {
                applyToNext(student);
            }
        }
    }

    public void run2(){
        //reset spot map for all courses
        for (Course c : Course.getCourses().values()){
            c.removeRestrictionsFromSpotMap();
        }
        //reset "current pick" to 0 for all students
        //run unlucky students back through placement algorithm
        for (Student student : unlucky) {
            student.currentPick = student.application.getPick(0);
            applyToNext(student);
        }
    }


    /**
     * Attempts to fit a student on to their next choice of course.
     * This method acts recursively. If the student fails to get on a course,
     * or if the student outbids another student, this method calls applyToNext on one of those students accordingly
     */
    /*protected 4 test*/ void applyToNext(Student student){

        Course course = student.currentPick.course;
        //System.out.println(course.courseName);
        Student nextStudent = course.placeStudent(student);

        //if the next student is null, no students were outbid and our job is done here.
        if(nextStudent == null){
            return;
        }


        //only advance to the next pick if the student fails to get on this one.
        //if placeStudent() returns another student, they apply to the course again.
        if (nextStudent.equals(student)) {
            //if a student has no more trips to try, add them to unlucky and finish
            if(student.advancePick()) {
                if(!student.unlucky){
                    unlucky.add(student);
                    student.unlucky=true;
                    return;
                }
                else{
                    secondLotto.add(student);
                    return;
                }
            }
        }

        //seccy recursion
        applyToNext(nextStudent);
    }

    public List<Student> getUnlucky(){
        return unlucky;
    }
    public List<Student> getSecondLotto(){
        return secondLotto;
    }
}
