package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel
{
	// Customized colours
	public static final Color GREY = new Color(1710618);
	public static final Color WHITE = new Color(16777215);
	public static final Color BLUETURD = new Color(5066239);
	public static final Color REDTURD = new Color(16731469);


	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Snake snake = Snake.snake;

		g.setColor(GREY);
		g.fillRect(0, 0, Snake.jWidth, Snake.jHeight);
		
		// colouring p1
		g.setColor(Color.BLUE);
		g.fillRect(snake.head1.x * Snake.SCALE, snake.head1.y * Snake.SCALE, 
				Snake.SCALE, Snake.SCALE);
		// colouring p2
		g.setColor(Color.RED);
		g.fillRect(snake.head2.x * Snake.SCALE, snake.head2.y * Snake.SCALE, 
				Snake.SCALE, Snake.SCALE);
		
		// new block
		g.setColor(Color.white);
		g.fillRect(snake.block.x * Snake.SCALE, snake.block.y * Snake.SCALE, 
     		   Snake.SCALE, Snake.SCALE);
		
		// print out all claimed blocks
		// p1 blocks
		g.setColor(BLUETURD);
		for (int i = 0; i < Snake.p1_blocks.size(); i++){
			g.fillRect(Snake.p1_blocks.get(i).x * Snake.SCALE, Snake.p1_blocks.get(i).y * Snake.SCALE, 
	        		   Snake.SCALE, Snake.SCALE);
	    }
		// p2 blocks
		g.setColor(REDTURD);
		for (int i = 0; i < Snake.p2_blocks.size(); i++){
			g.fillRect(Snake.p2_blocks.get(i).x * Snake.SCALE, Snake.p2_blocks.get(i).y * Snake.SCALE, 
	        		   Snake.SCALE, Snake.SCALE);
	    }
		
		// Messages for user throughout the game
		g.setColor(Color.white);
		String message = "P1 Score: " + snake.score1 + ",   P2 Score: " + snake.score2 + ",   Time: " + snake.time / 20;
		g.drawString(message, (int) (getWidth() / 2 - message.length() * 2.5f), 10);

		message = "GAME OVER";
		if (snake.over)
			g.drawString(message, (int) (getWidth() / 2 - message.length() * 2.5f), 
					(int) snake.dim.getHeight() / 4);

		message = "Paused!";
		if (snake.paused && !snake.over)
			g.drawString(message, (int) (getWidth() / 2 - message.length() * 2.5f), 
					(int) snake.dim.getHeight() / 4);
	}
}
