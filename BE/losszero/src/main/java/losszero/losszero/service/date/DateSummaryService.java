package losszero.losszero.service.date;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

public interface DateSummaryService<T> {
    T getSummary(int lineId, LocalDate startDate, LocalDate endDate);
}
