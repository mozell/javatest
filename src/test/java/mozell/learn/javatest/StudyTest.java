package mozell.learn.javatest;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("Assertion 강의 assertEquals")
    public void create_test_Assertion1() throws Exception {
        Study study = new Study(-10);
        assertNotNull(study);
        System.out.println("create");

        // 테스트 성공 여부에 관계없이 문자열 연산을 무조건 수행함
        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 "+ StudyStatus.DRAFT + "여야 한다.");
        // 테스트가 실패하는 경우에만 문자열 연산을 실행함
        assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 "+ StudyStatus.DRAFT + "여야 한다.");
        // new Supplier<String>() 사용
        assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {
            @Override
            public String get() {
                return "스터디를 처음 만들면 상태값이 "+ StudyStatus.DRAFT + "여야 한다.";
            }
        });

        assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.");
    }

    @Test
    @DisplayName("Assertion 강의 assertAll")
    void create_test_Assertion2() {
        Study study = new Study(-10);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + "여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.")
        );
    }

    @Test
    @DisplayName("Assertion 강의 assertThrows")
    void create_test_Assertion3() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = exception.getMessage();

        // 예외의 메시지를 뽑아서, 기대했던 메시지와 같은지 체크
        assertEquals("limit 는 0보다 커야한다.", message);
    }

    @Test
    @DisplayName("Assertion 강의 assertTimeout")
    void create_test_Assertion4() {
        //assertTimeoutPreemptively(); //TODO ThreadLocal -> 예상치 못한 결과가 나올 수 있는 점을 유의하여 사용해야 한다.
        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });

    }

    @Test
    void create_second_test() {
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