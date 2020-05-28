package ru.home.collaborativeeducation.model.comparator;

import java.util.Comparator;

import ru.home.collaborativeeducation.model.CourseWithMetadataAndComments;

public class RateSourceFilter implements Comparator<CourseWithMetadataAndComments> {

    private boolean isReversed = false;

    public RateSourceFilter() {}

    public RateSourceFilter(boolean isReversed) {
        this.isReversed = isReversed;
    }

    @Override
    public int compare(CourseWithMetadataAndComments first, CourseWithMetadataAndComments second) {
        int result;
        boolean isFirstBigger = first.metadata.likes.getCounter() > second.metadata.likes.getCounter();
        boolean isEqual = first.metadata.likes.getCounter() == second.metadata.likes.getCounter();
        if (isEqual) {
            result = 0;
        } else if (isFirstBigger) {
            result = 1;
        } else {
            result = -1;
        }

        result *= isReversed ? -1 : 1;
        return result;
    }

}
