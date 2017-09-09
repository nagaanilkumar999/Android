package com.example.android.pathoflowestlib;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PathStateTest {

    private PathState subject;

    @Before
    public void setUp() {
        subject = new PathState(2);
    }

    @Test
    public void beginsWithNoRowsTraversed() {
        assertThat(subject.getRowsTraversed().size(), equalTo(0));
    }

    @Test
    public void beginsWithNoCost() {
        assertThat(subject.getTotalCost(), equalTo(0));
    }

    @Test
    public void beginsUnsuccessful() {
        assertThat(subject.isSuccessful(), is(false));
    }

    @Test
    public void beginsWithPathLengthOfZero() {
        assertThat(subject.getPathLength(), equalTo(0));
    }

    @Test
    public void beginsIncomplete() {
        assertThat(subject.isComplete(), is(false));
    }

    @Test
    public void returnsRowsAdded() {
        List<Integer> expectedRows = new ArrayList<Integer>();

        expectedRows.add(1);
        subject.addRowWithCost(1, 0);
        assertThat(subject.getRowsTraversed(), equalTo(expectedRows));

        expectedRows.add(10);
        subject.addRowWithCost(10, 0);
        assertThat(subject.getRowsTraversed(), equalTo(expectedRows));
    }

    @Test
    public void returnsCostForRowsAdded() {
        subject.addRowWithCost(1, 2);
        assertThat(subject.getTotalCost(), equalTo(2));

        subject.addRowWithCost(1, 10);
        assertThat(subject.getTotalCost(), equalTo(12));
    }

    @Test
    public void isOverCostIfCostExceedsMaximum() {
        subject.addRowWithCost(1, PathState.MAXIMUM_COST + 1);
        assertThat(subject.isOverCost(), is(true));
    }

    @Test
    public void isNotOverCostIfCostDoesNotExceedMaximum() {
        subject.addRowWithCost(1, PathState.MAXIMUM_COST);
        assertThat(subject.isOverCost(), is(false));
    }

    @Test
    public void returnsPathLengthAfterAddingRows() {
        subject.addRowWithCost(2, 1);
        assertThat(subject.getPathLength(), equalTo(1));

        subject.addRowWithCost(1, 10);
        assertThat(subject.getPathLength(), equalTo(2));
    }

    @Test
    public void isCompleteIfPathLengthMatchesGridLength() {
        subject.addRowWithCost(2, 1);
        assertThat(subject.isComplete(), is(false));

        subject.addRowWithCost(1, 10);
        assertThat(subject.isComplete(), is(true));
    }

    @Test
    public void isSuccessfulIfCompleteAndCostDoesNotExceedMaximum() {
        subject.addRowWithCost(1, PathState.MAXIMUM_COST - 1);
        assertThat(subject.isSuccessful(), is(false));

        subject.addRowWithCost(2, 1);
        assertThat(subject.isSuccessful(), is(true));
    }

    @Test
    public void isNotSuccessfulIfCompleteAndCostExceedsMaximum() {
        subject.addRowWithCost(1, PathState.MAXIMUM_COST);
        subject.addRowWithCost(2, 1);
        assertThat(subject.isSuccessful(), is(false));
    }

    @Test
    public void copyConstructorMakesAnExactCopyOfPath() {
        subject.addRowWithCost(1, 10);
        subject.addRowWithCost(2, 10);
        List<Integer> expectedRows = new ArrayList<Integer>();
        expectedRows.add(1);
        expectedRows.add(2);

        PathState copy = new PathState(subject);

        assertThat(copy.getTotalCost(), equalTo(20));
        assertThat(copy.getRowsTraversed(), equalTo(expectedRows));
        assertThat(copy.isComplete(), is(true));
    }

    @Test
    public void copyConstructorMakesAnIndependentCopyOfPath() {
        subject.addRowWithCost(1, 10);
        List<Integer> expectedRows = new ArrayList<Integer>();
        expectedRows.add(1);

        PathState copy = new PathState(subject);
        subject.addRowWithCost(2, 10);

        assertThat(copy.getTotalCost(), equalTo(10));
        assertThat(copy.getRowsTraversed(), equalTo(expectedRows));
        assertThat(copy.isComplete(), is(false));
    }
}