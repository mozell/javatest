package mozell.learn.javatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    @Test
    public void create() throws Exception {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");
    }

    @Test
    void create1() {
        System.out.println("create1");
    }

    /**
     * @Disabled
     * 깨지는 테스트인 경우에, 테스트를 무시하고 돌릴 수 있다.
     */
    @Test
    @Disabled
    void create2() {
        System.out.println("create2");
    }


    /**
     * @BeforeAll
     * @AfterAll
     * : 테스트 클래스 안에 있는 모든 테스트가 실행 되기 전/후에 딱 한번 호출된다.
     * -. 제약사항
     * 반드시 static 메소드를 사용해야한다.
     * private 안되고, default는 된다.
     * return 타입이 있으면 안된다.
     * -> static void 로 시작하면 된다.
     * -. 메소드 이름은 상관없다.
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after All");
    }


    /**
     * @BeforeEach
     * @AfterEach
     * : 테스트 클래스 안에 있는 각각의 테스트가 실행 되기 이전/이후에 한번씩 호출된다.
     * 따라서, static 일 필요가 없다.
     */
    @BeforeEach
    void beforeEach() {
        System.out.println("before Each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after Each");
    }
}