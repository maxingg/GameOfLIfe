package lifegame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Show  extends JPanel{
	
	/*
	 * 表示层模块，显示生命游戏界面效果
	 * 附：优化意见：1.细胞状态不再变化，则自动结束
	 * 			   2.删除多余代码
	 * 			   3.分离数据和代码函数，规格化界面
	 */

	private static final long serialVersionUID = -336975817478756636L;  
	
	public static char world[][]=new char[Data.WORLD_SIZE][Data.WORLD_SIZE];
    
    public char[][] nextStatus = new char[world.length][world[0].length];  
    
    public static char[][] tempStatus = new char[world.length][world[0].length];  
  
    private Timer timer;
    
    Act act = new Act();
         
    //构造函数
    public Show() { 
    	//初始化均为死细胞
    	for(int i= 0;i<=Data.WORLD_SIZE-1;i++) {
    		for(int j= 0;j < Data.WORLD_SIZE-1;j++) {
    			world[i][j]='N';
    		}
    	}
    	act.startAnimation();
    } 
    
    public void Init() {
    	super.setLayout(null);
    	  JSlider js=new JSlider(1,10);
          js.setBounds(575, 350, 110, 10);
          super.add(js);  
          js.addChangeListener(new ChangeListener() {  
              public void stateChanged(ChangeEvent e) {  
                  int value = js.getValue();
                  Data.DELAY_TIME=value*-100+1100;
                  // Update the time label  
                 timer.setDelay(Data.DELAY_TIME);
              }
          });  
          JLabel lb = new JLabel("慢    繁衍速度   快");   //  创建一个 Label对象
          lb.setBounds(582, 360, 110, 40);
          super.add(lb);   
   	      super.addMouseListener(new MouseListener() {
   			@Override
   			public void mouseEntered(MouseEvent e) {
   				// TODO Auto-generated method stub
   				
   			}
   			@Override
   			public void mouseExited(MouseEvent e) {
   				// TODO Auto-generated method stub	
   			}
   			@Override
   			public void mousePressed(MouseEvent e) {
   				System.out.println("点击成功");// TODO Auto-generated method stub
   				if(e.getX()<=572&&e.getY()<=572) {
   				int j=e.getX()/22;
   				int i=e.getY()/22;//获取细胞的位置
   				System.out.println("点击前"+nextStatus[i][j]);
   				nextStatus[i][j]='A';
   				world[i][j]='A';
   				tempStatus[i][j]='A';//变活细胞
   			    repaint();// TODO Auto-generated method stub
   				System.out.println("点击后"+nextStatus[i][j]);}
   			}
   			@Override
   			public void mouseReleased(MouseEvent e) {
   				// TODO Auto-generated method stub
   				
   			}
   			@Override
   			public void mouseClicked(MouseEvent e) {
   				// TODO Auto-generated method stub
   				
   			}
        	
        });
   }  
    
  //初始化界面
    protected void paintComponent(Graphics g) {  
            super.paintComponent(g);  
            super.setLayout(null);
            JButton b1=new JButton("自动繁衍");
            b1.setBounds(580, 50, 100, 50);
            super.add(b1);
            JButton b2=new JButton("暂停");
            b2.setBounds(580, 125, 100, 50);
            super.add(b2);
            JLabel lb = new JLabel();   //  创建一个 Label对象
            lb.setBounds(580, 520, 100, 40);
            super.add(lb);     // 添加标签到窗口上 
            JButton b3=new JButton("下一代");
            b3.setBounds(580, 200, 100, 50);
            super.add(b3);
            JButton b4 = new JButton("刷新");
            b4.setBounds(580, 275, 100 ,50);
            super.add(b4);
            
            b3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   timer.stop();
                   act.changeCellStatus();  
                   repaint();  
                }
            });
            b4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					for(int i= 0;i<=Data.WORLD_SIZE-1;i++) {
			    		for(int j= 0;j < Data.WORLD_SIZE-1;j++) {
							world[i][j] = nextStatus[i][j] = tempStatus[i][j] = 'N';
			    		}
			    	}
					repaint();
			    	timer.stop();
				}            	
            });
            super.addMouseMotionListener(new MouseMotionListener() {

            	public void mouseMoved(MouseEvent e) {
            		lb.setText("坐标："+e.getX()+","+e.getY());
            		repaint();
            	}

    			@Override
    			public void mouseDragged(MouseEvent e) {
    				// TODO Auto-generated method stub
    				
    			}
    			
           
            }
          );
            b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   timer.start();
                }
            });
            b2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   timer.stop();
                }
            });
            

            for (int j = 0; j < nextStatus.length; j++) {  
                for (int i = 0; i < nextStatus[j].length; i++) {  
                    if (nextStatus[j][i] == Data.WORLD_MAP_ALIVE) {  
                        g.fillRect( i * Data.width, j * Data.height, Data.width, Data.height);  
                       
                    } else {  
                    	 g.drawRect( i * Data.width, j * Data.height, Data.width, Data.height); 
                    }  
                }  
            }  
        }  
      
    class Act {
    	/*
    	 * 封装工具函数，作为逻辑层代码
    	 */
    	public void changeCellStatus() {  
        	
            for (int row = 0; row < nextStatus.length; row++) {  
                for (int col = 0; col < nextStatus[row].length; col++) {  
      
                    switch (neighborsCount(row, col)) {  
                    case 0:  
                    case 1:  
                    case 4:  
                    case 5:  
                    case 6:  
                    case 7:  
                    case 8:  
                        nextStatus[row][col] = Data.WORLD_MAP_NONE;  //细胞死
                        break;  
                    case 2:  
                        nextStatus[row][col] = tempStatus[row][col];  //状态不变
                        break;  
                    case 3:  
                        nextStatus[row][col] = Data.WORLD_MAP_ALIVE;  //细胞活
                        break;  
                    }  
                }  
            }  
        
            copyWorldMap();  //复制地图
        }
    	
    	//计算细胞周围的活细胞个数
        private int neighborsCount(int row, int col) {  
            int count = 0, r = 0, c = 0;  
            for (r = row - 1; r <= row + 1; r++) {  
                for (c = col - 1; c <= col + 1; c++) {  
                    if (r < 0 || r >= tempStatus.length || c < 0  
                            || c >= tempStatus[0].length) {  
                        continue;  
                    }  
                    if (tempStatus[r][c] == Data.WORLD_MAP_ALIVE) {  
                        count++;  
                    }  
                }  
            }  
            if (tempStatus[row][col] == Data.WORLD_MAP_ALIVE) {  
                count--;  
            }  
            return count;  
        }  
      //开始动画
        public void startAnimation() {  
        	Init( );
        	
            for (int row = 0; row < world.length; row++) {  
                for (int col = 0; col < world[0].length; col++) {  
                    nextStatus[row][col] = world[row][col];  
                    tempStatus[row][col] = world[row][col];  
                }  
            }  
            // 创建计时器  
            timer = new Timer(Data.DELAY_TIME, new ActionListener() {  
            	
                @Override  
                public void actionPerformed(ActionEvent e) {  
                	
                    changeCellStatus();  
                    repaint();  
                }  
            });  
            // 开启计时器  
            timer.stop();  
        }  
        //复制地图
        private void copyWorldMap() {  
            for (int row = 0; row < nextStatus.length; row++) {  
                for (int col = 0; col < nextStatus[row].length; col++) {  
                    tempStatus[row][col] = nextStatus[row][col];  
                }  
            }  
        } 
    }
}
