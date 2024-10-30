package losszero.losszero.service.date;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public abstract class AbstractDateService<E, D> implements DateSummaryService<List<D>> {

    public abstract List<E> findEntitiesByLineAndDate(int lineId, LocalDate startDate, LocalDate endDate);

    public abstract List<D> createSummaryList(List<E> entities);

    @Override
    public List<D> getSummary(int lineId, LocalDate startDate, LocalDate endDate) {
        List<E> entities = findEntitiesByLineAndDate(lineId, startDate, endDate);

        if (entities.isEmpty()) {
            throw new IllegalArgumentException("데이터가 없습니다.");
        }

        return createSummaryList(entities);
    }
}