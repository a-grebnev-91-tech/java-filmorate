package ru.yandex.practicum.filmorate.util.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.dto.MpaRatingDto;
import ru.yandex.practicum.filmorate.model.film.MpaRating;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MpaRatingMapper {

    public MpaRating dtoToRating(MpaRatingDto dto) {
        return MpaRating.getById(dto.getId());
    }

    public MpaRatingDto ratingToDto(MpaRating rating) {
        return new MpaRatingDto(rating.getId(), rating.getTitle());
    }

    public List<MpaRatingDto> batchRatingToDto(List<MpaRating> ratings) {
        return ratings.stream().map(this::ratingToDto).collect(Collectors.toList());
    }
}
