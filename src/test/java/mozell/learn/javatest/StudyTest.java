package mozell.learn.javatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

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

    @Test
    @DisplayName("조건에따른 테스트 강의 assume1")
    void create_test_assume1() {
        /**
         * Edit Configurations (Run/Debug Configurations) -> Environment variables 세팅
         *  name : TEST_ENV / value : LOCAL
         */
        String test_env = System.getenv("TEST_ENV");
        System.out.println("test_env = " + test_env);

        // assumeTrue() : 조건 검증
        assumeTrue("mozell".equalsIgnoreCase(test_env));

        // assumingThat() : 해당 조건일 때 테스트 수행
        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            Study study = new Study(100);
            System.out.println("study.getLimit() = " + study.getLimit());
            assertNotNull(study);
        });
        assumingThat("mozell".equalsIgnoreCase(test_env), () -> {
            Study study = new Study(10);
            System.out.println("study.getLimit() = " + study.getLimit());
            assertNotNull(study);
        });
    }

    @Test
    @DisplayName("조건에따른 테스트 강의 assume - Enabled Annotation")
    @EnabledOnOs(OS.WINDOWS)
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11})
    void create_test_assume2() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println("test_env = " + test_env);

        Study study = new Study(100);
        assertNotNull(study);
    }

    @Test
    @DisplayName("조건에따른 테스트 강의 assume - EnabledIfEnvironmentVariable")
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "mozell")
    void create_test_assume3() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println("test_env = " + test_env);

        Study study = new Study(100);
        assertNotNull(study);
    }

    @Test
    @DisplayName("태깅과 필터링 - Tag")
    @Tag("fast")
    void create_test_tag1() {
        System.out.println("tag");
    }

    @Test
    @DisplayName("태깅과 필터링 - Tags")
    @Tags({
        @Tag("fast"),
        @Tag("tag2")
    })
    void create_test_tag2() {
        System.out.println("tags");
    }

    @DisplayName("커스텀 태그 1")
    @FastTest
    void create_test_custom_tag1() {
        System.out.println("fast");
    }

    @DisplayName("커스텀 태그 2")
    @SlowTest
    void create_test_custom_tag2() {
        System.out.println("slow");
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