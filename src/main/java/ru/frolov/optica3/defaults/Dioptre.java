package ru.frolov.optica3.defaults;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.DoubleStream;


public class Dioptre {

    private static final List<String> dioptresStringList = createDioptresStringList();

    public static List<String> getDioptresStringList() {
        return dioptresStringList;
    }

    private static List<String> createDioptresStringList() {
        List<String> dioptresStringList = new ArrayList<>();

        double[] doubles = DoubleStream.iterate(-20, operand -> operand <= 20, operand -> operand + 0.25).toArray();

        dioptresStringList.add("_");
        for (double d : doubles) {
            if (d == 0) dioptresStringList.add("p l a n u m");
            else
                dioptresStringList.add(d > 0 ? "+" + d : "" + d);
        }

        return  Collections.unmodifiableList(dioptresStringList);
    }
}
