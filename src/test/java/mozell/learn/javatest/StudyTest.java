package mozell.learn.javatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

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

    @DisplayName("테스트 반복하기 1 - 단순반복")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest1 (RepetitionInfo repetitionInfo) {
        System.out.println("test " + repetitionInfo.getCurrentRepetition() + "/"
                                   + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("테스트 반복하기 2 - 값 반복")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(strings = {"날씨가", "많이", "선선해지고", "있네요."})
    void parameterizedTest(String message) {
        System.out.println(message);
    }

    @DisplayName("테스트 반복하기 2부")
    @ParameterizedTest(name = "{index} message = {0}")
    @ValueSource(strings = {"날씨가", "많이", "선선해지고", "있네요."})
    @NullAndEmptySource
    void parameterizedTest2(String message) {
        System.out.println(message);
    }

    @DisplayName("테스트 반복하기 2부 - 인자값 타입 변환")
    @ParameterizedTest(name = "{index} message = {0}")
    @ValueSource(ints = {10,20,40})
    void parameterizedTest3(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println("study.getLimit() = " + study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("테스트 반복하기 2부 - 인자값 타입 변환")
    @ParameterizedTest(name = "{index} message = {0}")
    @CsvSource({"10, '자바 스터디'","20, 스프링"})
    void parameterizedTest4_1(Integer limit, String name) {
        Study study = new Study(limit, name);
        System.out.println(study);
    }

    @DisplayName("테스트 반복하기 2부 - 인자값 타입 변환")
    @ParameterizedTest(name = "{index} message = {0}")
    @CsvSource({"10, '자바 스터디'","20, 스프링"})
    void parameterizedTest4_2(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println(study);
    }

    @DisplayName("테스트 반복하기 2부 - 인자값 타입 변환")
    @ParameterizedTest(name = "{index} message = {0}")
    @CsvSource({"10, '자바 스터디'","20, 스프링"})
    void parameterizedTest4_3(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        // ArgumentsAggregator 제약조건 : static inner class 또는 public class 여야 사용할 수 있다.
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
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