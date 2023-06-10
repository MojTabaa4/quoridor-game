package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;


public class RoundedButton extends JButton {
    private int borderRadius;
    private Color startColor;
    private Color endColor;
    private boolean showShadow;
    private Color shadowColor;
    private float shadowOpacity;
    private int shadowOffsetX;
    private int shadowOffsetY;

    public RoundedButton(String text, int borderRadius) {
        super(text);
        this.borderRadius = borderRadius;
        this.startColor = getBackground();
        this.endColor = getBackground();
        this.showShadow = false;
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 40));
    }

    public void setGradient(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        repaint();
    }

    public void setShadow(boolean showShadow) {
        this.showShadow = showShadow;
        repaint();
    }

    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
        repaint();
    }

    public void setShadowOpacity(float shadowOpacity) {
        this.shadowOpacity = shadowOpacity;
        repaint();
    }

    public void setShadowOffsetX(int shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
        repaint();
    }

    public void setShadowOffsetY(int shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
        repaint();
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        startColor = bg;
        endColor = bg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Draw the shadow
        if (showShadow) {
            g2d.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), (int) (shadowOpacity * 255)));
            g2d.fillRoundRect(borderRadius + shadowOffsetX, borderRadius + shadowOffsetY, width - 2 * borderRadius, height - 2 * borderRadius, borderRadius, borderRadius);
        }

        // Draw the background gradient
        GradientPaint gradientPaint = new GradientPaint(
                0, 0, startColor,
                0, height, endColor
        );
        g2d.setPaint(gradientPaint);
        g2d.fillRoundRect(0, 0, width, height, borderRadius, borderRadius);

        // Draw the text
        g2d.setColor(getForeground());
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textX = (width - fontMetrics.stringWidth(getText())) / 2;
        int textY = (height - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
        g2d.drawString(getText(), textX, textY);

        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Do nothing to remove the border
    }
}