package ËÑË÷Ëã·¨;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;


public class AXAlgorithm {
	static class status {
		int Status[][];
		int xpos;
		int ypos;
		int directFromParent;
		int deep;
		int cost;
		public status(int Status[][]) {
			this.Status = new int[3][3];
			for (int i = 0; i < 3; i++) {
				this.Status[i] = new int[3];
				for (int j = 0; j < 3; j++) {
					this.Status[i][j] = Status[i][j];
					if (Status[i][j] == 9) {
						xpos = i;
						ypos = j;
					}
				}
			}
			this.directFromParent = -1;
			this.deep = -1;
			this.cost=-1;
		}

		public status(int Status[][], int xpos, int ypos, int directFromParent) {
			this.Status = new int[3][3];
			for (int i = 0; i < 3; i++) {
				this.Status[i] = new int[3];
				for (int j = 0; j < 3; j++) {
					this.Status[i][j] = Status[i][j];
				}
			}
			this.xpos = xpos;
			this.ypos = ypos;
			this.directFromParent = directFromParent;
			this.deep = -1;
			this.cost=-1;
		}

		public status(status oldStatus) {
			this.Status = new int[3][3];
			for (int i = 0; i < 3; i++) {
				this.Status[i] = new int[3];
				for (int j = 0; j < 3; j++) {
					this.Status[i][j] = oldStatus.Status[i][j];
				}
			}
			this.xpos = oldStatus.xpos;
			this.ypos = oldStatus.ypos;
			this.directFromParent = oldStatus.directFromParent;
			this.deep = oldStatus.deep;
			this.cost=-1;
		}

		public status nextMove(int direct) {
			status nextStatus = null;
			if (direct == 0 && xpos > 0) {
				nextStatus = new status(this);
				int temp = nextStatus.Status[xpos][ypos];
				nextStatus.Status[xpos][ypos] = nextStatus.Status[xpos - 1][ypos];
				nextStatus.Status[xpos - 1][ypos] = temp;
				nextStatus.xpos--;
				nextStatus.directFromParent = 0;
				nextStatus.deep++;
			} else if (direct == 1 && xpos < 2) {
				nextStatus = new status(this);
				int temp = nextStatus.Status[xpos][ypos];
				nextStatus.Status[xpos][ypos] = nextStatus.Status[xpos + 1][ypos];
				nextStatus.Status[xpos + 1][ypos] = temp;
				nextStatus.xpos++;
				nextStatus.directFromParent = 1;
				nextStatus.deep++;
			} else if (direct == 2 && ypos > 0) {
				nextStatus = new status(this);
				int temp = nextStatus.Status[xpos][ypos];
				nextStatus.Status[xpos][ypos] = nextStatus.Status[xpos][ypos - 1];
				nextStatus.Status[xpos][ypos - 1] = temp;
				nextStatus.ypos--;
				nextStatus.directFromParent = 2;
				nextStatus.deep++;
			} else if (direct == 3 && ypos < 2) {
				nextStatus = new status(this);
				int temp = nextStatus.Status[xpos][ypos];
				nextStatus.Status[xpos][ypos] = nextStatus.Status[xpos][ypos + 1];
				nextStatus.Status[xpos][ypos + 1] = temp;
				nextStatus.ypos++;
				nextStatus.directFromParent = 3;
				nextStatus.deep++;
			}

			return nextStatus;

		}

