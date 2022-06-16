package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntryAlreadyExistException;
import ru.yandex.practicum.filmorate.model.FilmsRatingData;

@Component
public class InMemoryFilmsRatingStorage extends InMemoryStorage<FilmsRatingData> {

    @Override
    public FilmsRatingData create(FilmsRatingData ratingData) {
        if (isExist(ratingData.getFilmId())) {
            throw new EntryAlreadyExistException("Cannot create film rating entry because it already exist");
        }
        super.storage.put(ratingData.getFilmId(), ratingData);
        return ratingData;
    }
}
