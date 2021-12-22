import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Steuerung extends JFrame{
    private int r, g, b;

    public Steuerung (Leinwand leinwand) {
        super("Zeichenprogramm - Steuerung");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension size = new Dimension(500, 500);

        JPanel panel = new JPanel();
        this.add(panel);
        panel.setPreferredSize(size);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        //----ELEMENTS---------------------------------------------------------------------

        JButton color_preview = new JButton();
        panel.add(color_preview);
        color_preview.setBackground(new Color(r, g, b));
        color_preview.setEnabled(false);
        color_preview.setPreferredSize(new Dimension(500, 100));

        JSlider red = new JSlider(0, 255, 0);
        panel.add(red);
        red.setBackground(Color.RED);
        JLabel red_label = new JLabel("0");
        panel.add(red_label);
        red_label.setLabelFor(red);
        red.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = red.getValue();
                red_label.setText(Integer.toString(value));
                r = value;
                leinwand.setColor(r, g, b);
                color_preview.setBackground(new Color(r, g, b));
            }
        });

        JSlider green = new JSlider(0, 255, 0);
        panel.add(green);
        green.setBackground(Color.GREEN);
        JLabel green_label = new JLabel("0");
        panel.add(green_label);
        green_label.setLabelFor(green);
        green.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = green.getValue();
                green_label.setText(Integer.toString(value));
                g = value;
                leinwand.setColor(r, g, b);
                color_preview.setBackground(new Color(r, g, b));
            }
        });

        JSlider blue = new JSlider(0, 255, 0);
        panel.add(blue);
        blue.setBackground(Color.BLUE);
        JLabel blue_label = new JLabel("0");
        panel.add(blue_label);
        blue_label.setLabelFor(blue);
        blue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = blue.getValue();
                blue_label.setText(Integer.toString(value));
                b = value;
                leinwand.setColor(r, g, b);
                color_preview.setBackground(new Color(r, g, b));
            }
        });

        JCheckBox rainbow = new JCheckBox();
        panel.add(rainbow);
        rainbow.setSelected(false);
        JLabel rainbow_label = new JLabel("Regenbogen-Modus");
        panel.add(rainbow_label);
        rainbow_label.setLabelFor(rainbow);
        rainbow.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    red.setEnabled(false);
                    green.setEnabled(false);
                    blue.setEnabled(false);

                    leinwand.setColor(255, 0, 0);
                    leinwand.rainbow = true;
                }
                else {
                    leinwand.rainbow = false;
                    leinwand.setColor(r, g, b);

                    red.setEnabled(true);
                    green.setEnabled(true);
                    blue.setEnabled(true);
                }
            }
        });

        JSlider breite = new JSlider(1, 100, 10);
        panel.add(breite);
        breite.setBackground(Color.WHITE);
        JLabel breite_label = new JLabel("Breite: 10");
        panel.add(breite_label);
        breite_label.setLabelFor(breite);
        breite.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = breite.getValue();
                breite_label.setText("Breite: " + value);
                leinwand.zeichenbreite = value;
            }
        });

        JSlider punktung = new JSlider(1, 100, 10);
        panel.add(punktung);
        punktung.setBackground(Color.WHITE);
        JLabel punktung_label = new JLabel("max Punkteabstand in einer Linie: 1 Pixel");
        panel.add(punktung_label);
        punktung_label.setLabelFor(punktung);
        punktung.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = punktung.getValue();
                punktung_label.setText("max Punkteabstand in einer Linie: " + (value/10.0) + " Pixel");
                leinwand.max_distance = (value/10.0);
            }
        });

        //---------------------------------------------------------------------------------

        this.pack();
        this.setVisible(true);
    }
}
