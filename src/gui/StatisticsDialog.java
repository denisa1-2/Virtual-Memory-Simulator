package gui;

import statistics.PerformanceMetrics;

import javax.swing.*;
import java.awt.*;

public class StatisticsDialog extends JDialog {

    public StatisticsDialog(JFrame parent, PerformanceMetrics m) {
        super(parent, "Simulation Statistics", true);

        setLayout(new GridLayout(0, 1, 5, 5));
        setSize(350, 210);
        setLocationRelativeTo(parent);

        add(new JLabel("Total accesses: " + m.getTotalAccesses()));
        add(new JLabel("Page faults: " + m.getTotalPageFaults()));
        add(new JLabel(String.format("Fault rate: %.4f " , m.getFaultRate())));
        add(new JLabel(String.format("AMAT: %.4f" , m.getAverageMemoryAccessTime())));
        add(new JLabel(String.format("Memory utilization: %.2f%%" ,m.getMemoryUtilizationPercent())));

        setVisible(true);
    }
}
