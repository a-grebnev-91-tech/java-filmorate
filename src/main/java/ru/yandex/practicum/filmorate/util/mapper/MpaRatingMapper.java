package ru.yandex.practicum.filmorate.util.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.dto.web.MpaRatingWebDto;
import ru.yandex.practicum.filmorate.model.film.MpaRating;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MpaRatingMapper {

    public MpaRating dtoToRating(MpaRatingWebDto dto) {
        return MpaRating.getById(dto.getId());
    }

    public MpaRatingWebDto ratingToDto(MpaRating rating) {
        return new MpaRatingWebDto(rating.getId(), rating.getTitle());
    }

    public List<MpaRatingWebDto> batchRatingToDto(List<MpaRating> ratings) {
        return ratings.stream().map(this::ratingToDto).collect(Collectors.toList());
    }
}
