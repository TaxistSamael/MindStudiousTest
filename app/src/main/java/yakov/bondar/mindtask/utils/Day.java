package yakov.bondar.mindtask.utils;


public enum Day {
    MONDAY ("ПОНЕДЕЛЬНИК"),
    TUESDAY ("ВТОРНИК"),
    WEDNESDAY ("СРЕДА"),
    THURSDAY ("ЧЕТВЕРГ"),
    FRIDAY ("ПЯТНИЦА"),
    SATURDAY ("СУББОТА"),
    SUNDAY ("ВОСКРЕСЕНЬЕ");

    public static final String DAY = "day";
    private final String name;

    private Day(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}

