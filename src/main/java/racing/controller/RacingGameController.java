package racing.controller;

import java.util.List;
import java.util.stream.Collectors;
import racing.RandomNumberGenerator;
import racing.domain.Car;
import racing.dto.GameResultDto;
import racing.service.RacingGameService;
import racing.view.InputView;
import racing.view.OutputView;

public class RacingGameController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        List<String> names = inputView.inputNames();
        List<Car> cars = names.stream()
                .map(Car::new)
                .collect(Collectors.toList());
        int tryCount = inputView.inputTryCount();

        RacingGameService gameService = new RacingGameService(new RandomNumberGenerator(), tryCount, cars);
        while (!gameService.isEnd()) {
            GameResultDto resultDto = gameService.play();
            outputView.printRacing(resultDto);
        }
        List<String> winnerNames = gameService.getWinners();
        outputView.printWinners(winnerNames);
    }
}
