package 搜索算法;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Search {
	public static void main(String arg[]) {
		myFrame myframe = new myFrame();
		myframe.setBounds(800, 400, 1000, 650);
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.setVisible(true);
	}
}
class Result
{
	private int num;
	private long sTime;
	private long eTime;
	public Result(int num,long sTime,long eTime)
	{
		this.num=num;
		this.sTime=sTime;
		this.eTime=eTime;
	}
	public Result()
	{
	}
	public void startCounting()
	{
		sTime=System.currentTimeMillis();
	}
	public void endCounting()
	{
		eTime=System.currentTimeMillis();
	}
	public String getTime()
	{
		String s=new String(eTime-sTime+"ms");
		return s;
	}
	public void setNum(int num)
	{
		this.num=num;
	}
	public int  getNum() {
		// TODO Auto-generated method stub
		
		return num;
	}
}
class myFrame extends JFrame {
	Result result;
	int maxDeep;
	final int cols = 3;
	final int rows = 3;
	int xpos;
	int ypos;
	int endingStatus[][] = null;
	int startingStatus[][] = null;
	myComponent[][] myArray = null;
	ArrayDeque<Integer> path = null;
	int searchMethod;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem BFSItem, DFSItem, AXItem;

	public myFrame() {
	 result=new Result();
		setLayout(null);
		searchMethod = 0;
		myArray = new myComponent[cols][rows];
		path = new ArrayDeque<Integer>();
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				myArray[i][j] = new myComponent(i, j);

				add(myArray[i][j]);
			}
		xpos = 2;
		ypos = 2;
		add(new userComponent());
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menu = new JMenu("Algorithm");
		menuBar.add(menu);

		BFSItem = new JMenuItem("BFS");
		menu.add(BFSItem);
		BFSItem.addActionListener(new AlgorithmChoosingListener());

		DFSItem = new JMenuItem("DFS");
		menu.add(DFSItem);
		DFSItem.addActionListener(new AlgorithmChoosingListener());

