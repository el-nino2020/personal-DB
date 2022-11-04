/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import javax.swing.*;

/**
 * @author Morgan
 */
public class Test {
    public static void main(String[] args) {
        
        JFrame frame = new JFrame();
        frame.setSize(2000, 1000);
        frame.add(new CreateNewDBTablePanel());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("===============DONE==================");
    }
}
