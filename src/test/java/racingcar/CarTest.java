package racingcar;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CarTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("4 이상일 때 이동한다")
    public void carMove_WhenNumberMoreThanEqualMinimumNumberToMove() {
        Car car = new Car();
        car.move(4);
        assertThat(car.toDto().getPosition()).isOne();
    }

    @Test
    @DisplayName("3 미만일 때 이동하지 않는다")
    public void carMove_WhenNumberLessThanMinimumNumberToMove() {
        Car car = new Car();
        car.move(3);
        assertThat(car.toDto().getPosition()).isZero();
    }
}