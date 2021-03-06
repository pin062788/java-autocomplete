package games.application.handler.copyGamesFromRepository;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import games.domain.repositories.GameRepository;
import games.domain.services.CopyGamesService;

import java.util.concurrent.CompletionStage;

public class CopyGamesFromRepositoryCommandHandler {

    @Inject
    @Named("game.jpa")
    private GameRepository destGameRepository;

    @Inject @Named("game.json")
    private GameRepository sourceGameRepository;

    @Inject
    private CopyGamesService copyGamesService;

    @Inject
    public CopyGamesFromRepositoryCommandHandler(GameRepository sourceGameRepository,
                                                 GameRepository destGameRepository,
                                                 CopyGamesService copyGamesService
    ) {
        this.sourceGameRepository = sourceGameRepository;
        this.destGameRepository = destGameRepository;
        this.copyGamesService = copyGamesService;
    }

    public CompletionStage<CopyGamesFromRepositoryResponse> handle() {
        return this.copyGamesService.copy(sourceGameRepository, destGameRepository)
                .thenApplyAsync((addedGames) -> new CopyGamesFromRepositoryResponse(addedGames.count()));
    }
}
