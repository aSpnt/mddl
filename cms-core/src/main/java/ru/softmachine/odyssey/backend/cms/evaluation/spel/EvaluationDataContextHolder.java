package ru.softmachine.odyssey.backend.cms.evaluation.spel;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

/**
 * Используется для передачи данный в контекст исполнения,
 * содержит вспомогательные функции, облегчающие написание выражений
 */
@Data
@RequiredArgsConstructor
public class EvaluationDataContextHolder {

    private final Map<String, Object> contextMap;

    private DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);

    public Integer getLengthSafe(String key) {
        if (contextMap.containsKey(key) && contextMap.get(key) instanceof Collection) {
            return ((Collection) contextMap.get(key)).size();
        }
        if (contextMap.containsKey(key) && contextMap.get(key) instanceof String) {
            return ((String) contextMap.get(key)).length();
        }
        return 0;
    }

    public Double getDoubleSafe(String key) {
        if (contextMap.containsKey(key) && contextMap.get(key) instanceof Double) {
            return Double.parseDouble(contextMap.get(key).toString());
        }
        return 0.0;
    }

    public Double getDoubleOrElse(String key, double defaultValue) {
        if (contextMap.containsKey(key) && contextMap.get(key) instanceof Double) {
            return (Double) contextMap.get(key);
        }
        return defaultValue;
    }

    public Integer getIntSafe(String key) {
        if (contextMap.containsKey(key) && contextMap.get(key) instanceof Integer) {
            return Integer.parseInt(contextMap.get(key).toString());
        }
        return 0;
    }

    public Integer getIntOrElse(String key, int defaultValue) {
        if (contextMap.containsKey(key) && contextMap.get(key) instanceof Integer) {
            return (Integer) contextMap.get(key);
        }
        return defaultValue;
    }

    public Long getLongSafe(String key) {
        if (contextMap.containsKey(key) && contextMap.get(key) instanceof Long) {
            return Long.parseLong(contextMap.get(key).toString());
        }
        return 0L;
    }

    public Long getLongOrElse(String key, long defaultValue) {
        if (contextMap.containsKey(key) && contextMap.get(key) instanceof Long) {
            return (Long) contextMap.get(key);
        }
        return defaultValue;
    }

    public String getStringSafe(String key) {
        if (contextMap.containsKey(key) && contextMap.get(key) instanceof String) {
            return (String) contextMap.get(key);
        }
        return "";
    }

    public String getStringOrElse(String key, String defaultValue) {
        if (contextMap.containsKey(key) && contextMap.get(key) instanceof String) {
            return (String) contextMap.get(key);
        }
        return defaultValue;
    }

    public LocalDate getDateSafe(String key) {
        if (contextMap.containsKey(key) && contextMap.get(key) instanceof LocalDate) {
            return (LocalDate) contextMap.get(key);
        }
        return null;
    }

    public LocalDate getDateSafeOrElse(String key, String defaultValue) {
        if (contextMap.containsKey(key) && contextMap.get(key) instanceof LocalDate) {
            return (LocalDate) contextMap.get(key);
        }
        return LocalDate.parse(defaultValue, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public boolean isNull(String key) {
        return !contextMap.containsKey(key);
    }

    public boolean isNotNull(String key) {
        return contextMap.containsKey(key);
    }

    public List<LocalDateTime> getTourTrainDepartureDates() {
        var ticketType = getTicketType(contextMap);

        var now = LocalDateTime.now();
        var result = getRoutesDepartureDates((List<Map<String, Object>>) contextMap.get("routes"), ticketType).stream()
                .filter(now::isBefore)
                .sorted(LocalDateTime::compareTo)
                .toList();

        return result;

    }

    private List<LocalDateTime> getRoutesDepartureDates(List<Map<String, Object>> routes, String ticketType) {
        var departuresList = new ArrayList<LocalDateTime>();

        for (var route : routes) {
            var departureType = getDepartureType(route);
            if (isIrregular(departureType)) {
                departuresList.addAll(getIrregularScheduleDepartureDates((List<Map<String, Object>>) route.get("departures")));
                if (isComplexType(ticketType)) {
                    departuresList.addAll(getIrregularScheduleDepartureDates((List<Map<String, Object>>) route.get("departuresBackward")));
                }
            } else {
                departuresList.addAll(getRegularScheduleDepartureDates((List<Map<String, Object>>) route.get("regularSchedule")));
                if (isComplexType(ticketType)) {
                    departuresList.addAll(getRegularScheduleDepartureDates((List<Map<String, Object>>) route.get("regularScheduleBackward")));
                }
            }
        }

        return departuresList;
    }

    private String getTicketType(Map<String, Object> context) {
        var ticketType = (String) ((Map<String, Object>) context.get("ticketType")).get("code");
        if (!ticketType.equals("complexTicket") && !ticketType.equals("singleTicket")) {
            throw new RuntimeException("Неправильный code для ticketType. Ожидаемые значения(complexTicket, singleTicket)");
        }
        return ticketType;
    }

    private boolean isComplexType(String ticketType) {
        return ticketType.equals("complexType");
    }

    private String getDepartureType(Map<String, Object> route) {
        var departureType = (String) ((Map) route.get("departureType")).get("code");
        if (!departureType.equals("regular") && !departureType.equals("irregular")) {
            throw new RuntimeException("Неправильный code для departureType. Ожидаемые значения(regular, irregular)");
        }
        return departureType;
    }

    private boolean isIrregular(String departureType) {
        return departureType.equals("irregular");
    }

    private List<LocalDateTime> getIrregularScheduleDepartureDates(List<Map<String, Object>> irregularDepartures) {
        return irregularDepartures.stream().map(d -> {
                    var date = (LocalDate) d.get("departureDate");
                    var time = (LocalTime) d.get("departureTime");
                    return LocalDateTime.of(date, time);
                })
                .toList();
    }

    private List<LocalDateTime> getRegularScheduleDepartureDates(List<Map<String, Object>> regularSchedules) {
        var result = new ArrayList<LocalDateTime>();

        for (var schedule : regularSchedules) {

            var weekDays = switch (getDaysOption(schedule)) {
                case "daily" -> List.of(getWeekDayForDate((LocalDate) schedule.get("from")));
                case "weekly" -> getWeekDays(schedule);
                default -> throw new RuntimeException();
            };

            for (var weekDay : weekDays) {
                var departureDate = getFirstDateOfSchedule((LocalDate) schedule.get("from"), weekDay);

                while (departureDate.isBefore((LocalDate) schedule.get("to"))) {
                    var exceptionDates = ((List<LocalDate>) schedule.get("exceptionsDates")).stream().collect(Collectors.toSet());
                    if (!emptyIfNull(exceptionDates).contains(departureDate)) {
                        for (var time : getDepartureTimes(schedule)) {
                            result.add(LocalDateTime.of(departureDate, time));
                        }
                    }

                    switch (getDaysOption(schedule)) {
                        case "daily":
                            departureDate = departureDate.plusDays(1);
                            break;
                        case "weekly":
                            departureDate = departureDate.plusWeeks(1);
                            break;
                        default:
                            throw new RuntimeException();
                    }
                }
            }
        }

        return result;
    }

    private String getDaysOption(Map<String, Object> regularSchedule) {
        var daysOption = (String) ((Map<String, Object>) regularSchedule.get("daysOption")).get("code");
        if (!daysOption.equals("daily") && !daysOption.equals("weekly")) {
            throw new RuntimeException("Неправильный code для daysOption. Ожидаемые значения(daily, weekly)");
        }
        return daysOption;
    }

    private List<String> getWeekDays(Map<String, Object> regularSchedule) {
        return ((List<Map>) regularSchedule.get("weekDays")).stream()
                .map(days -> (String) days.get("code"))
                .toList();
    }

    private LocalDate getFirstDateOfSchedule(LocalDate from, String weekDay) {
        return IntStream.range(0, 7)
                .mapToObj(i -> from.plusDays(i))
                .filter(day -> day.format(dayOfWeekFormatter).equals(weekDay))
                .findFirst()
                .orElse(from);
    }

    private List<LocalTime> getDepartureTimes(Map<String, Object> regularSchedule) {
        return ((List<Map>) regularSchedule.get("departures")).stream()
                .map(d -> (LocalTime) d.get("departureTime"))
                .toList();
    }

    private String getWeekDayForDate(LocalDate date) {
        return date.format(dayOfWeekFormatter);
    }
}
