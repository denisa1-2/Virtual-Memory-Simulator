package gui;

import algorithms.FIFO;
import algorithms.LRU;
import algorithms.OPT;
import algorithms.PageReplacementAlgorithm;
import core.MemoryManager;
import core.MemoryManager.Step;
import statistics.PerformanceMetrics;
import utils.PageSequenceGenerator;
import model.Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SimulationFrame extends JFrame {

    private MemoryManager manager;
    private int[] currentSequence;

    private JPanel simulationPanel;
    private JPanel sequencePanel;

    private JComboBox<String> algorithmBox;
    private JTextField framesField;
    private JTextField pagesField;
    private JTextField manualSequenceField;
    private JTextField randomLenField;
    private JTextField randomMaxPageField;

    private JButton runButton;
    private JButton statsButton;
    private JButton randomButton;

    public SimulationFrame(){
        super("Virtual Memory Simulator");

        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        add(createTopSequencePanel(), BorderLayout.NORTH);
        add(createCenterSimulationPanel(), BorderLayout.CENTER);
        add(createBottomControls(), BorderLayout.SOUTH);

        statsButton.setEnabled(false);
        setLocationRelativeTo(null);

        refreshSequenceFromField();

        setVisible(true);
    }

    // --------------------------- TOP PANEL -----------------------------

    private JPanel createTopSequencePanel(){
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(new EmptyBorder(25, 15, 10, 15));
        top.setBackground(Color.WHITE);

        JLabel title = new JLabel("Page sequence:");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        sequencePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 5));
        sequencePanel.setBackground(Color.WHITE);

        top.add(title);
        top.add(Box.createVerticalStrut(10));
        top.add(sequencePanel);

        return top;
    }


    private JScrollPane createCenterSimulationPanel(){
        simulationPanel = new JPanel();
        simulationPanel.setLayout(new BoxLayout(simulationPanel, BoxLayout.Y_AXIS));
        simulationPanel.setBackground(Color.WHITE);
        simulationPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(simulationPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);

        return scroll;
    }

    private JPanel createBottomControls(){

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(new EmptyBorder(20, 20, 20, 20));
        bottom.setBackground(Color.WHITE);

        Font f = new Font("Arial", Font.PLAIN, 16);

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        row1.setBackground(Color.WHITE);

        row1.add(new JLabel("Algorithm:"));
        algorithmBox = new JComboBox<>(new String[]{"FIFO", "LRU", "OPT"});
        algorithmBox.setFont(f);
        row1.add(algorithmBox);

        row1.add(new JLabel("Frames:"));
        framesField = new JTextField("3", 5);
        framesField.setFont(f);
        row1.add(framesField);

        row1.add(new JLabel("Pages:"));
        pagesField = new JTextField("10", 5);
        pagesField.setFont(f);
        row1.add(pagesField);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        row2.setBackground(Color.WHITE);

        row2.add(new JLabel("Sequence:"));
        manualSequenceField = new JTextField("6 0 1 2 0 3 0 1", 40);
        manualSequenceField.setFont(f);
        row2.add(manualSequenceField);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        row3.setBackground(Color.WHITE);

        row3.add(new JLabel("Random length:"));
        randomLenField = new JTextField("20", 5);
        randomLenField.setFont(f);
        row3.add(randomLenField);

        row3.add(new JLabel("Max page:"));
        randomMaxPageField = new JTextField("10", 5);
        randomMaxPageField.setFont(f);
        row3.add(randomMaxPageField);

        randomButton = new JButton("Generate random");
        randomButton.setFont(f);
        randomButton.addActionListener(e -> generateRandomSequence());
        row3.add(randomButton);

        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        row4.setBackground(Color.WHITE);

        runButton = new JButton("Run simulation");
        runButton.setFont(f);
        runButton.addActionListener(e -> runSimulation());

        statsButton = new JButton("Show statistics");
        statsButton.setFont(f);
        statsButton.addActionListener(e -> showStatistics());

        row4.add(runButton);
        row4.add(statsButton);

        bottom.add(row1);
        bottom.add(row2);
        bottom.add(row3);
        bottom.add(row4);

        return bottom;
    }

    private void generateRandomSequence() {
        try {
            int len = Integer.parseInt(randomLenField.getText());
            int max = Integer.parseInt(randomMaxPageField.getText());

            int[] seq = PageSequenceGenerator.generateRandomSequence(len, max);
            StringBuilder sb = new StringBuilder();
            for (int n : seq) sb.append(n).append(" ");

            manualSequenceField.setText(sb.toString().trim());

            refreshSequenceFromField();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid random parameters", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshSequenceFromField() {
        int[] seq = PageSequenceGenerator.parseManualInput(manualSequenceField.getText());
        if (seq != null && seq.length > 0) {
            updateSequencePanel(seq, -1, false);
        } else {
            sequencePanel.removeAll();
            sequencePanel.revalidate();
            sequencePanel.repaint();
        }
    }

    private void runSimulation() {
        try {
            int frames = Integer.parseInt(framesField.getText());
            int pages = Integer.parseInt(pagesField.getText());

            int[] seq = PageSequenceGenerator.parseManualInput(manualSequenceField.getText());
            if (seq == null || seq.length == 0) {
                JOptionPane.showMessageDialog(this, "Invalid sequence", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PageReplacementAlgorithm algorithm = switch (algorithmBox.getSelectedItem().toString()) {
                case "FIFO" -> new FIFO();
                case "LRU" -> new LRU();
                default -> new OPT();
            };

            manager = new MemoryManager(frames, pages, algorithm);
            manager.reset();
            currentSequence = seq;

            updateSequencePanel(seq, -1, false);
            statsButton.setEnabled(true);

            new Thread(() -> playAnimation(seq)).start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void playAnimation(int[] seq) {
        try {
            for (int i = 0; i < seq.length; i++) {

                final int index = i;
                final int page = seq[i];
                final Step step = manager.step(page, seq, i);

                SwingUtilities.invokeLater(() -> {
                    updateSequencePanel(seq, index, step.hit);
                    drawFrames(step.snapshot, step.frameIndex, step.hit, step.page);
                });

                Thread.sleep(600);
            }
        } catch (InterruptedException ignored) {}
    }

    private void updateSequencePanel(int[] seq, int currentIndex, boolean hit) {
        sequencePanel.removeAll();
        for (int i = 0; i < seq.length; i++) {
            JLabel lbl = new JLabel(String.valueOf(seq[i]));
            lbl.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            lbl.setPreferredSize(new Dimension(55, 50));
            lbl.setFont(new Font("Arial", Font.BOLD, 20));
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setOpaque(true);
            lbl.setBackground(Color.WHITE);

            if (i == currentIndex) {
                lbl.setBackground(hit ? new Color(0, 180, 0) : new Color(200, 0, 0));
                lbl.setForeground(Color.WHITE);
            } else {
                lbl.setForeground(Color.BLACK);
            }

            sequencePanel.add(lbl);
        }

        sequencePanel.revalidate();
        sequencePanel.repaint();
    }

    private void drawFrames(Frame[] frames, int highlightedIndex, boolean hit, int pageNumber) {
        simulationPanel.removeAll();

        JPanel framesRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 35, 25));
        framesRow.setBackground(Color.WHITE);

        for (int i = 0; i < frames.length; i++) {

            JPanel box = new JPanel();
            box.setPreferredSize(new Dimension(140, 120));
            box.setLayout(new BorderLayout());
            box.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
            box.setOpaque(true);

            if (i == highlightedIndex)
                box.setBackground(hit ? new Color(0, 180, 0) : new Color(220, 0, 0));
            else
                box.setBackground(new Color(240, 240, 240));

            String text = frames[i].isFree() ? "Empty" : "Page " + frames[i].getPage().getId();
            JLabel center = new JLabel(text, SwingConstants.CENTER);
            center.setFont(new Font("Arial", Font.BOLD, 18));
            center.setForeground(i == highlightedIndex ? Color.WHITE : Color.BLACK);

            JLabel bottom = new JLabel("Frame " + i, SwingConstants.CENTER);
            bottom.setFont(new Font("Arial", Font.PLAIN, 14));
            bottom.setForeground(Color.DARK_GRAY);

            box.add(center, BorderLayout.CENTER);
            box.add(bottom, BorderLayout.SOUTH);

            framesRow.add(box);
        }

        simulationPanel.add(framesRow);

        JLabel info = new JLabel("Last accessed: page " + pageNumber + (hit ? " (HIT)" : " (MISS)"));
        info.setFont(new Font("Arial", Font.BOLD, 20));
        info.setForeground(hit ? new Color(0, 150, 0) : new Color(180, 0, 0));
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.setBorder(new EmptyBorder(20, 0, 0, 0));

        simulationPanel.add(info);

        simulationPanel.revalidate();
        simulationPanel.repaint();
    }

    private void showStatistics() {
        if (manager == null || currentSequence == null) return;

        PageReplacementAlgorithm algStats = switch (algorithmBox.getSelectedItem().toString()) {
            case "FIFO" -> new FIFO();
            case "LRU" -> new LRU();
            default -> new OPT();
        };

        MemoryManager statManager = new MemoryManager(
                manager.getFrames().length,
                manager.getPageTable().size(),
                algStats
        );

        statManager.runSimulation(currentSequence);

        PerformanceMetrics metrics = new PerformanceMetrics(
                statManager,
                currentSequence,
                1.0,
                100.0
        );

        new StatisticsDialog(this, metrics);
    }
}
