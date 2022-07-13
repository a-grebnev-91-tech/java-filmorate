package ru.yandex.practicum.filmorate.util.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.dto.MpaRatingDto;
import ru.yandex.practicum.filmorate.model.film.MpaRating;

@Component
public class MpaRatingMapper {

    public MpaRating dtoToRating(MpaRatingDto dto) {
        return MpaRating.getById(dto.getId());
    }

    public MpaRatingDto ratingToDto(MpaRating rating) {
        return new MpaRatingDto(rating.getId(), rating.getTitle());
    }
}
