package frc.scouting;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class ScorePanel extends JPanel{
    private String[] locations = {
        "Cargo Bay", "Rocket 1", "Rocket 2", "Rocket 3"
    };
    private String[] gamePieces = {
        "Cargo", "Hatch"
    };

    //Counter[] totals;
    /**
     * 
     * cargo miss
     * hatch miss
     * cargo hit
     * hatch hit
     * 
     */
    private int[][] totals = {
        {0,0,0,0}, {0,0,0,0}, {0,0,0,0}, {0,0,0,0}
    };

    private int btnNumbers = 0;
    
    public ScorePanel(){
      //  this.totals = totals;

        this.setLayout(new GridLayout(gamePieces.length*2 + 1, locations.length + 1));
        this.setSize(400,200);
        

        for (int i = 0; i < gamePieces.length*2 + 1; i++){
            if (i == gamePieces.length){
               this.add(new JLabel(""));
            } else {
                int index = (i > gamePieces.length) ? (i-1)%gamePieces.length : i;
                this.add(new JLabel(gamePieces[index]));
            }
        }

        for (String str : locations){
            for (String piece : gamePieces){
                this.add(makeButton(piece.charAt(0) + " Miss", btnNumbers, false));
                btnNumbers++;
            }

            this.add(new JLabel(str));
            for (String piece : gamePieces){
                this.add(makeButton(piece.charAt(0) + " Score", btnNumbers, true));
                btnNumbers++;
            }
        }

        

    }

    private JButton makeButton(String label, int index, boolean add){
        JButton res = new JButton(label);
        if (add){
            res.setBackground(Color.GREEN);
        } else {
            res.setBackground(Color.RED);
        }
        
        int [] n = {(int) index/4, index%4};

        res.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				totals[n[0]][n[1]]++;
                System.out.println(totals[n[0]][n[1]]);
			}
		});

        return res;
    }
}