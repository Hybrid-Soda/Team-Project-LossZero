package losszero.losszero.dto.weekly;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyCircumstanceDTO {
    private LocalDate date;
    private float maxTemp;
    private float minTemp;
    private float maxHumid;
    private float minHumid;
}