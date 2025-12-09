//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv DON'T CHANGE! vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// Graphics Libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
public class BasicGameApp implements Runnable {

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;


    //Variable Definition Section
    //You can set their initial values too
    // Like Mario mario = new Mario(); //

    Player player; //adds all the variables used publically in this class and stuff
    Goal leftGoal;
    Goal rightGoal;
    Enemy shark;
    Image background;
    double distance;
    int scorePlayer = 0;
    Text scoreboard;

    KeyInput keyInput;






    // Initialize your variables and construct your program objects here.
    public BasicGameApp() { // BasicGameApp constructor

        //variable and objects
        //create (construct) the objects needed for the game
player = new Player(50,50,200,300); //creates (constructs) the objects needed for the game
shark = new Enemy(50,50,800,300);
leftGoal = new Goal(50,90,165,315);
rightGoal = new Goal(50,90,785,315);

keyInput = new KeyInput(this); //creates keyinput object and links its methods to this class
Font font = new Font("Comic Sans", Font.PLAIN,28); //creates font object for scoreboard (see text class for credits)
scoreboard = new Text("score: " + scorePlayer, font,500,100); //creates the scoreboard object
        setUpGraphics();




background = Toolkit.getDefaultToolkit().getImage("fishtank4.jpeg"); //assigns images to all the image variables
player.alivePic = Toolkit.getDefaultToolkit().getImage("fishball.jpeg");
player.deadPic = Toolkit.getDefaultToolkit().getImage("gameover.jpeg");
shark.defaultPic = Toolkit.getDefaultToolkit().getImage("shark.jpeg");
shark.HESGETTINGCLOSE = Toolkit.getDefaultToolkit().getImage("shark2.jpeg");
shark.gameOverPic = Toolkit.getDefaultToolkit().getImage("shark3.jpeg");





    }
    // end BasicGameApp constructor
boolean moving; //creates a moving boolean to detect when the playe risnt moving to add friction
    public void moveThings() {
        //call the move() code for each object  -
        scoreboard.bounce();
        player.move(); //player constantly moves by dx and dy which are set to 0, decelerate to 0, and increase w the keyboard inputs bellow, this code bellow makes it accelerate while a key is being held
        if (player.isAlive) {
            UHOHHESCHASINGYOU(); //makes the enemy chase the player only while its alive
        }
        if(keyInput.isKeyDown(KeyEvent.VK_W)){
            player.dy-=.5 ;
            moving = true;
        }
        if(keyInput.isKeyDown(KeyEvent.VK_S)){
            player.dy+=.5 ;
            moving = true;
        }
        if(keyInput.isKeyDown(KeyEvent.VK_A)){
            player.dx-=.5 ;
            moving = true;
        }
        if(keyInput.isKeyDown(KeyEvent.VK_D)){
            player.dx+=.5 ;
            moving = true;
        }
        if (moving == false){ //friction
            if (player.dx>0){
                player.dx-=.8;
            }
            if (player.dx<0){
                player.dx+=.8;
            }
            if (player.dy>0){
                player.dy-=.8;
            }
            if (player.dy<0){
                player.dy+=.8;
            }
        }
   //     if (player.dx>100||player.dy>100){player.dx=0;player.dy=0;player.xpos=200;player.ypos=300;}
    }

    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the images
        // Signature: drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)

        g.drawImage(background,0,0,WIDTH,HEIGHT,null); //draws background
if (player.isAlive) { //draws the player alive pic while the player is alive
    g.drawImage(player.alivePic, player.xpos, player.ypos, player.width, player.height, null);
    if (distance < 150){ //changes the picture or the shark enemy while it is close to the player
        g.drawImage(shark.HESGETTINGCLOSE, shark.xpos, shark.ypos, shark.width, shark.height, null);
    }
    else {
        g.drawImage(shark.defaultPic, shark.xpos, shark.ypos, shark.width, shark.height, null); //draws teh normal shark picture
    }
}

else {
    g.drawImage(player.deadPic, player.xpos, player.ypos, player.width, player.height, null); //draws teh gameover player and shark pic while the player is dead
    g.drawImage(shark.gameOverPic, shark.xpos, shark.ypos, shark.width, shark.height, null);
}
        scoreboard.draw(g); //draws the scoreboard



        // Keep the code below at the end of render()
        g.dispose();
        bufferStrategy.show();
    }














    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv DON'T CHANGE! vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    // PSVM: This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever.
        while (true) {
            moveThings();  //move all the game objects
            collisionCheck(); //checks collisions for all the hitboxes
            render();  // paint the graphics
            pause(10); // sleep for 10 ms
        }
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private Image getImage(String filename){
        return Toolkit.getDefaultToolkit().getImage(filename);
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

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

        canvas.addKeyListener(keyInput); //dont rly under stand Keylistener yet
        System.out.println("DONE graphic setup");
    }

    public void keyPressed(KeyEvent e){ //makes it accelerate immidieatly when the key is pressed, kinda redundant but its the basic version of what they tell you to do in the tutorial so i kept it in for refernce
        int key = e.getKeyCode();//basically creates an integer variable to store the numerical ID of the key you press
        if(key==KeyEvent.VK_W){//
            player.dy-=.5 ;
        }
        if(key==KeyEvent.VK_S){
            player.dy+=.5 ;
        }
        if(key ==KeyEvent.VK_A){
            player.dx-=.5 ;
        }
        if(key ==KeyEvent.VK_D){
            player.dx+=.5 ;
        }
        if(key==KeyEvent.VK_R){ //if you press 'r' it will reset the player back to being alive for when you die, and also resets the score and player position
            player.isAlive=true; //brings the player back to life
            scorePlayer = 0; //resets score
            player.xpos = 200; //resets player's position
            player.ypos = 300;
            scoreboard.text = "score: " + scorePlayer; //updates the text in the scoreboard
        }
        /*else {
            if (player.dx>0){
                player.dx-=.8;
            }
            if (player.dx<0){
                player.dx+=.8;
            }
            if (player.dy>0){
                player.dy-=.8;
            }
            if (player.dy<0){
                player.dy+=.8;
            }
        } */


    }

    public void keyReleased(KeyEvent e){
moving = false; //when you release a key it sets moving to false and applies friction

    }

    public void UHOHHESCHASINGYOU(){ //path finding for the shark

        double ydistance =  player.ypos - shark.ypos; //finds the height of the triangle between the shark and player
        double xdistance =  player.xpos - shark.xpos; //finds the length (aka base) of the triangle between the shark and player
        double angle = Math.atan2(ydistance, xdistance); //finds the angle of where the the player is to the shark
        shark.xpos += Math.cos(angle)*3; //these 2 lines always move the shark through the hypotenuse of said triangle i.e the shortest path
        shark.ypos += Math.sin(angle)*3;
        shark.hitbox = new Rectangle((int)shark.xpos,(int)shark.ypos,shark.width,shark.height);//updates shark hitbox after moving
        distance = Math.sqrt(xdistance * xdistance+ydistance*ydistance);//uses pythag. thry. to find the distance between player and shark, and uses that to update the shark's photo if its close
        //System.out.println(distance);
    }


    public void collisionCheck(){
        if (player.hitbox.intersects(shark.hitbox)){
            player.isAlive = false; //kills the player if it hits the shark
        }
        if (player.hitbox.intersects(rightGoal.hitbox)&& player.isAlive){
            scorePlayer+=1; //gives the player a point if its alive and touches the right goal
            scoreboard.text = "score: " + scorePlayer; //updates the text on teh scoreboard
            player.xpos=200; //sets the player back to starting position

        }


    }


//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
}
