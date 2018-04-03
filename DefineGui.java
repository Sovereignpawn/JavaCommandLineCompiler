/*
* File: DefineGui.java
* Author: Claire Stovall
* Date: February 3, 2018
* Purpose: This class defines the GUI for the program that behaves like the
* Java command line compiler.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

// Class to define the GUI, with an inner class to handle button click event
public class DefineGui extends JFrame {

  private static final int WIDTH = 600;
  private static final int HEIGHT = 350;
  private static final int ZERO = 0;
  private static final int ONE = 1;
  private static final int TWO = 2;
  private JPanel panel;
  private JPanel inputPanel;
  private Handler handler;
  private JButton topologicalOrderButton;
  private JButton buildDirectedGraphButton;
  private JLabel inputFileName;
  private JLabel classToRecompile;
  private JTextField fileNameInput;
  private JTextField classInput;
  private JTextArea recompilationOutput;
  private GridBagConstraints c;

  // Constructor to define the GUI
  public DefineGui() {
    // Customize frame
    super("Class Dependency Graph");
    setSize(WIDTH, HEIGHT);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    // Create new base panel and set layout
    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

    // Create panel for top row and set layout
    inputPanel = new JPanel();
    inputPanel.setPreferredSize(new Dimension(600, 125));
    inputPanel.setMinimumSize(new Dimension(600, 125));
    inputPanel.setMaximumSize(new Dimension(600, 125));
    inputPanel.setLayout(new GridBagLayout());
    c = new GridBagConstraints();
    c.weightx = 1;

    // Create inputs, buttons, and labels for top row and add to panels
    inputFileName = new JLabel("Input file name:");
    c.gridx = ZERO;
    c.gridy = ZERO;
    c.anchor = GridBagConstraints.CENTER;
    inputPanel.add(inputFileName, c);
    buildDirectedGraphButton = new JButton("Build Directed Graph");
    c.gridx = TWO;
    c.gridy = ZERO;
    inputPanel.add(buildDirectedGraphButton, c);
    panel.add(inputPanel);
    classToRecompile = new JLabel("Class to recompile:");
    c.gridx = ZERO;
    c.gridy = ONE;
    inputPanel.add(classToRecompile, c);
    topologicalOrderButton = new JButton("Topological Order");
    c.gridx = TWO;
    c.gridy = ONE;
    inputPanel.add(topologicalOrderButton, c);
    fileNameInput = new JTextField(12);
    c.gridx = ONE;
    c.gridy = ZERO;
    c.fill = GridBagConstraints.HORIZONTAL;
    inputPanel.add(fileNameInput, c);
    classInput = new JTextField(12);
    c.gridx = ONE;
    c.gridy = ONE;
    inputPanel.add(classInput, c);
    panel.add(inputPanel);

    // Add handlers
    handler = new Handler();
    topologicalOrderButton.addActionListener(handler);
    buildDirectedGraphButton.addActionListener(handler);
    buildDirectedGraphButton.setActionCommand("build");
    topologicalOrderButton.setActionCommand("topo");

    // Create output for bottom row and add to panel
    recompilationOutput = new JTextArea();
    recompilationOutput.setLineWrap(true);
    recompilationOutput.setWrapStyleWord(true);
    panel.add(recompilationOutput);

    // Set borders
    inputPanel.setBorder(BorderFactory.createTitledBorder(" "));
    recompilationOutput.setBorder(BorderFactory.createTitledBorder("Recompi"
        + "lation Order"));

    // Add base panel to frame
    add(panel);
   }

   // Method to make frame visible
   public void display() {
    setVisible(true);
   }

   // Class to handle the button clicks and call appropriate graph methods
   private class Handler implements ActionListener {
      private Graph<String> graph;
      private Scanner scan;
      private StringTokenizer token;
      private String vertex1;
      private String vertex2;

      // Handler for button click
      @Override
      public void actionPerformed(ActionEvent e) {
        // If the "Build Directed Graph" button
        if("build".equals(e.getActionCommand())) {
          // Get input, try to open file, tokenize file contents if possible,
          // call method to build graph, and add edges to graph based on tokens
          try {
           scan = new Scanner(new File(fileNameInput.getText()));
           graph = new Graph<String>();
           while (scan.hasNext()) {
             token = new StringTokenizer(scan.nextLine());
             vertex1 = token.nextToken();
             while (token.hasMoreTokens()) {
               vertex2 = token.nextToken();
               graph.addEdge(vertex1, vertex2);
             }
           }
           JOptionPane.showMessageDialog(null, "Graph Built Successfully",
                "Success", JOptionPane.INFORMATION_MESSAGE);
          } catch (NullPointerException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File Did Not Open",
                "Warning", JOptionPane.WARNING_MESSAGE);
          } finally {
            if (scan != null) {
                scan.close();
            }
          }
        // If the "Topological Order" button
        } else if("topo".equals(e.getActionCommand())) {
          // Try calling the generateToplogicalOrder method using class input
          // and setting output to display
          try {
            recompilationOutput.setText(graph.generateToplogicalOrder(classInput.getText()));
          } catch (InvalidClassException exc) {
            JOptionPane.showMessageDialog(null, "Invalid Class Name",
                "Warning", JOptionPane.WARNING_MESSAGE);
          } catch (CycleDetectedException except) {
            JOptionPane.showMessageDialog(null, "Cycle Detected",
                "Warning", JOptionPane.WARNING_MESSAGE);
          }
        }
      }
    }

  // Main method calling constructor to build and display the GUI
  public static void main(String[] args) {
    DefineGui gui = new DefineGui();
    gui.display();
  }
}
