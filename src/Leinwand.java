import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Leinwand extends JFrame implements MouseMotionListener{
    public double zeichenbreite = 10;
    public double max_distance = 1;

    private Image leinwandImage;
    private Graphics2D graphics;
    private Zeichenflaeche zeichenflaeche;

    private int r = 0, g = 0, b = 0;//Color
    public boolean rainbow = false;

    private double x_old = -1, y_old = -1;

    public Leinwand() {
        super("Zeichenprogramm");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addMouseMotionListener(this);

        Dimension size = new Dimension(1000, 1000);

        zeichenflaeche = new Zeichenflaeche();
        this.setContentPane(zeichenflaeche);
        zeichenflaeche.setPreferredSize(size);

        this.pack();

        leinwandImage = zeichenflaeche.createImage(size.width, size.height);
        graphics = (Graphics2D) leinwandImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, size.width, size.height);
        graphics.setColor(Color.BLACK);

        this.setVisible(true);
    }

    public void setColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        graphics.setColor(new Color(r, g, b));
    }
    /*
    rot(100) - gelb(110) - grün(010) - cyan(011) - blau(001) - magenta(101)
     */
    public void rainbow() {
        if ((r == 255) && (g < 255) && (b == 0)) {//rot -> gelb
            setColor(r, g+1, b);
        }
        else if ((r > 0) && (g == 255) && (b == 0)) {//gelb -> grün
            setColor(r-1, g, b);
        }
        else if ((r == 0) && (g == 255) && (b < 255)) {//grün -> cyan
            setColor(r, g, b+1);
        }
        else if ((r == 0) && (g > 0) && (b == 255)) {//cyan -> blau
            setColor(r, g-1, b);
        }
        else if ((r < 255) && (g == 0) && (b == 255)) {//blau -> magenta
            setColor(r+1, g, b);
        }
        else {//magenta -> rot
            setColor(r, g, b-1);
        }
    }

    public void draw(double x, double y) {
        graphics.fill(new Ellipse2D.Double(x, y, zeichenbreite, zeichenbreite));
        zeichenflaeche.repaint();
        if (rainbow) rainbow();
        x_old = x;
        y_old = y;
    }
    public void drawLine(double x1, double y1, double x2, double y2) {
        double dx = (x2 - x1);
        double dy = (y2 - y1);
        double length = Math.sqrt((dx * dx) + (dy * dy));
        int points = (int)Math.ceil(length / max_distance);
        dx /= points;
        dy /= points;

        for (int i = 1; i <= points; i++) {
            draw(x1 + (i * dx), y1 + (i * dy));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (x_old >= 0 && y_old >= 0) drawLine(x_old, y_old, e.getX(), e.getY());
        else draw(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x_old = -1;
        y_old = -1;
    }

    private class Zeichenflaeche extends JPanel {
        @Override
        public void paint(Graphics g) {
            g.drawImage(leinwandImage, 0, 0, null);
        }
    }
}
