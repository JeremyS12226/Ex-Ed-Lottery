package net.aspenk12.exed.alg.containers;

import net.aspenk12.exed.alg.members.Course;
import net.aspenk12.exed.alg.members.CourseTest;
import net.aspenk12.exed.alg.members.Student;
import net.aspenk12.exed.util.Warnings;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ApplicationTest {

    @Test
    public void testValidation() {
        CourseTest.makeExampleCourses();

        List<Course> previousCourses = new ArrayList<>();
        previousCourses.add(Course.get("SS"));
        previousCourses.add(Course.get("JH"));
        previousCourses.add(Course.get("CC"));

        Student student = new Student("Alex", "Appleby", "alexandera11325@aspenk12.net", Grade.SENIOR, Gender.MALE, 30, previousCourses);

        Application application = new Application(student);
        application.addPick(new Pick(Course.get("SS"), 10)); //course already attended
        application.addPick(new Pick(Course.get("ZC"), 15));
        application.addPick(new Pick(Course.get("WE"), 6));
        application.addPick(new Pick(Course.get("CT"), -10)); //negative value
        application.addPick(new Pick(Course.get("WE"), 12)); //duplicate, should be removed
        application.addPick(new Pick(Course.get("NM"), 2));

        application.validate();

        assertEquals(Course.get("ZC"), application.getPick(0).course);
        assertEquals(15, application.getPick(0).points);

        assertEquals(Course.get("CT"), application.getPick(2).course);
        assertEquals(0, application.getPick(2).points);

        assertEquals(Course.get("NM"), application.getPick(3).course);
        assertEquals(2, application.getPick(3).points);
    }
}