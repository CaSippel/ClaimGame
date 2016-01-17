/* BUGS
 * add: opening screen, music,
 * window size
 */

package snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.swing.JFrame;
import javax.swing.Timer;


/**
 * @Original Framework: Jaryt Bustard
 * @edited: Cassidee
 */
public class Snake implements ActionListener, KeyListener
{
	
	
	public static Snake snake;

	public JFrame jframe;
	
	//dimensions of board and for random generation
	public static int jHeight=600;
	public static int jWidth=800;
	public int maxY = jHeight/SCALE - 1;
	public int maxX = jWidth/ SCALE - 1;
	
	public RenderPanel renderPanel;
	public Timer timer = new Timer(20, this);

	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	// keeps track of the blocks that have been run over
	public static ArrayList<Point> p1_blocks = new ArrayList<Point>();
	public static ArrayList<Point> p2_blocks = new ArrayList<Point>();

	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
	public int score1, score2;
	public int ticks = 0, tailLength = 1, time;
	public int direction1 = DOWN, direction2 = DOWN;

	public Point head1, head2;
	public Point block;

	public Random random;
	public boolean over = false, paused;
	public Dimension dim;
	
	

	public Snake()
	{
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("The Claim Game");
		jframe.setVisible(true);
		// size of frame
		jframe.setSize(jWidth, jHeight+22);
		jframe.setResizable(true);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
		jframe.add(renderPanel = new RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
	}
	
	// functions that check the validity of the moves generated items
	public void validMoveForP1(int x, int y){
		for(int i=0 ; i < p2_blocks.size() ; ++i){
			if (x == p2_blocks.get(i).x && y == p2_blocks.get(i).y) 
				over = true;
		}
	}
	public void validMoveForP2(int x, int y){
		for(int i=0 ; i < p1_blocks.size() ; ++i){
			if (x == p1_blocks.get(i).x && y == p1_blocks.get(i).y) 
				over = true;
		}
	}
	
	
	public void startGame()
	{
		// respective variables for P1 and P2 ///////
		head1 = new Point(0, 0);
		head2 = new Point(maxX, maxY);
		direction1 = DOWN;
		direction2 = UP;
		score1 = 0;
		score2 = 0;
		/////////////////////////////////////////////
		over = false;
		paused = false;
		time = 0;
		tailLength = 1;
		ticks = 0;
		random = new Random();
		snakeParts.clear();
		block = new Point(random.nextInt(maxX), random.nextInt(maxY));
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		renderPanel.repaint();
		++ticks;

		if (ticks % 2 == 0 && head1 != null && !over && !paused)
		{
			time++;
			snakeParts.add(new Point(head1.x, head1.y));

			// player 1's movement
			if (direction1 == UP)
			{
				if (head1.y - 1 >= 0) {
					validMoveForP1(head1.x, head1.y -1);
					head1 = new Point(head1.x, head1.y - 1);
				}
				//else
					//over = true;
			}
			if (direction1 == DOWN)
			{
				if (head1.y + 1 < jHeight/SCALE) {
					validMoveForP1(head1.x, head1.y +1);
					head1 = new Point(head1.x, head1.y + 1);
				}
				//else
					//over = true;
			}
			if (direction1 == LEFT)
			{
				if (head1.x - 1 >= 0) {
					validMoveForP1(head1.x - 1, head1.y);
					head1 = new Point(head1.x - 1, head1.y);
				}
				//else
					//over = true;
			}
			if (direction1 == RIGHT)
			{
				if (head1.x + 1 < jWidth/SCALE) {
					validMoveForP1(head1.x + 1, head1.y);
					head1 = new Point(head1.x + 1, head1.y);
				}
				//else
					//over = true;
			}


			// player 2's movement
			if (direction2 == UP)
			{
				if (head2.y - 1 >= 0) {
					validMoveForP2(head2.x, head2.y - 1);
					head2 = new Point(head2.x, head2.y - 1);
				} 
				//else
					//over = true;
			}
			if (direction2 == DOWN)
			{
				if (head2.y + 1 < jHeight/SCALE) {
					validMoveForP2(head2.x, head2.y + 1);
					head2 = new Point(head2.x, head2.y + 1);
				}
				//else
					//over = true;
			}
			if (direction2 == LEFT)
			{
				if (head2.x - 1 >= 0) {
					validMoveForP2(head2.x - 1, head2.y);
					head2 = new Point(head2.x - 1, head2.y);
				}
				//else
					//over = true;
			}
			if (direction2 == RIGHT)
			{
				if (head2.x + 1 < jWidth/SCALE) {
					validMoveForP2(head2.x + 1, head2.y);
					head2 = new Point(head2.x + 1, head2.y);
				}
				//else
					//over = true;
			}


			if (snakeParts.size() > tailLength)
				snakeParts.remove(0);

			if (block != null)
			{
				if (head1.equals(block))
				{
					// when player 1 gets the block:
					score1 += 10;
					ticks += 500; // increase speed as block eaten
					// make a new point and add that to that
					p1_blocks.add(block);
					block = new Point(random.nextInt(maxX), random.nextInt(maxY));
				}
				if (head2.equals(block)) {
					// when player 2 gets the block:
					score2 += 10;
					ticks += 500;
					// make a new point and add that to that
					p2_blocks.add(block);
					block = new Point(random.nextInt(maxX), random.nextInt(maxY));
				}
					
			}
		}
	}

	public static void main(String[] args)
	{
		snake = new Snake();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int i = e.getKeyCode();

		if ((i == KeyEvent.VK_A) && direction2 != RIGHT)
			direction2 = LEFT;
		if ((i == KeyEvent.VK_D) && direction2 != LEFT)
			direction2 = RIGHT;
		if ((i == KeyEvent.VK_W) && direction2 != DOWN)
			direction2 = UP;
		if ((i == KeyEvent.VK_S ) && direction2 != UP)
			direction2 = DOWN;
		if ((i == KeyEvent.VK_LEFT) && direction1 != RIGHT)
			direction1 = LEFT;
		if ((i == KeyEvent.VK_RIGHT) && direction1 != LEFT)
			direction1 = RIGHT;
		if ((i == KeyEvent.VK_UP) && direction1 != DOWN)
			direction1 = UP;
		if ((i == KeyEvent.VK_DOWN) && direction1 != UP)
			direction1 = DOWN;

		if (i == KeyEvent.VK_SPACE)
		{
			if (over) {
				//refreshing each player's territory
				p1_blocks.clear();
				p2_blocks.clear();
				startGame();
			}
			else
				paused = !paused;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

}