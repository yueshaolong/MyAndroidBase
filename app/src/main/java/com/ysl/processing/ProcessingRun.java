package com.ysl.processing;

import processing.core.*;

public class ProcessingRun extends PApplet{
//    public void setup(){
////        strokeWeight(20);
//
//        float[][] distances;
//        float maxDistance;
//
//        background(0);
//        maxDistance = dist(width/2, height/2, width, height);
//        distances = new float[width][height];
//        for(int i=0; i<height; i++) {
//            for(int j=0; j<width; j++) {
//                float dist = dist(width/2, height/2, j, i);
//                distances[j][i] = dist/maxDistance * 255;
//            }
//        }
//
//        for(int i=0; i<height; i+=2) {
//            for(int j=0; j<width; j+=2) {
//                stroke(distances[j][i]);
//                point(j, i);
//            }
//        }
//
//    }
//    public void draw(){
//        line(mouseX,mouseY,pmouseX,pmouseY);
//        System.out.println("mouseX = "+mouseX);
//        System.out.println("mouseY = "+mouseY);
//        System.out.println("pmouseX = "+pmouseX);
//        System.out.println("pmouseY = "+pmouseY);
//    }

    // Particle System object
    ParticleSystem ps;
    // A PImage for particle's texture
    PImage sprite;

    public void setup() {
        size(640, 360, P2D);
        // Load the image
        sprite = loadImage("sprite.png");
        // A new particle system with 10,000 particles
        ps = new ParticleSystem(10000);

        // Writing to the depth buffer is disabled to avoid rendering
        // artifacts due to the fact that the particles are semi-transparent
        // but not z-sorted.
        hint(DISABLE_DEPTH_MASK);

    }

    public void draw () {
        background(0);
        // Update and display system
        ps.update();
        ps.display();

        // Set the particle system's emitter location to the mouse
        ps.setEmitter(mouseX,mouseY);

        // Display frame rate
        fill(255);
        textSize(16);
        text("Frame rate: " + (int)(frameRate),10,20);

    }
}

