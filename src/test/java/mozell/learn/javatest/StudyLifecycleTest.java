package mozell.learn.javatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudyLifecycleTest {


    // private만 아니면된다. 라이프사이클 추가함으로써 static 빼도된다.
    @BeforeAll
    void beforeAll() {
        System.out.println("beforeAll");
    }

    @Test
    @Order(2)
    void test1() {
        Study study = new Study(10);
        System.out.println(this);
        assertNotNull(study);
    }

    @Test
    @Order(1)
    void test2() {
        Study study = new Study(20);
        System.out.println(this);
        assertNotNull(study);
    }
    // Lifecycle을 추가함으로써 실제로 test1(), test2() 의 인스턴스값이 같음을 확인할 수 있다.

    @AfterAll
    void afterAll() {
        System.out.println("afterAll");
    }

}