		public status lastMove(ArrayList<status> arrayStatus) {
			status nextStatus = null;
			int direct = this.directFromParent;
			if (direct == 0) {
				nextStatus = new status(this);
				int temp = nextStatus.Status[xpos][ypos];
				nextStatus.Status[xpos][ypos] = nextStatus.Status[xpos + 1][ypos];
				nextStatus.Status[xpos + 1][ypos] = temp;
				for (status e : arrayStatus) {
					if (e.ifEqual(nextStatus)) {
						nextStatus = e;
						break;
					}
				}
			} else if (direct == 1) {
				nextStatus = new status(this);
				int temp = nextStatus.Status[xpos][ypos];
				nextStatus.Status[xpos][ypos] = nextStatus.Status[xpos - 1][ypos];
				nextStatus.Status[xpos - 1][ypos] = temp;
				for (status e : arrayStatus) {
					if (e.ifEqual(nextStatus)) {
						nextStatus = e;
						break;
					}
				}
			} else if (direct == 2) {
				nextStatus = new status(this);
				int temp = nextStatus.Status[xpos][ypos];
				nextStatus.Status[xpos][ypos] = nextStatus.Status[xpos][ypos + 1];
				nextStatus.Status[xpos][ypos + 1] = temp;
				for (status e : arrayStatus) {
					if (e.ifEqual(nextStatus)) {
						nextStatus = e;
						break;
					}
				}
			} else if (direct == 3) {
				nextStatus = new status(this);
				int temp = nextStatus.Status[xpos][ypos];
				nextStatus.Status[xpos][ypos] = nextStatus.Status[xpos][ypos - 1];
				nextStatus.Status[xpos][ypos - 1] = temp;
				for (status e : arrayStatus) {
					if (e.ifEqual(nextStatus)) {
						nextStatus = e;
						break;
					}
				}
			}
			return nextStatus;
		}

		public boolean ifEqual(status newStatus) {
			if (newStatus == null)
				return false;
			if (newStatus == this)
				return true;
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					if (Status[i][j] != newStatus.Status[i][j]) {
						return false;
					}
				}

			return true;

		}

		public boolean ifIn(ArrayList<status> statusArray) {
			for (status e : statusArray) {
				if (e.ifEqual(this))
					return true;
			}
			return false;
		}
		public void getCost(int Status[][])
		{
			 cost=deep;
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
				{
					if(this.Status[i][j]!=9&&this.Status[i][j]!=Status[i][j])
						cost++;
				}
		}
	}
    public static Comparator<status> costComparator = new Comparator<status>(){
    	 
        @Override
        public int compare(status c1, status c2) {
            return (int) (c1.cost - c2.cost);
        }
    };

	public static boolean compute(int startingStatus[][], int endingStatus[][], ArrayDeque<Integer> path,Result result) {
		result.startCounting();
		status newStatus=new status(startingStatus);
		ArrayList<status> statusArray = new ArrayList<status>();
		Queue<status> statusQueue = new PriorityQueue<status>(1,costComparator);
		newStatus.deep=1;
		statusQueue.add(newStatus);
		boolean ifFound=true;
		while (!statusQueue.isEmpty()) {
			newStatus = statusQueue.remove();
			/*ÏÔÊ¾³öÕ»Ë³Ðò
			System.out.println(statusQueue.size());
			
			  System.out.println("pop:");
			for(int i=0;i<3;i++)
			{
				for(int j=0;j<3;j++){
					System.out.print(newStatus.Status[i][j]+" ");
				}
				System.out.print("\n");
			}
				*/
			if (newStatus.ifIn(statusArray))
				continue;
			
			statusArray.add(newStatus);
			if (newStatus.ifEqual(new status(endingStatus))) {
				ifFound = true;
				break;
			}
			for(int dir=0;dir<4;dir++)
			{
				  status nextStatus=newStatus.nextMove(dir);
				
				if(nextStatus!=null&&!nextStatus.ifIn(statusArray))
				{
					nextStatus.getCost(endingStatus);
					  /*ÏÔÊ¾ÈëÕ»Ë³Ðò
					System.out.println("put:");
					for(int i=0;i<3;i++)
					{
						for(int j=0;j<3;j++){
							System.out.print(nextStatus.Status[i][j]+" ");
						}
						System.out.print("\n");
					}
					*/
					statusQueue.add(nextStatus);
				}
			}
		}
		if (ifFound)
		{
			newStatus=statusArray.get(statusArray.size()-1);
			while(!newStatus.ifEqual(new status(startingStatus)))
			{
				path.addFirst(newStatus.directFromParent);
				newStatus=newStatus.lastMove(statusArray);
			}
			result.endCounting();
			result.setNum(statusArray.size());
			return true;
		}
		System.out.println("Î´ÕÒµ½£¡");
		return false;
	}

}
