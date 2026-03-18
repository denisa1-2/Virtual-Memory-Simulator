package gui;

import statistics.RunResult;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatisticsDialog extends JDialog {

    public StatisticsDialog(JFrame parent, List<RunResult> history) {
        super(parent, "Simulation Statistics", true);

        setLayout(new BorderLayout(10,10));
        setSize(800,300);
        setLocationRelativeTo(parent);

        String[] columnNames = {
                "#",
                "Algorithm",
                "Frames",
                "Pages",
                "Total accesses",
                "Page faults",
                "Fault rate",
                "AMAT",
                "Memory utilization (%)"
        };

        Object[][] data = new Object[history.size()][columnNames.length];
        for(int i = 0; i < history.size(); i++){
            RunResult r = history.get(i);
            data[i][0] = i + 1;
            data[i][1] = r.getAlgorithm();
            data[i][2] = r.getFrames();
            data[i][3] = r.getPages();
            data[i][4] = r.getTotalAccesses();
            data[i][5] = r.getPageFaults();
            data[i][6] = String.format("%.4f", r.getFaultRate());
            data[i][7] = String.format("%.4f", r.getAverageMemoryAccessTime());
            data[i][8] = String.format("%.4f",r.getMemoryUtilizationPercent());
        }

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
