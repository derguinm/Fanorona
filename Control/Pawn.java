package Control;

import java.awt.*;

public class Pawn implements Cloneable{
    private Point center, drawingPoint;
    private boolean highlithed, playable;
    private int diameter;
    private Color c;
    public int i, j;
    public boolean wasDragged, rainbow;

    Pawn(){
        center = new Point(0, 0);
        drawingPoint = new Point(0, 0);
        highlithed = false;
        playable = false;
        wasDragged = false;
        diameter = 0;
    }

    public Pawn clone(){
        Pawn p = new Pawn();
        p.center = this.center;
        p.drawingPoint = this.drawingPoint;
        p.highlithed = this.highlithed;
        p.playable = this.playable;
        p.diameter = this.diameter;
        p.c = this.c;
        p.i = i;
        p.j = j;
        p.wasDragged = wasDragged;

        return p;
    }

    public boolean IsPlayable(){
        return playable;
    }

    public void setPlayable(boolean b){
        playable = b;
    }

    public void setPawnCenter(int x, int y){
        center.move(x, y);
        drawingPoint.move((int)center.getX() - diameter / 2, (int)center.getY() - diameter / 2);
    }

    public Point getPawnCenter(){
        return center;
    }

    public void setHighlight(boolean b){
        highlithed = b;
    }

    public void toggleHighlight(){
        if(highlithed)
            highlithed = false;
        else
            highlithed = true;
    }

    public boolean isHighlithed(){
        return highlithed;
    }

    public void setPawnColor(Color c){
        this.c = c;
    }

    public Color getColor(){
        return c;
    }

    public void setPawnDiameter(int diameter){
        this.diameter = diameter;
    }

    public int getDiameter(){
        return diameter;
    }

    private void drawForm(Graphics2D g, int w, int h, int offsetX, int offsetY, int f){
        switch(f){
            case 1:
                g.drawOval((int)drawingPoint.getX() - offsetX, (int)drawingPoint.getY() - offsetY, w + offsetX*2, h + offsetY*2);
            break;
            case 2:
                g.fillOval((int)drawingPoint.getX() - offsetX, (int)drawingPoint.getY() - offsetY, w + offsetX*2, h + offsetY*2);
            break;
            case 3:
                g.fillOval((int)drawingPoint.getX() - offsetX, (int)drawingPoint.getY() - offsetY, w + offsetX*2, h + offsetY*2);
            break;
        }
    }

    public void drawPawn(Graphics2D g){
        g.setColor(c);
        drawForm(g, diameter, diameter, 0, 0, 2);
    }  

    public void highlightPawn(Graphics2D g, int w){
        switch(w){
            case 1: g.setColor(Color.GREEN);
            break;
            case 2: g.setColor(Color.RED);
            break;
            case 3: g.setColor(Color.BLUE);
            break;
        }
            
        drawForm(g, diameter, diameter, 5, 5, 2);
        g.setColor(c);
        drawForm(g, diameter, diameter, 2, 2, 2);
    }

    public boolean isMouseOver(int x, int y){
        boolean b = false;
        int ray = diameter / 2;
        double c1, c2;
        c1 = Math.pow(x - center.getX(), 2);
        c2 = Math.pow(y - center.getY(), 2);

        if(Math.sqrt(c1 + c2) < ray)
            b = true;

        return b;
    }
}
