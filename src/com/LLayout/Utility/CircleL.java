package com.LLayout.Utility;

public class CircleL {
    public CircleL(int r){
        this.radius = r;
    }

    private RectL<Integer> bounds = new RectL<Integer>(0, 0, 100, 100);
    private int radius;


    public CircleL setBounds(RectL<Integer> bounds){
        this.bounds = bounds;
        this.radius = this.bounds.width / 2;
        return this;
    }

    public CircleL setRadius(int r){
        this.radius = r;
        this.bounds.width = r * 2;
        this.bounds.height = r * 2;
        return this;
    }

    public int getRadius(){
        return radius;
    }

    public CircleL setScale(int scale){
        this.radius = scale;
        return this;
    }

    public CircleL setCenterPosition(Vector2L<Integer> centerPosition){
        this.bounds.x = centerPosition.x - this.radius;
        this.bounds.y = centerPosition.y - this.radius;
        return this;
    }

    public RectL<Integer> getBounds() {
        return bounds;
    }

    public Vector2L<Integer> getCenter(){
        return new Vector2L<>(
                this.bounds.x + this.bounds.width / 2,
                this.bounds.y + this.bounds.height / 2
        );
    }

    public boolean contains(Vector2L<Integer> position){
        int d = (int)
                (Math.pow(this.radius, 2) -
                        (Math.pow(this.getCenter().x - position.x, 2) +
                                Math.pow(this.getCenter().y - position.y, 2)));

        return d >= 0;
    }
}