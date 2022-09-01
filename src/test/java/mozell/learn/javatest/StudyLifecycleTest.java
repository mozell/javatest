package mozell.learn.javatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@ExtendWith(FindSlowTestExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudyLifecycleTest {

    int value = 1;

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

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
        System.out.println("value = " + value++);
        assertNotNull(study);
    }

    @Test
    @Order(1)
    void test2() {
        Study study = new Study(20);
        System.out.println(this);
        System.out.println("value = " + value++);
        assertNotNull(study);
    }

    @Test
    @Order(3)
    @Disabled
    void test_3() {
        Study study = new Study(30);
        System.out.println(this);
        System.out.println("value = " + value++);
        assertNotNull(study);
    }

    @Test
    @Order(4)
    @Disabled
    void test_4() throws InterruptedException {
        Thread.sleep(1005L);
        System.out.println(this);
        System.out.println("value = " + value++);
    }
    // Lifecycle을 추가함으로써 실제로 test1(), test2() 의 인스턴스값이 같음을 확인할 수 있다.

    @AfterAll
    void afterAll() {
        System.out.println("afterAll");
    }

}