		AXItem = new JMenuItem("A*");
		menu.add(AXItem);
		AXItem.addActionListener(new AlgorithmChoosingListener());

	}

	public void setStart() {
		startingStatus = new int[3][3];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				startingStatus[i][j] = myArray[i][j].getNumber();
			}
	}

	public void setEnd() {
		endingStatus = new int[3][3];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				endingStatus[i][j] = myArray[i][j].getNumber();
			}
	}

	public boolean ifFinished() {
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				if (myArray[i][j].getNumber() != endingStatus[i][j])
					return false;
			}
		return true;
	}

	public boolean getMovement() {
		if (endingStatus == null) {
			return false;
		}
		setStart();
		path.clear();
		if (searchMethod == 0)
			return BFSAlgorithm.compute(startingStatus, endingStatus, path,result);
		else if (searchMethod == 1)
		{
			return DFSAlgorithm.compute(startingStatus, endingStatus, path,maxDeep,result);
		}
		
		else if (searchMethod == 2)
			return AXAlgorithm.compute(startingStatus, endingStatus, path,result);

		return false;
	}

	public boolean moveUsingPath() {
		if (path.isEmpty())
			return false;
		Timer timer = new Timer();
		Task timerTask = new Task();
		timer.schedule(timerTask, 0, 500);
		return true;
	}

	class AlgorithmChoosingListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == BFSItem) {
				searchMethod = 0;
			} else if (e.getSource() == DFSItem) {
				String input=JOptionPane.showInputDialog(null,"搜索深度：","深度",JOptionPane.QUESTION_MESSAGE);
				maxDeep=Integer.parseInt(input);
				searchMethod = 1;
			} else if (e.getSource() == AXItem) {
				searchMethod = 2;
			}

		}

	}

	class Task extends TimerTask {
		public void run() {
			if (!path.isEmpty()) {

				int e = path.removeFirst();
				// System.out.println(xpos + ";" + ypos + ";" + e);
				if (e == 0) {

					int temp = myArray[xpos - 1][ypos].getNumber();
					myArray[xpos - 1][ypos].changeNumber(myArray[xpos][ypos].getNumber());
					myArray[xpos][ypos].changeNumber(temp);
					xpos--;

				} else if (e == 1) {

					int temp = myArray[xpos + 1][ypos].getNumber();
					myArray[xpos + 1][ypos].changeNumber(myArray[xpos][ypos].getNumber());
					myArray[xpos][ypos].changeNumber(temp);
					xpos++;

				} else if (e == 2) {

					int temp = myArray[xpos][ypos - 1].getNumber();
					myArray[xpos][ypos - 1].changeNumber(myArray[xpos][ypos].getNumber());
					myArray[xpos][ypos].changeNumber(temp);
					ypos--;

				} else if (e == 3) {

					int temp = myArray[xpos][ypos + 1].getNumber();
					myArray[xpos][ypos + 1].changeNumber(myArray[xpos][ypos].getNumber());
					myArray[xpos][ypos].changeNumber(temp);
					ypos++;

				}
			} else {
				if (ifFinished())
					JOptionPane.showMessageDialog(null, "完成搜索！用时"+result.getTime()+","+"扩展节点数："+result.getNum(), "到达", JOptionPane.INFORMATION_MESSAGE);
				this.cancel();
			}

		}
	}

	class userComponent extends JComponent {
		JButton buttonStart;
		JButton buttonSetEnd;
		JButton buttonUp;
		JButton buttonDown;
		JButton buttonLeft;
		JButton buttonRight;
		JButton buttonCheck;
		JButton buttonCheckEnd;
		JButton buttonMess;
		JLabel label1;

		public userComponent() {
			setBorder(BorderFactory.createEtchedBorder());
			setBounds(550, 15, 400, 470);
			buttonStart = new JButton("开始");
			buttonStart.addActionListener(new RunListener());
			buttonStart.setBounds(80, 80, 100, 40);
			buttonMess = new JButton("打乱");
			buttonMess.addActionListener(new SetListener());
			buttonMess.setBounds(220, 80, 100, 40);
			buttonSetEnd = new JButton("设为终态");
			buttonSetEnd.addActionListener(new SetListener());
			buttonSetEnd.setBounds(150, 400, 100, 40);
			buttonUp = new JButton("上");
			buttonUp.setBounds(175, 170, 50, 30);
			buttonUp.addActionListener(new MoveListener());
			buttonDown = new JButton("下");
			buttonDown.setBounds(175, 220, 50, 30);
			buttonDown.addActionListener(new MoveListener());
			buttonLeft = new JButton("左");
			buttonLeft.setBounds(80, 220, 50, 30);
			buttonLeft.addActionListener(new MoveListener());
			buttonRight = new JButton("右");
			buttonRight.setBounds(270, 220, 50, 30);
			buttonRight.addActionListener(new MoveListener());
			buttonCheck = new JButton("查看搜索路径");
			buttonCheck.addActionListener(new RunListener());
			buttonCheck.setBounds(70, 300, 120, 40);
			buttonCheckEnd = new JButton("查看终态");
			buttonCheckEnd.addActionListener(new RunListener());
			buttonCheckEnd.setBounds(210, 300, 120, 40);
			label1 = new JLabel();
			label1.setBounds(10, 10, 120, 30);
			label1.setText("操作面板:");
			label1.setFont(new Font("Monospaced", Font.BOLD, 20));
			add(buttonStart);
			add(buttonMess);
			add(buttonSetEnd);
			add(buttonUp);
			add(buttonDown);
			add(buttonLeft);
			add(buttonRight);
			add(buttonCheck);
			add(label1);
			add(buttonCheckEnd);
		}

		class MoveListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == buttonUp) {
					if (xpos > 0) {
						int temp = myArray[xpos - 1][ypos].getNumber();
						myArray[xpos - 1][ypos].changeNumber(myArray[xpos][ypos].getNumber());
						myArray[xpos][ypos].changeNumber(temp);
						xpos--;
						setStart();
					}
				}
				if (e.getSource() == buttonDown) {
					if (xpos < 2) {
						int temp = myArray[xpos + 1][ypos].getNumber();
						myArray[xpos + 1][ypos].changeNumber(myArray[xpos][ypos].getNumber());
						myArray[xpos][ypos].changeNumber(temp);
						xpos++;
						setStart();
					}
				}
				if (e.getSource() == buttonRight) {
					if (ypos < 2) {
						int temp = myArray[xpos][ypos + 1].getNumber();
						myArray[xpos][ypos + 1].changeNumber(myArray[xpos][ypos].getNumber());
						myArray[xpos][ypos].changeNumber(temp);
						ypos++;
						setStart();
					}
				}
				if (e.getSource() == buttonLeft) {
					if (ypos > 0) {
						int temp = myArray[xpos][ypos - 1].getNumber();
						myArray[xpos][ypos - 1].changeNumber(myArray[xpos][ypos].getNumber());
						myArray[xpos][ypos].changeNumber(temp);
						ypos--;
						setStart();
					}
				}
			}
		}

		class SetListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == buttonSetEnd) {
					setEnd();
					JOptionPane.showMessageDialog(null, "设置成功", "通知", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(e.getSource()==buttonMess)
				{
					int times=10;
					for(int i=0;i<times;)
					{
						Random r=new Random();
						int mov=r.nextInt()%4;
						// System.out.println(xpos + ";" + ypos + ";" + e);
						if (mov == 0&&xpos>0) {
								i++;
							int temp = myArray[xpos - 1][ypos].getNumber();
							myArray[xpos - 1][ypos].changeNumber(myArray[xpos][ypos].getNumber());
							myArray[xpos][ypos].changeNumber(temp);
							xpos--;

						} else if (mov == 1&&xpos<2) {
                                i++;
							int temp = myArray[xpos + 1][ypos].getNumber();
							myArray[xpos + 1][ypos].changeNumber(myArray[xpos][ypos].getNumber());
							myArray[xpos][ypos].changeNumber(temp);
							xpos++;

						} else if (mov == 2&&ypos>0) {
								i++;
							int temp = myArray[xpos][ypos - 1].getNumber();
							myArray[xpos][ypos - 1].changeNumber(myArray[xpos][ypos].getNumber());
							myArray[xpos][ypos].changeNumber(temp);
							ypos--;

						} else if (mov == 3&&ypos<2) {
                             i++; 
							int temp = myArray[xpos][ypos + 1].getNumber();
							myArray[xpos][ypos + 1].changeNumber(myArray[xpos][ypos].getNumber());
							myArray[xpos][ypos].changeNumber(temp);
							ypos++;

						}
					}
				}
			}
		}

		class RunListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (endingStatus == null) {
					JOptionPane.showMessageDialog(null, "请先设置终止状态", "警告", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (e.getSource() == buttonCheck) {
					if (!getMovement()) {
						JOptionPane.showMessageDialog(null, "未找到路径", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					String pathstr = new String("");
					String checkList = "上下左右";
					for (int element : path) {
						pathstr += checkList.charAt(element);
					}
					JOptionPane.showMessageDialog(null, pathstr, "路径", JOptionPane.INFORMATION_MESSAGE);
				} else if (e.getSource() == buttonStart) {
					if (!getMovement()) {
						JOptionPane.showMessageDialog(null, "未找到路径", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					moveUsingPath();

				} else if (e.getSource() == buttonCheckEnd) {
					String statusStr = new String("");
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							if (endingStatus[i][j] != 9)
								statusStr += endingStatus[i][j] + " ";
							else {
								statusStr += "# ";

							}
						}
						statusStr += "\n";

					}
					JOptionPane.showMessageDialog(null, statusStr, "终态", JOptionPane.INFORMATION_MESSAGE);

				}
			}
		}
	}

}

class myComponent extends JComponent {
	int col;
	int row;
	int number;

	public void changeNumber(int number) {
		this.number = number;
		repaint();
	}

	public int getNumber() {
		return number;
	}

	public myComponent(int row, int col) {
		this.col = col;
		this.row = row;
		number = row * 3 + col + 1;
		setBounds(160 * col + 15, 160 * row + 15, 150, 150);
		setBorder(BorderFactory.createEtchedBorder());
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics g2 = (Graphics) g;
		Font font = new Font("Dialog", Font.BOLD, 60);
		g2.setFont(font);
		g2.setColor(Color.BLACK);
		int x = this.getWidth();
		int y = this.getHeight();
		g2.drawString(String.valueOf(number), x / 2 - 20, y / 2 + 10);
		if (number == 9) {
			g2.setColor(Color.white);
			g2.fillRect(0, 0, x, y);
		}

	}

}
