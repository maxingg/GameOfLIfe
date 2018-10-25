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
	 * ��ʾ��ģ�飬��ʾ������Ϸ����Ч��
	 * �����Ż������1.ϸ��״̬���ٱ仯�����Զ�����
	 * 			   2.ɾ���������
	 * 			   3.�������ݺʹ��뺯������񻯽���
	 */

	private static final long serialVersionUID = -336975817478756636L;  
	
	public static char world[][]=new char[Data.WORLD_SIZE][Data.WORLD_SIZE];
    
    public char[][] nextStatus = new char[world.length][world[0].length];  
    
    public static char[][] tempStatus = new char[world.length][world[0].length];  
  
    private Timer timer;
    
    Act act = new Act();
         
    //���캯��
    public Show() { 
    	//��ʼ����Ϊ��ϸ��
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
          JLabel lb = new JLabel("��    �����ٶ�   ��");   //  ����һ�� Label����
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
   				System.out.println("����ɹ�");// TODO Auto-generated method stub
   				if(e.getX()<=572&&e.getY()<=572) {
   				int j=e.getX()/22;
   				int i=e.getY()/22;//��ȡϸ����λ��
   				System.out.println("���ǰ"+nextStatus[i][j]);
   				nextStatus[i][j]='A';
   				world[i][j]='A';
   				tempStatus[i][j]='A';//���ϸ��
   			    repaint();// TODO Auto-generated method stub
   				System.out.println("�����"+nextStatus[i][j]);}
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
    
  //��ʼ������
    protected void paintComponent(Graphics g) {  
            super.paintComponent(g);  
            super.setLayout(null);
            JButton b1=new JButton("�Զ�����");
            b1.setBounds(580, 50, 100, 50);
            super.add(b1);
            JButton b2=new JButton("��ͣ");
            b2.setBounds(580, 125, 100, 50);
            super.add(b2);
            JLabel lb = new JLabel();   //  ����һ�� Label����
            lb.setBounds(580, 520, 100, 40);
            super.add(lb);     // ��ӱ�ǩ�������� 
            JButton b3=new JButton("��һ��");
            b3.setBounds(580, 200, 100, 50);
            super.add(b3);
            JButton b4 = new JButton("ˢ��");
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
            		lb.setText("���꣺"+e.getX()+","+e.getY());
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
    	 * ��װ���ߺ�������Ϊ�߼������
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
                        nextStatus[row][col] = Data.WORLD_MAP_NONE;  //ϸ����
                        break;  
                    case 2:  
                        nextStatus[row][col] = tempStatus[row][col];  //״̬����
                        break;  
                    case 3:  
                        nextStatus[row][col] = Data.WORLD_MAP_ALIVE;  //ϸ����
                        break;  
                    }  
                }  
            }  
        
            copyWorldMap();  //���Ƶ�ͼ
        }
    	
    	//����ϸ����Χ�Ļ�ϸ������
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
      //��ʼ����
        public void startAnimation() {  
        	Init( );
        	
            for (int row = 0; row < world.length; row++) {  
                for (int col = 0; col < world[0].length; col++) {  
                    nextStatus[row][col] = world[row][col];  
                    tempStatus[row][col] = world[row][col];  
                }  
            }  
            // ������ʱ��  
            timer = new Timer(Data.DELAY_TIME, new ActionListener() {  
            	
                @Override  
                public void actionPerformed(ActionEvent e) {  
                	
                    changeCellStatus();  
                    repaint();  
                }  
            });  
            // ������ʱ��  
            timer.stop();  
        }  
        //���Ƶ�ͼ
        private void copyWorldMap() {  
            for (int row = 0; row < nextStatus.length; row++) {  
                for (int col = 0; col < nextStatus[row].length; col++) {  
                    tempStatus[row][col] = nextStatus[row][col];  
                }  
            }  
        } 
    }
}
