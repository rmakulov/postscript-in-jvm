package runtime.graphics.unuse;

/**
 * Created by 1 on 20.03.14.
 */
public class Section {
    public double start;
    public double finish;

    public Section(double start, double finish) {
        this.start = Math.min(start, finish);
        this.finish = Math.max(start, finish);
    }

    public Section intersect(Section section) {
        if (!isIntersect(section)) return null;
        return new Section(Math.max(start, section.start),
                Math.min(finish, section.finish));
    }

    public boolean isIntersect(Section section) {
        return !(start > section.finish || finish < section.start);
    }

    public Section union(Section section) {
        if (!isIntersect(section)) return null;
        return new Section(Math.min(start, section.start),
                Math.max(finish, section.finish));
    }
}
