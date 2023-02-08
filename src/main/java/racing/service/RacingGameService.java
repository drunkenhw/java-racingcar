package racing.service;

import java.util.List;
import java.util.stream.Collectors;
import racing.NumberGenerator;
import racing.domain.Car;
import racing.domain.Cars;
import racing.dto.GameResultDto;

public class RacingGameService {
    private static final int MOVE_CONDITION = 4;

    private final NumberGenerator numberGenerator;
    private final Cars cars;
    private int tryCount;

    public RacingGameService(NumberGenerator numberGenerator, int tryCount, List<Car> cars) {
        this.numberGenerator = numberGenerator;
        this.cars = new Cars(cars);
        this.tryCount = tryCount;
    }

    public GameResultDto play() {
        GameResultDto result = new GameResultDto();
        for (Car car : cars.getCars()) {
            int number = numberGenerator.generate();
            move(car, number);
            result.put(car.getName(), car.getPosition());
        }
        tryCount--;
        return result;
    }

    private void move(Car car, int number) {
        if (number >= MOVE_CONDITION) {
            car.addPosition();
        }
    }

    public boolean isEnd() {
        return tryCount == 0;
    }

    public List<String> getWinners() {
        return cars.getFirstPosition().stream()
                .map(Car::getName)
                .collect(Collectors.toList());
    }
}
