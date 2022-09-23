import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;

public class RunMyProgram implements Runnable {

    final int WIDTH = 800;
    final int HEIGHT = 800;
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;
   // Point[] data;
    Point[] errordata; //data with max 10% error
    Line[] newline;
    private int x;


    public static void main(String[] args) {
        RunMyProgram myAPP = new RunMyProgram();
        new Thread(myAPP).start();
    }

    public RunMyProgram() {
        setUpGraphics();

     //   data = new Point[20];
        errordata = new Point[400];
        newline= new Line[400];

        //   for (int x = 0; x < data.length; x++) {
        //     int xx = ((int) (Math.random() * 100));
        //     data[x] = new Point(xx, 2 * xx + 7);
        //     data[x].print();}

        for (int i = 0; i < errordata.length; i++) {
            int ii = ((int) (Math.random() * 400-200)); //random 200 points
            double Random = 0.1 * (2 * ii + 7);
            double error = Math.random();
            if (error < 0.5) {
                errordata[i] = new Point(ii, ((2 * ii + 7) + Math.random()* Random));
            }
            if (error > 0.5) {
                errordata[i] = new Point(ii, ((2 * ii + 7) - Math.random() * Random));
            }
            errordata[i].print();
        }

        for(int j=0;j<newline.length; j++){
            int jj=((int)(Math.random()*400-200));
            newline[j] = new Line(jj, 2 * jj + 7);
            newline[j].print();
        }
    }
         public void calculateAverageDeviation(){
                double total=0;
                double avedev;
                for(int i=0;i<errordata.length;i++){
                    if(errordata[i].x ==newline[i].x){
                        double I= Math.abs(errordata[i].y-(2*errordata[i].x+7));//absolute value of everything
                        total+=I;
                    }
                }
                avedev= total/ errordata.length;
                System.out.println(avedev);
            }


    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

     /*   for (int x = 0; x < 10; x++) {
            g.fillRect((int)data[x].x, (int)(data[x].y)*-1,2,2);
            g.setColor(Color.red);
            g.drawRect(1*(int)data[x].x, (int)(data[x].y)*-1,8,8);
        }*/
        for (int i = 0; i < 400; i++) {
            g.drawRect((int) errordata[i].x+400, (int) (errordata[i].y) * -1 +400, 2, 2);
            g.setColor(Color.blue);
        }
        for(int x=0; x<50; x++){
            g.drawRect((int)newline[x].x+400,(int)(newline[x].y)*-1+400,2,2);
            g.setColor(Color.MAGENTA);
        }
        g.setColor(Color.black);
        g.drawLine(0,800,800,0);
        g.drawLine(400,0,400,800);
        g.drawLine(0,400,800,400);

        calculateAverageDeviation();


        g.dispose();
        bufferStrategy.show();
    }

    public void run() {
        while (true) {
            render();
        }
    }

    public void setUpGraphics() {
        frame = new JFrame("GraphWindow");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }


}
