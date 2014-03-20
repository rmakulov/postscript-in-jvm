package runtime.graphics.paths;

import java.util.ArrayList;

/**
 * Created by 1 on 20.03.14.
 */
public class SectionSet {
    public ArrayList<Section> sections;

    public SectionSet(ArrayList<Section> sections) {
        this.sections = sections;
    }

    public SectionSet(Section section) {
        sections = new ArrayList<Section>();
        sections.add(section);
    }

    public SectionSet(Section section1, Section section2) {
        sections = new ArrayList<Section>();
        sections.add(section1);
        union(new SectionSet(section2));
    }

    public void union(SectionSet set) {
        if (sections.isEmpty()) {
            sections.addAll(set.sections);
            return;
        }
        for (Section s : set.sections) {
            union(s);
        }
    }

    public SectionSet(double start, double finish) {
        this(new Section(start, finish));
    }

    public SectionSet(double start1, double finish1, double start2, double finish2) {
        this(new Section(start1, finish1), new Section(start2, finish2));
    }

    public void union(Section section) {
        if (sections.isEmpty()) {
            sections.add(section);
            return;
        }
        for (Section s : sections) {
            if (s.isIntersect(section)) {
                Section newSection = s.union(section);
                sections.remove(s);
                union(newSection);
                break;
            }
        }
    }

    public boolean isIntersect(SectionSet set) {
        for (Section s : set.sections) {
            if (isIntersect(s)) return true;
        }
        return true;
    }

    public boolean isIntersect(Section section) {
        for (Section s : sections) {
            if (s.isIntersect(section)) return true;
        }
        return false;
    }

    public void intersect(Section section) {
        if (!isIntersect(section)) {
            sections.clear();
            return;
        }
        ArrayList<Section> newSections = new ArrayList<Section>();
        for (Section s : sections) {
            if (s.isIntersect(section)) {
                newSections.add(s.intersect(section));
            }
        }
        sections = newSections;
    }

    public void intersect(SectionSet set) {
        for (Section s : set.sections) {
            if (isEmpty()) return;
            intersect(s);
        }
    }

    public boolean isEmpty() {
        return sections.isEmpty();
    }

    public static SectionSet get2PiSection() {
        return new SectionSet(0, Math.PI * 2);
    }
}
