import java.awt.*;

public class Enemy {

    Rectangle hitbox;//defines variables for the enemy
    int width;
    int height;
    Image defaultPic;
    Image HESGETTINGCLOSE;
    Image gameOverPic;
    int xpos;
    int ypos;
    double dx;
    double dy;
    boolean isAlive;
    BasicGameApp game;

    public Enemy(int widthInput, int heightInput, int xposInput, int yposInput){//constructor for the enemy
        hitbox = new Rectangle(xposInput, yposInput, widthInput, heightInput);//makes the hitbox
        width =widthInput;// sets the variables for the enemy
        height=heightInput;
        xpos=xposInput;
        ypos=yposInput;
        dx = 0;
        dy = 0;
    }



}
