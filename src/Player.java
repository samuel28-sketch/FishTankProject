import java.awt.*;

public class Player {
    Rectangle hitbox; //creates variables for player class
    int width;
    int height;
    Image alivePic;
    Image deadPic;
    int xpos;
    int ypos;
    double dx;
    double dy;
    boolean isAlive = true;

    public Player(int widthInput, int heightInput, int xposInput, int yposInput){
        hitbox = new Rectangle(xposInput, yposInput, widthInput, heightInput);
        width =widthInput; //set variables
        height=heightInput;
        xpos=xposInput;
        ypos=yposInput;
        dx = 0;
        dy = 0;
    }

public void move(){
        xpos += dx; //moves the player by dx and dy
        ypos += dy;
        //if (dx>0){dx-=.1;}
        //if (dy>0){dy-=.1;}
    if (xpos>1000){ //wraps the player to the left side if it goes over the right side of teh screen
        xpos = 0;
    }
    else if (xpos<0){ //doesnt let the player go past the left side of the screen
        xpos=0;
    }
    if (ypos>700){ //wraps the player vertically across the screen
        ypos = 0;
    }
    else if (ypos<0){//wraps the player vertically across the screen
        ypos=700;
    }
    hitbox = new Rectangle(xpos, ypos, width, height);//updates the hitbox
}



}
