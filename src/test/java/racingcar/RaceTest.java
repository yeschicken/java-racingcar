package racingcar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import racingcar.domain.Car;
import racingcar.domain.Race;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class RaceTest {
    @Test
    @DisplayName("경주를 생성하면 입력받은 대수만큼의 차가 초기 위치에 생성된다")
    public void race_Initial() {
        Race race = new Race(List.of("pobi", "crong", "honux"), 2, new AlwaysMoveStrategy());
        assertThat(race.toCarDtoList().get(0).getPosition()).isZero();
        assertThat(race.toCarDtoList().get(1).getPosition()).isZero();
        assertThat(race.toCarDtoList().get(2).getPosition()).isZero();
    }

    @Test
    @DisplayName("경주를 1회 진행하면 조건에 따라 차들이 이동한다")
    public void race_OneTry() {
        Race race = new Race(List.of("pobi", "crong", "honux"), 2, new AlwaysMoveStrategy());
        race.continueRace();
        assertThat(race.toCarDtoList().get(0).getPosition()).isOne();
        assertThat(race.toCarDtoList().get(1).getPosition()).isOne();
        assertThat(race.toCarDtoList().get(2).getPosition()).isOne();
    }

    @Test
    @DisplayName("현재까지 시도 회수가 입력받은 총 시도 회수보다 작으면 경주를 마치지 않는다.")
    public void race_NotFinished() {
        Race race = new Race(List.of("pobi", "crong", "honux"), 2, new AlwaysMoveStrategy());
        race.continueRace();
        assertThat(race.isNotFinished()).isTrue();
    }

    @Test
    @DisplayName("현재까지 시도 회수가 입력받은 총 시도 회수와 같으면 경주를 마친다.")
    public void race_Finished() {
        Race race = new Race(List.of("pobi", "crong", "honux"), 2, new AlwaysMoveStrategy());
        race.continueRace();
        race.continueRace();
        assertThat(race.isNotFinished()).isFalse();
    }

    @Test
    @DisplayName("자동차 이름은 쉼표(,)를 기준으로 구분한다")
    public void race_SplitCarNameString() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("pobi", 1));
        cars.add(new Car("crong", 2));
        cars.add(new Car("honux", 2));
        Race race = new Race(cars, 2);
        assertThat(race.toCarDtoList().get(0).getName()).isEqualTo("pobi");
        assertThat(race.toCarDtoList().get(1).getName()).isEqualTo("crong");
        assertThat(race.toCarDtoList().get(2).getName()).isEqualTo("honux");
    }

    @Test
    @DisplayName("자동차 경주 게임을 완료한 후 누가 우승했는지를 알려준다")
    public void race_Winner() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("pobi", 1));
        cars.add(new Car("crong", 1));
        cars.add(new Car("honux", 2));
        Race race = new Race(cars, 2);
        List<String> winnerCars = race.getFirstPlace()
                .stream()
                .map(car -> car.toDto().getName())
                .collect(Collectors.toList());
        assertThat(winnerCars).containsExactlyInAnyOrder("honux");
    }

    @Test
    @DisplayName("우승자는 한명 이상일 수 있다")
    public void race_CoWinner() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("pobi", 1));
        cars.add(new Car("crong", 2));
        cars.add(new Car("honux", 2));
        Race race = new Race(cars, 2);
        List<String> winnerCars = race.getFirstPlace()
                .stream()
                .map(car -> car.toDto().getName())
                .collect(Collectors.toList());
        assertThat(winnerCars).containsExactlyInAnyOrder("crong", "honux");
    }
}
